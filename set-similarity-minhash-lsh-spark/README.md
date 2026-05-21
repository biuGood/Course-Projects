# Comparisons Between Large Collections of Sets | Scala & Spark

This project implements set similarity comparison methods using Scala and Apache Spark. The goal was to compare large collections of records by converting them into shingles, estimating similarity using MinHash, and using locality-sensitive hashing (LSH) to efficiently identify similar pairs.

## Project Overview

Comparing every pair of records directly can be computationally expensive when working with large collections of sets. This project uses MinHash and locality-sensitive hashing to approximate similarity and reduce the number of expensive pairwise comparisons.

The project supports:

- Shingling records into sets
- Computing exact Jaccard similarity
- Computing MinHash signatures
- Comparing records using MinHash similarity
- Applying locality-sensitive hashing to find candidate similar pairs
- Reporting similarities from Jaccard, MinHash, and LSH methods

## Tools and Technologies

- Scala
- Apache Spark
- Spark RDD
- MinHash
- Locality-Sensitive Hashing
- Jaccard Similarity
- CRC32 hashing

## Main Workflow

### 1. Read Input Records

The program reads a text file where each line represents a record. Each record is split using a user-provided delimiter.

The first token is treated as the record ID, and the remaining tokens are used to create shingles.

### 2. Create Shingles

Each record is converted into a set of shingles using a sliding window of tokens.

For example, with a shingle size of 3:

```text
a b c d e
```

the generated shingles are:

```text
[a,b,c], [b,c,d], [c,d,e]
```

Each shingle is then hashed into an integer value.

### 3. Compute Jaccard Similarity

Jaccard similarity is used as the exact similarity measure between two shingled records.

```text
Jaccard(A, B) = |A ∩ B| / |A ∪ B|
```

This provides an exact baseline, but comparing all record pairs can be slow for large datasets.

### 4. Compute MinHash Signatures

The project generates multiple hash functions and applies them to each shingled record.

For each hash function, the minimum hashed value is selected. These minimum values form the MinHash signature for the record.

MinHash allows the program to approximate Jaccard similarity without comparing full sets directly.

### 5. Compare MinHash Signatures

The project compares MinHash signatures between record pairs.

The similarity between two MinHash signatures is calculated as the proportion of hash positions where the two records have the same value.

### 6. Apply Locality-Sensitive Hashing

Locality-sensitive hashing is used to reduce unnecessary pairwise comparisons.

The MinHash signature is divided into bands. Each band is hashed to identify candidate similar pairs. Records with matching band hashes are treated as possible matches.

### 7. Generate Report

The program outputs a comparison report showing similarity results from:

- Exact Jaccard similarity
- Full MinHash comparison
- LSH-based comparison

This makes it possible to compare the exact method with approximate methods.

## Files

| File | Description |
|---|---|
| `Main.scala` | Main Spark application that reads arguments, loads data, performs shingling, MinHashing, Jaccard comparison, LSH comparison, and reporting |
| `minhash.scala` | Implements shingling, MinHash signature generation, exact Jaccard similarity, MinHash similarity, and LSH matching |
| `common.scala` | Defines case classes, type aliases, hash utilities, CRC32 hashing, random hash function generation, and report formatting |

## Suggested Project Structure

```text
set-similarity-minhash-lsh-spark/
│
├── README.md
├── build.sbt
├── .gitignore
│
├── project/
│   └── build.properties
│
└── src/
    └── main/
        └── scala/
            └── similarity/
                ├── Main.scala
                ├── minhash.scala
                └── common.scala
```

## How to Run

This project is designed as a Scala/Spark project using SBT.

Compile the project:

```bash
sbt compile
```

Run the main program:

```bash
sbt "runMain similarity.Main <filename> <delimiter> <minimumSimilarity> <shingleSize> <hashCount> <bandSize> <doJaccard> <doAllMinHashes> <printHashCoefficients>"
```

Example:

```bash
sbt "runMain similarity.Main ./data/input.txt , 0.8 3 100 5 true true false"
```

## Command-Line Arguments

| Argument | Description |
|---|---|
| `filename` | Input file containing records |
| `delimiter` | Delimiter used to split each record |
| `minimumSimilarity` | Minimum similarity threshold for reporting matches |
| `shingleSize` | Number of tokens per shingle |
| `hashCount` | Number of hash functions used for MinHash signatures |
| `bandSize` | Number of MinHash values per LSH band |
| `doJaccard` | Whether to compute exact Jaccard similarity |
| `doAllMinHashes` | Whether to compute all-pairs MinHash similarity |
| `printHashCoefficients` | Whether to print generated hash coefficients |

## Example Output

The program reports matching pairs and their similarity values across different methods:

```text
Pair                  Jaccard              minHash                  lsh
-----------------------------------------------------------------------
record1,record2       0.85000              0.83000              0.82000
record3,record7       0.91000              0.90000              0.89000
-----------------------------------------------------------------------
```

## Key Concepts

### Jaccard Similarity

Jaccard similarity measures exact overlap between two sets.

It is accurate but expensive when applied to every pair in a large dataset.

### MinHash

MinHash approximates Jaccard similarity by comparing compact signatures instead of full sets.

This reduces computation while preserving similarity patterns.

### Locality-Sensitive Hashing

LSH reduces the number of candidate comparisons by grouping similar MinHash signatures into the same buckets.

This helps scale similarity search to larger datasets.

## Skills Demonstrated

- Scala programming
- Apache Spark development
- Spark RDD transformations
- Large-scale set similarity comparison
- Jaccard similarity
- MinHash implementation
- Locality-sensitive hashing
- Shingling text/token records
- Hash function generation
- Approximate similarity search
- Distributed data processing

## Resume Summary

This project demonstrates experience implementing scalable set similarity comparison methods using Scala and Apache Spark. It applies shingling, MinHash, Jaccard similarity, and locality-sensitive hashing to efficiently identify similar pairs in large collections of records.
