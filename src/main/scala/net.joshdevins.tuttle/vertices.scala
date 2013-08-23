package net.joshdevins.tuttle

final object EdgeDirection extends Enumeration {
  type EdgeDirection = Value
  val In, Out = Value
}

import EdgeDirection._

/** A basic vertex with neighbours connected via edges.
  *
  * @type I The type of the vertex IDs, usually an [[Int]] or [[String]]
  * @type M The type of the edge metadata, usually a weight like [[Float]] or
  *         [[Double]] but can also be a case class for something more
  *         complicated (although this has large effects on memory usage). Note
  *         that some implementations do not require edge metadata and thus you
  *         can provide any type such as [[Int]].
  */
trait Vertex[I, M] {
  def id: I
  def numEdges: Long
  def foreachNeighbour[A](fn: (I) => A): Unit
  def foreachEdge[A](fn: (I, M) => A): Unit
}

final object Vertex {
  def walkEdgesWith[I, M, A](neighbours: Iterable[I], metadata: Option[Iterable[M]])(fn: (I, M) => A): Unit = {
    if (metadata.isEmpty)
      throw new UnsupportedOperationException("No metadata provided")

    val nIterator = neighbours.iterator
    val mIterator = metadata.get.iterator

    while(nIterator.hasNext && mIterator.hasNext)
      fn(nIterator.next, mIterator.next)
  }

  def validateEdgeIterators[I, M](neighbours: Iterable[I], metadata: Option[Iterable[M]]): Unit = {
    metadata.foreach { m: Iterable[M] =>
      require(neighbours.size == m.size, "Size of neightbours and metadata must be the same")
    }
  }
}

/** A vertex with neighbours. Neighbour IDs and metadata (if provided) must be
  * of the same size as they are iterated over together.
  */
abstract class AbstractVertex[I, M](
    neighbours: Iterable[I],
    metadata: Option[Iterable[M]])
  extends Vertex[I, M] {

  Vertex.validateEdgeIterators(neighbours, metadata)

  override def numEdges: Long = neighbours.size

  override def foreachNeighbour[A](fn: (I) => A): Unit = neighbours.foreach(fn)

  override def foreachEdge[A](fn: (I, M) => A): Unit =
    Vertex.walkEdgesWith(neighbours, metadata)(fn)
}

/** A vertex with neighbours.
  */
final case class UndirectedVertex[I, M](
    override val id: I,
    neighbours: Iterable[I],
    metadata: Option[Iterable[M]])
  extends AbstractVertex[I, M](neighbours, metadata)

/** A vertex with neighbours where the edges are directed. Only one direction is
  * supported, either in or out edges.
  */
final case class DirectedVertexWithEdgeMetadata[I, M](
    override val id: I,
    neighbours: Iterable[I],
    metadata: Option[Iterable[M]],
    val direction: EdgeDirection)
  extends AbstractVertex[I, M](neighbours, metadata)

/** A vertex with neighbours where the edges are directed. Both in and out-bound
  * edges are supported.
  */
final class MultiDirectedVertex[I, M](
    override val id: I,
    inNeighbours: Iterable[I],
    inMetadata: Option[Iterable[M]],
    outNeighbours: Iterable[I],
    outMetadata: Option[Iterable[M]])
  extends Vertex[I, M] {

  def numInEdges: Long = inNeighbours.size
  def foreachInNeighbour[A](fn: (I) => A): Unit = inNeighbours.foreach(fn)
  def foreachInEdge[A](fn: (I, M) => A): Unit =
    Vertex.walkEdgesWith(inNeighbours, inMetadata)(fn)

  def numOutEdges: Long = outNeighbours.size
  def foreachOutNeighbour[A](fn: (I) => A): Unit = outNeighbours.foreach(fn)
  def foreachOutEdge[A](fn: (I, M) => A): Unit =
    Vertex.walkEdgesWith(outNeighbours, outMetadata)(fn)

  override def numEdges: Long = numInEdges + numOutEdges

  override def foreachNeighbour[A](fn: (I) => A): Unit = {
    foreachInNeighbour(fn)
    foreachOutNeighbour(fn)
  }

  override def foreachEdge[A](fn: (I, M) => A): Unit = {
    if (inMetadata.isEmpty && outMetadata.isEmpty)
      throw new UnsupportedOperationException("No metadata provided")

    if (inMetadata.isDefined) foreachInEdge(fn)
    if (outMetadata.isDefined) foreachOutEdge(fn)
  }
}
