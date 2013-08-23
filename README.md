# Tuttle

A minimal, in-memory graph database. This is modelled on my learnings working with
"[Pregel](http://googleresearch.blogspot.de/2009/06/large-scale-graph-computing-at-google.html)
style" systems for running graph-based learning algorithms. In particular,
extending [Cassovary](http://github.com/twitter/cassovary) to support
weighted edges provided the inspiration and somehow the impetus as well.

[![Build Status](https://travis-ci.org/joshdevins/tuttle.png)](https://travis-ci.org/joshdevins/tuttle)

## Overview

 - flexible vertex types, minimally storing node ID and edges to neighbours
 - support for both in and out-edges (directed) or undirected edges
 - supports arbitrary metadata on edges, for example weights
 - iteration over all nodes in the graph
 - iteration over all out-edges from a vertex
 - optional lookup by vertex ID
 - at a high level, agnostic to on-disk, serialization formats
 - for convenience, includes well-defined, serialization formats

## Abstractions

The main abstractions that you will interact with include:

 - `Graph` -- the main interface to the graph as a whole, allowing you to
              iterate over all vertices in the graph, and in some cases
              also access individual vertices directly by ID
 - `Vertex` -- a single vertex in the graph, allowing you to iterate over all
               edges to neighbours
 - `Edge` -- a directed or undirected edge connecting two vertices in the graph,
             this is a concept only as edges are not maintained in memory as an
             object as this is too costly for large graphs
 - `GraphReader` -- tools to build a `Graph` from a serialized format on disk

## Other Options

This is a non-exhaustive list of Pregel-style frameworks for graph algorithms.
This does not include any number of linear algebra standard libraries in every
other language you can think of.

### Single Machine

 - [Cassovary](http://github.com/twitter/cassovary)
 - [GraphChi](http://graphlab.org/graphchi)

### Distributed

These are typically [BSP](http://en.wikipedia.org/wiki/Bulk_Synchronous_Parallel)
implementations.

 - [Giraph](http://giraph.apache.org/)
 - [GraphLab](http://graphlab.org)
 - [Hama](http://hama.apache.org)

## Credits

 - [SoundCloud](https://soundcloud.com/jobs) for allowing engineers to have 20%
   projects such as this
 - [Cassovary](http://github.com/twitter/cassovary) and its' authors

## Colophon

What's a tuttle? Well, a tree is a graph and a tuttle tuttle tree is where ten
tired turtles are. Since I have kids, I often get children's stories stuck in
my head. A Tuttle Tuttle Tree is a fictional tree in Dr. Seuess's
[ABC](http://seuss.wikia.com/wiki/Dr._Seuss's_ABC) book. "Big T little t what
begins with T? Ten tired turtles in a tuttle tuttle tree."

## License

The MIT License (MIT)

Copyright (c) 2013 SoundCloud, Josh Devins

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
