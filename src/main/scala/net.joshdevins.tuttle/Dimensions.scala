package net.joshdevins.tuttle

/** The general dimensions of a graph.
  */
final case class Dimensions(numVertices: Long, numEdges: Long)

final object Dimensions {

  /** Note that this is a relatively inefficient means to calculate statistics
    * but is effective for small datasets like tests.
    */
  def apply(vertices: Seq[Vertex[_, _]]): Dimensions = {
    var numEdges = 0l

    vertices.
      filter(_ != null).
      foreach(numEdges += _.numEdges)

    Dimensions(vertices.size, numEdges)
  }
}
