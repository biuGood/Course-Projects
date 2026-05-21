# Music Auto Generation | Python & Keras

This project uses a deep learning model to generate new piano-style MIDI music. The model is based on Long Short-Term Memory (LSTM) neural networks, which are suitable for learning sequential patterns and long-term dependencies in music data.

## Project Overview

Music is naturally sequential: each note depends on the notes and chords that came before it. This project trains an LSTM-based neural network on MIDI files and uses the trained model to generate new musical sequences.

The workflow includes:

- Parsing MIDI files
- Extracting notes and chords
- Converting musical events into numerical sequences
- Training an LSTM neural network
- Saving model weights during training
- Generating new note/chord sequences
- Exporting generated music as a MIDI file

## Tools and Technologies

- Python
- Keras
- TensorFlow
- Music21
- NumPy
- h5py
- MIDI file processing
- LSTM neural networks

## Model Architecture

The neural network uses a sequential Keras model with:

- Three LSTM layers with 512 units
- Recurrent dropout to reduce overfitting
- Batch normalization
- Dropout layers
- Dense hidden layer
- Softmax output layer for predicting the next note or chord

The model is trained using categorical cross-entropy loss and the RMSprop optimizer.

## How It Works

### 1. MIDI Parsing

The training script reads MIDI files from the `midi_songs/` folder.

Each MIDI file is parsed using Music21. The program extracts:

- Individual notes
- Chords represented by their normal order

The extracted notes and chords are saved into a serialized file under the `data/` folder.

### 2. Sequence Preparation

The model uses a fixed sequence length of 100.

For each training example:

- The input is a sequence of 100 notes/chords
- The output is the next note/chord after that sequence

Each unique note or chord is mapped to an integer, then normalized before being passed into the LSTM model.

### 3. Model Training

The model learns to predict the next musical event based on the previous 100 events.

During training, model checkpoints are saved so the latest completed weights can be reused for generation.

### 4. Music Generation

The prediction script loads the trained model weights and selects a random starting sequence from the training data.

It then repeatedly predicts the next note/chord and appends it to the generated sequence.

The program generates 500 notes/chords and converts them into a MIDI file.

### 5. MIDI Output

The generated notes and chords are written into a new MIDI file:

```text
test_output.mid
```

## Files

| File | Description |
|---|---|
| `lstm.py` | Trains the LSTM model using MIDI files from the `midi_songs/` folder |
| `predict.py` | Loads trained weights and generates a new MIDI music file |
| `weights.hdf5` | Pretrained model weights used for music generation |
| `new_weights.hdf5` | Additional trained weights / checkpoint file |
| `.gitattributes` | Git attribute configuration, useful for tracking large model files |
| `LICENSE` | Project license |
| `README.md` | Project documentation |

## Suggested Project Structure

```text
music-generation-lstm-keras/
│
├── README.md
├── lstm.py
├── predict.py
├── weights.hdf5
├── new_weights.hdf5
├── .gitattributes
├── LICENSE
│
├── data/
│   └── notes
│
└── midi_songs/
    └── example.mid
```

If the MIDI training files are large or copyrighted, they should not be included in the repository. Users can add their own MIDI files to the `midi_songs/` folder before training.

## Requirements

Install the required packages:

```bash
pip install music21 keras tensorflow h5py numpy
```

## How to Train the Model

Place MIDI files in the `midi_songs/` folder.

Then run:

```bash
python lstm.py
```

The script will:

1. Parse MIDI files
2. Extract notes and chords
3. Prepare training sequences
4. Train the LSTM model
5. Save model checkpoints as `.hdf5` weight files

## How to Generate Music

After training, run:

```bash
python predict.py
```

The script will:

1. Load the saved notes from `data/notes`
2. Rebuild the LSTM model
3. Load trained weights from `weights.hdf5`
4. Generate a new sequence of notes and chords
5. Save the result as `test_output.mid`

## Example Output

```text
test_output.mid
```

The output file can be opened with MIDI players, music production software, or notation software that supports MIDI files.

## Notes on Model Weights

The `.hdf5` files contain trained neural network weights. If these files are too large for normal GitHub upload, they should be tracked with Git LFS or excluded from the repository.

If the weights are not included, users can still train the model from scratch by running:

```bash
python lstm.py
```

## Limitations

- The quality of generated music depends heavily on the MIDI training data
- Training can take a long time depending on hardware
- The model predicts note/chord events but does not explicitly understand music theory
- Generated music may contain repetition or inconsistent musical structure
- Better results may require more training data, longer training time, or additional model tuning

## Skills Demonstrated

- Python programming
- Deep learning with Keras
- LSTM neural networks
- Sequential data modeling
- MIDI data preprocessing
- Music generation
- Model checkpointing
- Categorical sequence prediction
- Neural network training and inference

## Resume Summary

This project demonstrates experience building an LSTM-based deep learning model for automatic music generation. The model learns sequential patterns from MIDI files and generates new music by predicting notes and chords from prior musical context.
