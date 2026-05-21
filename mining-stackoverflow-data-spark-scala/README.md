# Mining Stack Overflow Data | Scala & Spark

This project analyzes Stack Overflow programming language data using Scala and Apache Spark. The goal was to identify language usage patterns, discover frequent language combinations, and explore relationships among programming languages through machine learning methods.

## Project Overview

Stack Overflow data contains rich information about users, posts, tags, and programming languages. This project processes Stack Overflow post and tag data to identify users who work with multiple programming languages, then applies machine learning methods to analyze language relationships.

The project includes:

- Data preprocessing from Stack Overflow posts and tags
- Building user-language baskets
- Frequent pattern mining with FP-Growth
- Association rule generation
- LDA-based topic modeling for programming language relationships
- Conversion of basket data into document-style input for Spark ML

## Tools and Technologies

- Scala
- Apache Spark
- Spark RDD
- Spark SQL
- Spark MLlib
- Spark ML
- FP-Growth
- LDA topic modeling
- CSV data processing

## Dataset Concept

The analysis is based on Stack Overflow-style data files, including:

- Programming language index file
- Post-tag relationships
- Stack Overflow posts
- User-language baskets

The workflow identifies programming languages used by Stack Overflow users and analyzes which languages tend to appear together.

## Main Workflow

### 1. Data Preprocessing

The preprocessing step reads Stack Overflow posts, tags, and programming language files.

The logic includes:

- Reading a language reference file
- Filtering tags to keep only programming language tags
- Filtering posts with valid user IDs and post IDs
- Connecting posts with language tags
- Ignoring posts tagged with multiple programming languages
- Ignoring users who used only one programming language
- Creating baskets of languages for users who used multiple languages

The output is a basket-style dataset where each row represents one user and the set of programming languages associated with that user.

Example output format:

```text
user_id,language_1,language_2,language_3
```

### 2. Basket Analysis with FP-Growth

FP-Growth is used to identify frequent programming language combinations.

The process includes:

- Reading user-language baskets
- Mapping language indexes to language names
- Training an FP-Growth model
- Setting minimum support
- Generating frequent itemsets
- Generating association rules
- Sorting results by frequency
- Writing frequent language combinations to an output file

This helps answer questions such as:

- Which programming languages are commonly used together?
- What language combinations appear most frequently among users?
- Are there patterns that suggest common learning or usage paths?

### 3. Document Preparation for LDA

The project also converts basket data into a document-style representation suitable for Spark ML.

Each user basket is transformed into a sparse feature representation where programming languages are represented by numeric indexes.

Example format:

```text
user_id language_index:1 language_index:1 language_index:1
```

This format allows the data to be loaded as machine learning input for topic modeling.

### 4. LDA Topic Modeling

Latent Dirichlet Allocation is applied to identify hidden relationships among programming languages.

The LDA model groups programming languages into topics based on co-occurrence patterns in user baskets.

The project:

- Loads the processed document dataset
- Trains an LDA model
- Extracts top-weighted terms for each topic
- Maps language indexes back to language names
- Prints language clusters/topics with meaningful weights

This can reveal groups of languages that commonly appear together in user activity.

## Files

| File | Description |
|---|---|
| `basket.scala` | Spark-based preprocessing script that creates user-language baskets from posts, tags, and language data |
| `RDK.scala` | Alternative RDD-based preprocessing script for generating user-language basket data |
| `pre.scala` | Converts basket data into indexed document format for machine learning input |
| `BasketsN.scala` | Runs FP-Growth to find frequent programming language combinations |
| `topics.scala` | Runs LDA topic modeling to identify language relationship topics |
| `baskets.scala` | Basic Spark application skeleton / starter file |

## Suggested Project Structure

```text
mining-stackoverflow-data-spark-scala/
│
├── README.md
├── build.sbt
├── .gitignore
│
└── src/
    └── main/
        └── scala/
            └── stackoverflow/
                ├── basket.scala
                ├── RDK.scala
                ├── pre.scala
                ├── BasketsN.scala
                ├── topics.scala
                └── baskets.scala
```

## How to Run

This project is designed as a Scala/Spark project.

Compile the project with SBT:

```bash
sbt compile
```

Run the preprocessing step:

```bash
sbt "runMain stackoverflow.basket"
```

Run FP-Growth basket analysis:

```bash
sbt "runMain stackoverflow.Baskets_4 baskets.txt data/languages.csv"
```

Run document preprocessing for LDA:

```bash
sbt "runMain stackoverflow.pre"
```

Run LDA topic modeling:

```bash
sbt "runMain stackoverflow.Topics documents.txt data/languages.csv"
```

The exact input filenames may need to be adjusted depending on the local `data/` folder structure.

## Example Data Files

The scripts reference files such as:

```text
./data/data/languages.csv
./data/data/poststags.csv
./data/data/posts.csv
./data/baskets.txt
./data/documents.txt
```

Large raw data files are not included in this repository. The code expects Stack Overflow-style CSV files with post, tag, and language information.

## Key Methods

### FP-Growth

FP-Growth is used to perform frequent pattern mining on user-language baskets.

In this project, each basket represents a user and the programming languages associated with that user. Frequent itemsets show which languages often appear together.

### Association Rules

Association rules are generated from frequent itemsets to identify relationships between languages.

For example, if many users who use one language also use another, the rule may suggest a common language pairing.

### LDA Topic Modeling

LDA is used as an unsupervised topic modeling method to discover hidden groups of programming languages.

Instead of treating each language pair individually, LDA identifies broader clusters of languages that tend to appear together across users.

## Skills Demonstrated

- Scala programming
- Apache Spark development
- Spark RDD transformations
- Spark SQL data processing
- Large-scale CSV data preprocessing
- Basket analysis
- FP-Growth frequent pattern mining
- Association rule mining
- LDA topic modeling
- Unsupervised machine learning
- Feature preparation for machine learning
- Data pipeline design

## Resume Summary

This project demonstrates experience mining Stack Overflow data using Scala and Apache Spark. It includes preprocessing user-language activity data, applying FP-Growth for basket analysis, and using LDA topic modeling to identify programming language relationship patterns.
