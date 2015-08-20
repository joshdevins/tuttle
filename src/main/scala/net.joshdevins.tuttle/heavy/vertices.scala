package net.joshdevins.tuttle.heavy

import net.joshdevins.tuttle.{Vertex => BaseVertex}
import net.joshdevins.tuttle.EdgeDirection._

final object Vertex {
  def walkEdgesWith[I, M](neighbours: Iterable[I], metadata: Iterable[M])(fn: (I, M) => Unit): Unit = {
    val nIterator = neighbours.iterator
    val mIterator = metadata.iterator

    while(nIterator.hasNext && mIterator.hasNext)
      fn(nIterator.next, mIterator.next)
  }

  def validateEdgeIterators[I, M](neighbours: Iterable[I], metadata: Iterable[M]): Unit =
    require(neighbours.size == metadata.size, "Size of neightbours and metadata must be the same")
}

/** A vertex with neighbours. Neighbour IDs and metadata (if provided) must be
  * of the same size as they are iterated over together.
  */
abstract class AbstractVertex[I, M](
    neighbours: Iterable[I],
    metadata: Iterable[M])
  extends BaseVertex[I, (I, M) => Unit] {

  Vertex.validateEdgeIterators(neighbours, metadata)

  override def numEdges: Long = neighbours.size

  override def foreachNeighbour(fn: (I) => Unit): Unit = neighbours.foreach(fn)

  override def foreachEdge(fn: (I, M) => Unit): Unit =
    Vertex.walkEdgesWith(neighbours, metadata)(fn)
}

/** A vertex with neighbours.
  */
final case class UndirectedVertex[I, M](
    override val id: I,
    neighbours: Iterable[I],
    metadata: Iterable[M])
  extends AbstractVertex[I, M](neighbours, metadata)

/** A vertex with neighbours where the edges are directed. Only one direction is
  * supported, either in or out edges.
  */
final case class DirectedVertexWithEdgeMetadata[I, M](
    override val id: I,
    neighbours: Iterable[I],
    metadata: Iterable[M],
    val direction: EdgeDirection)
  extends AbstractVertex[I, M](neighbours, metadata)

/** A vertex with neighbours where the edges are directed. Both in and out-bound
  * edges are supported.
  */
final class MultiDirectedVertex[I, M](
    override val id: I,
    inNeighbours: Iterable[I],
    inMetadata: Iterable[M],
    outNeighbours: Iterable[I],
    outMetadata: Iterable[M])
  extends Vertex[I, M] {

  def numInEdges: Long = inNeighbours.size
  def foreachInNeighbour(fn: (I) => Unit): Unit = inNeighbours.foreach(fn)
  def foreachInEdge(fn: (I, M) => Unit): Unit =
    Vertex.walkEdgesWith(inNeighbours, inMetadata)(fn)

  def numOutEdges: Long = outNeighbours.size
  def foreachOutNeighbour(fn: (I) => Unit): Unit = outNeighbours.foreach(fn)
  def foreachOutEdge(fn: (I, M) => Unit): Unit =
    Vertex.walkEdgesWith(outNeighbours, outMetadata)(fn)

  override def numEdges: Long = numInEdges + numOutEdges

  override def foreachNeighbour(fn: (I) => Unit): Unit = {
    foreachInNeighbour(fn)
    foreachOutNeighbour(fn)
  }

  override def foreachEdge(fn: (I, M) => Unit): Unit = {
    foreachInEdge(fn)
    foreachOutEdge(fn)
  }
}
