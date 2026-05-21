# Dimensionality Reduction | MATLAB

This project explores dimensionality reduction techniques for high-dimensional classification problems using MATLAB. The project compares Principal Component Analysis (PCA), Fisher Linear Discriminant (FLD), Bayes classification, and neural network classification on a high-dimensional dataset.

## Project Overview

High-dimensional datasets can create challenges for classification because the available samples may not be dense enough across all dimensions. This project investigated how dimensionality reduction can be used to simplify the feature space while still preserving useful classification information.

The original dataset contained:

- 1000 samples
- 2 classes
- 100 measurement dimensions

The project applied PCA and FLD to reduce the dimensionality of the data and then evaluated classification performance using Bayes classification and a neural network classifier.

## Tools Used

- MATLAB
- MATLAB Live Script
- Principal Component Analysis
- Fisher Linear Discriminant
- Bayes classification
- Neural network classifier
- Training/test data partitioning

## Methods

### 1. Data Preparation

The dataset was randomly divided into:

- 25% training data
- 75% test data

The training data was used to learn dimensionality reduction models, and the test data was used to evaluate classification performance.

### 2. PCA Dimensionality Reduction

Principal Component Analysis was applied to reduce the original 100-dimensional data into 3 dimensions.

PCA identifies directions of maximum variance in the data. However, because PCA is unsupervised, it does not directly optimize for class separation.

### 3. Bayes Classification after PCA

After reducing the data with PCA, Bayes classification was applied to the reduced test dataset. The classification performance was then evaluated to understand how well PCA preserved useful information for classification.

### 4. Fisher Linear Discriminant

Fisher Linear Discriminant was applied to reduce the data to 1 dimension.

Unlike PCA, FLD is supervised and uses class labels to find a projection direction that better separates the classes.

### 5. Bayes Classification after FLD

Bayes classification was applied again after FLD-based dimensionality reduction. This allowed the project to compare PCA and FLD in terms of classification performance.

### 6. Neural Network Classification

A neural network classifier was trained on the original dataset using predefined training, validation, and test indices.

The neural network result was compared against the PCA-based and FLD-based classification results.

## Key Learning Points

- PCA is useful for reducing dimensionality but may not be ideal for classification because it focuses on variance rather than class separation
- FLD is often more suitable for classification because it uses class labels to find discriminative directions
- Bayes classification can be used to evaluate the effectiveness of reduced feature spaces
- Neural networks can classify high-dimensional data, but they may provide less direct interpretability than FLD
- Dimensionality reduction can help address the curse of dimensionality and improve understanding of feature structure

## Files

| File | Description |
|---|---|
| `hw04.mlx` | Main MATLAB Live Script for PCA, FLD, and classification analysis |
| `Ex4_NNsetup.m` | MATLAB function for setting up and training the neural network classifier |
| `Exercise_4_Data.mat` | Main dataset used for dimensionality reduction and classification |
| `Exercise_4_Data_extra.mat` | Additional dataset used for the exercise |

## Skills Demonstrated

- MATLAB programming
- Dimensionality reduction
- PCA
- Fisher Linear Discriminant
- Bayes classification
- Neural network classification
- High-dimensional data analysis
- Training/test data partitioning
- Classification performance evaluation

## Resume Summary

This project demonstrates experience in applying dimensionality reduction techniques to high-dimensional classification problems and evaluating model performance using MATLAB.
