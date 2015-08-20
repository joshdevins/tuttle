package net.joshdevins.tuttle

/** A basic vertex with neighbours connected via edges.
  *
  * @type I The type of the vertex IDs, usually an [[Int]] or [[String]]
  * @type E The type of the edge visitor function
  */
trait Vertex[I, E] {
  type NeighbourVisitor = (I) => Unit

  def id: I
  def numEdges: Long
  def foreachNeighbour(visitor: NeighbourVisitor): Unit
  def foreachEdge(visitor: E): Unit
}
