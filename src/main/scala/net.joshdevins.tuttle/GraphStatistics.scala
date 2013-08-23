package net.joshdevins.tuttle

/** General statistics about a graph such as dimension.
  */
final case class GraphStatistics(numVertices: Long, numEdges: Long)

final object GraphStatistics {

  /** Note that this is a relatively inefficient means to calculate statistics
    * but is effective for small datasets like tests.
    */
  def apply(vertices: Seq[Vertex[_, _]]): GraphStatistics = {
    var numEdges = 0l

    vertices.
      filter(_ != null).
      foreach(numEdges += _.numEdges)

    GraphStatistics(vertices.size, numEdges)
  }
}
