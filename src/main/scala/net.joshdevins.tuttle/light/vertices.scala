package net.joshdevins.tuttle.light

import net.joshdevins.tuttle.{Vertex => BaseVertex}

trait Vertex[I] extends BaseVertex[I, (I) => Unit]
