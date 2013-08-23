package net.joshdevins.tuttle

import scala.collection._

trait Graph[V <: Vertex]
  extends immutable.Iterable[V] {

  def statistics: GraphStatistics
}

trait IndexedGraph[V <: Vertex]
  extends Graph[V]
  with immutable.IndexedSeq[V]
