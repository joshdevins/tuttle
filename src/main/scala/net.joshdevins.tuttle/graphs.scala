package net.joshdevins.tuttle

trait Graph[I, M, V <: Vertex[I, M]]
  extends Iterable[V]

trait IndexedGraph[I, M, V <: Vertex[I, M]]
  extends Graph[I, M, V]
  with IndexedSeq[V]

/** A graph implementation backed by a simple iterator. Note that the iterable
  * should be dense, containing no empty values or nulls. In every way, this is
  * the most basic and limited type of graph possible.
  */
class SimpleGraph[I, M, V <: Vertex[I, M]](vertices: Iterable[V])
  extends Graph[I, M, V] {

  override def iterator: Iterator[V] = vertices.iterator
}
