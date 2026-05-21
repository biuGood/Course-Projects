# Processing Streams | Scala

This project implements classic streaming algorithms in Scala for processing large-scale data streams with limited memory. The project focuses on approximate distinct counting, fast membership checking, and unbiased random sampling from streaming data.

## Project Overview

Large-scale data streams are often too large to store or process using traditional exact methods. This project implements memory-efficient algorithms that can process stream elements incrementally.

The project includes three major streaming algorithms:

- Flajolet-Martin algorithm for approximate distinct counting
- Bloom Filter for memory-efficient membership testing
- Reservoir Sampling for unbiased random sampling from a stream

## Tools and Technologies

- Scala
- Functional programming
- Hash functions
- BitSet
- Streaming data processing
- Approximate algorithms

## Algorithms Implemented

### 1. Flajolet-Martin Algorithm

The Flajolet-Martin algorithm is used to estimate the number of distinct elements in a stream.

Instead of storing every unique element, the algorithm applies hash functions to stream items and uses the number of trailing zero bits in hashed values to estimate cardinality.

This implementation:

- Processes stream elements using multiple hash functions
- Tracks hash-based counts
- Estimates the number of distinct elements
- Groups estimates and summarizes them using median and average-based aggregation

### 2. Bloom Filter

The Bloom Filter is used for fast and memory-efficient membership checks.

It can quickly answer whether an item is possibly in a set or definitely not in a set.

This implementation:

- Reads an input file to build the filter
- Calculates the number of bits needed based on the expected false positive rate
- Calculates the number of hash functions required
- Uses a Scala BitSet to store hashed positions
- Tests whether stream elements may belong to the original set

Bloom Filters may produce false positives, but they do not produce false negatives when implemented correctly.

### 3. Reservoir Sampling

Reservoir Sampling is used to maintain a random sample from a stream when the total stream size is unknown or too large to store.

This implementation:

- Keeps a fixed-size sample reservoir
- Processes stream elements incrementally
- Randomly replaces existing sample elements using probability-based selection
- Supports standing queries on the current reservoir sample

The project tests reservoir sampling by computing summary statistics such as average and median from the maintained sample.

## File Structure

```text
processing-streams-scala/
│
├── README.md
└── src/
    └── streams/
        ├── filter.scala
        ├── sample.scala
        ├── streams.scala
        ├── utils.scala
        └── counting.scala
```

## Files

| File | Description |
|---|---|
| `streams.scala` | Main implementation of Bloom Filter, Flajolet-Martin, and Reservoir Sampling |
| `filter.scala` | Test program for the Bloom Filter implementation |
| `counting.scala` | Test program for the Flajolet-Martin distinct counting implementation |
| `sample.scala` | Test program for the Reservoir Sampling implementation |
| `utils.scala` | Utility definitions, hash functions, stream query types, and helper methods |

## Key Components

### `Bloom_Filter`

The `Bloom_Filter` class builds a Bloom Filter from an input file and supports membership checks using multiple hash functions.

Main methods:

- `parameters()`: computes filter parameters and builds the BitSet
- `in(v: String)`: checks whether a value is possibly in the filter

### `Flajolet_Martin`

The `Flajolet_Martin` class estimates the number of distinct items in a stream using hash-based approximation.

Main components:

- `hashCounts`: stores estimates from hash functions
- `summarize(groupSize: Int)`: groups estimates and returns a summarized distinct count estimate

### `reservoirSample`

The `reservoirSample` object performs fixed-size random sampling from a stream.

Main method:

- `process(s, sizeSample, r, queries)`: processes stream elements and applies standing queries to the sample

### `utils`

The `utils` object provides:

- Reproducible random hash function generation
- A list of hash functions
- A numeric truncation helper
- Common type definitions for hash functions and standing queries

## Example Usage

### Run Bloom Filter test

```bash
scala streams.FilterMain
```

### Run Flajolet-Martin test

```bash
scala streams.CountingMain
```

### Run Reservoir Sampling test

```bash
scala streams.SampleMain
```

Depending on the local Scala setup, the files may need to be compiled first.

Example:

```bash
scalac src/streams/*.scala
scala streams.FilterMain
```

## Example Data Files

The test files referenced in the code include examples such as:

```text
./data/test10.txt
./data/test10stream.txt
./data/petnames_1.txt
./data/petnames_2.txt
./data/4096.ints
./data/100ordered.ints
```

These data files are used to test false positive rates, distinct element estimation, and sampling behavior.

## Skills Demonstrated

- Scala programming
- Stream processing
- Approximate algorithms
- Bloom Filter implementation
- Flajolet-Martin distinct counting
- Reservoir Sampling
- Hash function design
- BitSet-based memory-efficient storage
- Functional programming with iterators and fold operations
- Probability-based algorithm design

## Resume Summary

This project demonstrates experience implementing memory-efficient streaming algorithms in Scala, including Bloom Filters for membership testing, Flajolet-Martin for approximate distinct counting, and Reservoir Sampling for unbiased random sampling from large-scale data streams.
