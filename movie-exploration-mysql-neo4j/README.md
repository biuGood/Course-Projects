# Movie Exploration | MySQL & Neo4j

This project explores a movie recommendation workflow using the Sakila Sample Database. The goal was to analyze a customer's rental history and identify other movies that may match their preferences based on country, favorite movie category, and favorite actor.

## Project Overview

The project was designed to answer the following question:

> Based on a user's rental history, country, favorite category, and favorite actor, how can we find related movies and inventory information efficiently?

The project first modeled the data using MySQL, then transformed the relational structure into a graph model in Neo4j to support relationship-based queries.

## Tools Used

- MySQL
- Neo4j
- Cypher
- Sakila Sample Database
- Relational data modeling
- Graph data modeling

## Dataset

The project used the Sakila Sample Database, which contains movie rental data such as customers, films, actors, categories, inventory, rental records, addresses, cities, and countries.

## Data Modeling

The project focused on several key entities:

- Customer
- Country
- Inventory
- Film
- Actor
- Category

Main relationships included:

- A customer lives in one country
- A customer rents inventories
- An inventory is related to one film
- A film belongs to a category
- A film has many actors

## MySQL Implementation

In MySQL, selected tables were created from the Sakila database to simplify the model and support the project requirements.

Examples of generated tables included:

- `customers`
- `countries`
- `inventories`
- `films`
- `actors`
- `categories`
- `rented`
- `lives_in`
- `acted_in`
- `has`
- `is_related_to`

Primary keys and foreign keys were added to maintain data integrity and support relational queries.

## Neo4j Graph Model

After preparing the relational tables in MySQL, the data was mapped into Neo4j as a graph model.

The graph model allowed the project to represent relationships such as:

- Customer → Country
- Customer → Inventory
- Inventory → Film
- Film → Actor
- Film → Category

This made it easier to query connected movie information based on user preferences.

## Query Logic

The analysis was divided into two main steps:

### 1. Identify user preferences

The project first identified the user's:

- Country
- Most frequently rented movie category
- Most frequently watched actor

### 2. Explore related movies

After identifying the user's preferred category and actor, the project searched for other films that matched those conditions and were rented by customers from the same country.

## Key Results

The project successfully used Neo4j Cypher queries to:

- Find the user's country
- Identify the user's favorite category
- Identify the user's favorite actor
- Retrieve related movie titles, film IDs, and store IDs

## Skills Demonstrated

- Relational database design
- Logical data modeling
- MySQL table creation and foreign key relationships
- Graph database modeling
- Neo4j mapping
- Cypher query writing
- Recommendation-style data exploration
- Query optimization through relationship-based modeling

## Resume Summary

This project demonstrates experience in relational database design, graph database modeling, and query-based data exploration using MySQL and Neo4j.
