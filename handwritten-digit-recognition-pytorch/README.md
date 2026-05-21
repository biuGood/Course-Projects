# Handwritten Digit Recognition using Deep Networks | PyTorch

This project implements a handwritten digit recognition system using deep neural networks in PyTorch. The main model is trained and tested on the MNIST dataset, and additional extensions explore custom handwritten image recognition, convolutional filter visualization, Greek symbol recognition, live video prediction, hyperparameter experiments, and deeper CNN architectures.

## Project Overview

The goal of this project was to build, train, evaluate, and analyze a convolutional neural network for handwritten digit recognition.

The project includes:

- Training a CNN on the MNIST handwritten digit dataset
- Testing the trained model on unseen test images
- Loading and evaluating saved model checkpoints
- Classifying custom handwritten digit images
- Visualizing convolutional filters and feature maps
- Creating an embedding-space approach for Greek symbol recognition
- Running live webcam-based digit prediction
- Experimenting with different CNN architectures and hyperparameters

## Tools and Technologies

- Python
- PyTorch
- Torchvision
- NumPy
- Matplotlib
- OpenCV
- PIL
- CSV
- MNIST dataset

## Model Architecture

The main CNN model contains:

- A first convolutional layer with 10 filters
- A second convolutional layer with 20 filters
- Max pooling
- ReLU activation
- Dropout regularization
- A fully connected layer with 50 hidden units
- A final fully connected output layer with 10 classes
- Log Softmax output for digit classification

The model is trained using negative log-likelihood loss and stochastic gradient descent.

## Main Workflow

### 1. Load MNIST Dataset

The project uses Torchvision to download and load the MNIST dataset. The images are transformed into tensors and normalized before being passed into the model.

### 2. Train CNN Model

The CNN is trained on the MNIST training set using:

- Batch size: 64
- Learning rate: 0.01
- Momentum: 0.5
- Epochs: 5

During training, the model records training loss and saves the trained model and optimizer checkpoints.

### 3. Test Model Performance

The trained model is evaluated on the MNIST test set. The program prints the average test loss and classification accuracy.

### 4. Visualize Training Results

Matplotlib is used to visualize:

- Sample MNIST images
- Predicted labels
- Training loss
- Test loss

### 5. Load Saved Model

A separate script loads the trained model checkpoint and evaluates the network on test images without retraining.

### 6. Classify Custom Handwritten Images

The project includes a custom dataset class for loading handwritten images from a local folder. These images are converted to grayscale, resized to 28x28, normalized, and passed into the trained model for prediction.

### 7. Analyze Convolutional Filters

The project examines learned convolutional filters by:

- Extracting model weights
- Visualizing convolutional filters
- Applying filters to sample images
- Building a truncated model to inspect intermediate feature representations

### 8. Greek Symbol Recognition Extension

The project also applies the trained network as a feature extractor for Greek symbol recognition.

The workflow includes:

- Creating a Greek symbol dataset from image files
- Preprocessing images into 28x28 grayscale format
- Passing images through a truncated CNN
- Projecting images into an embedding space
- Comparing images using distance-based matching

### 9. Live Video Prediction Extension

An OpenCV-based extension captures webcam frames, preprocesses them into grayscale 28x28 images, and uses the trained CNN model to predict handwritten input in real time.

### 10. Architecture and Hyperparameter Experiments

Additional experiments include:

- Testing different batch sizes
- Testing different numbers of epochs
- Testing different learning rates
- Testing different dropout rates
- Saving experiment results to CSV
- Building a deeper multi-convolution CNN model

## Files

| File | Description |
|---|---|
| `task1_main.py` | Main CNN model definition, MNIST loading, training, testing, and loss visualization |
| `task1_testset.py` | Loads the trained model and evaluates it on MNIST test samples |
| `task1_handwritting.py` | Tests the trained model on custom handwritten digit images |
| `task2_analysis.py` | Visualizes convolutional filters and analyzes intermediate CNN representations |
| `task3_main.py` | Creates an embedding-space approach for Greek symbol recognition |
| `task4_main.py` | Runs hyperparameter experiments and saves results |
| `task4_multiconv.py` | Implements a deeper CNN model with multiple convolutional layers |
| `extension1_classifier.py` | Extended Greek symbol classifier using KNN-style matching in embedding space |
| `extension2_livevideo.py` | Uses OpenCV webcam input for live handwritten digit prediction |
| `extension3_resnet.py` | Explores pretrained ResNeXt / ResNet-style convolutional filter analysis |

## How to Run

Install the required dependencies:

```bash
pip install torch torchvision numpy matplotlib opencv-python pillow pandas
```

Train the main CNN model:

```bash
python task1_main.py
```

Test the saved model on MNIST test images:

```bash
python task1_testset.py
```

Run prediction on custom handwritten images:

```bash
python task1_handwritting.py
```

Analyze convolutional filters:

```bash
python task2_analysis.py
```

Run Greek symbol embedding-space recognition:

```bash
python task3_main.py
```

Run hyperparameter experiments:

```bash
python task4_main.py
```

Run the deeper multi-convolution CNN model:

```bash
python task4_multiconv.py
```

Run live video prediction:

```bash
python extension2_livevideo.py
```

## Project Structure

```text
handwritten-digit-recognition-pytorch/
│
├── README.md
├── task1_main.py
├── task1_testset.py
├── task1_handwritting.py
├── task2_analysis.py
├── task3_main.py
├── task4_main.py
├── task4_multiconv.py
├── extension1_classifier.py
├── extension2_livevideo.py
└── extension3_resnet.py
```

## Skills Demonstrated

- Python programming
- PyTorch model development
- CNN architecture design
- Image preprocessing
- Handwritten digit recognition
- Model training and testing
- Model checkpoint loading
- Loss and accuracy visualization
- Convolutional filter visualization
- Feature extraction
- Embedding-space classification
- Hyperparameter experimentation
- OpenCV real-time video processing

## Resume Summary

This project demonstrates experience building and evaluating deep learning models for image classification using PyTorch. It includes CNN training on MNIST, custom handwritten image prediction, convolutional filter analysis, Greek symbol recognition through embedding-space matching, and live video-based digit prediction.
