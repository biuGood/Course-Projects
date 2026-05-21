# 3. Create a digit embedding space

# Your name here and a short header
# Hongyu Wan
# import statements
from task1_main import *
import csv
import math
from collections import Counter
import os
from PIL import Image
import numpy as np
# class definitions
class Greek_Submodel(Net):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)

    # override the forward method
    def forward(self, x):
        # A max pooling layer with a 2x2 window and a ReLU function applied.
        x = F.relu(F.max_pool2d(self.conv1(x), 2))
        # A dropout layer with a 0.5 dropout rate (50%)
        # A max pooling layer with a 2x2 window and a ReLU function applied
        x = F.relu(F.max_pool2d(self.conv2_drop(self.conv2(x)), 2))
        x = x.view(-1, 320)
        # ReLU function,fully connected Linear layer
        x = F.relu(self.fc1(x))
        x = F.dropout(x, training=self.training)
        return F.log_softmax(x)


# useful functions with a comment for each function
def process_example(test_loader):
    examples = enumerate(test_loader)
    batch_idx, (example_data, example_targets) = next(examples)
    return batch_idx, example_data, example_targets


def create_dataset(path, dataset_filepath, label_filepath):
    files = os.listdir(path)
    with open(dataset_filepath, "w", newline='') as f:
        writer = csv.writer(f)
        writer.writerow(['dataset'])
    for file in files:
        position = path + '\\' + file
        img = Image.open(position)
        img = img.resize((28, 28), Image.NEAREST)
        img = img.convert('LA')
        img = np.array(img)
        img = img[:, :, 0]
        for x in range(28):
            for y in range(28):
                if (img[x][y] > 150):
                    img[x][y] = 255
                else:
                    img[x][y] = 0
        with open(dataset_filepath, "a", newline='') as f:
            writer = csv.writer(f)
            writer.writerow(img.flatten())
    with open(label_filepath, "w", newline='') as f:
        writer = csv.writer(f)
        writer.writerow(['label(alpha = 0, beta = 1, gamma = 2, pi = 3, theta = 4, mu = 5)'])
        for file in files:
            #         position = path+'\\'+ file
            if "alpha" in file: writer.writerow('0')
            if "beta" in file: writer.writerow('1')
            if "gamma" in file: writer.writerow('2')
            if 'pi' in file: writer.writerow('3')
            if 'theta' in file: writer.writerow('4')
            if 'mu' in file: writer.writerow('5')
    return


def read_dataset(dataset_filepath, label_filepath):
    # read greek dataset（27*28*28）
    label = []
    dataset = []
    with open(dataset_filepath, "r", newline='') as f:
        head_row = next(f)
        reader = csv.reader(f)
        data = list(reader)
        data = np.array(data)
        dataset = []
        for line in data:
            arr_2d = np.reshape(line, (28, 28))
            dataset.append(arr_2d)

    dataset = np.array(dataset)

    # read label
    with open(label_filepath, "r", newline='') as f:
        head_row = next(f)
        reader = csv.reader(f)
        # label = list(reader)
        for row in reader:
            label.append(row[0])
        label = np.array(label)
    return dataset, label


# get new_greekdata from file
def get_test_greekdata(path):
    files = os.listdir(path)
    input_image = []
    raw_image = []
    for file in files:
        position = path + '\\' + file
        img = Image.open(position)
        img = img.resize((28, 28), Image.NEAREST)
        raw_img = img
        raw_img = np.array(raw_img)
        raw_image.append(raw_img)
        img = img.convert('LA')
        img = np.array(img)
        img = img[:, :, 0]
        for x in range(28):
            for y in range(28):
                if (img[x][y] > 150):
                    img[x][y] = 255
                else:
                    img[x][y] = 0
        input_image.append(img)
    return input_image, raw_image


def change_datatype(input_image):
    input_image = np.array(input_image)
    input_image_x = input_image.astype(float)
    input_image_t = torch.tensor(input_image_x)
    input_image_t = input_image_t.unsqueeze(1)
    input_image_y = torch.tensor(input_image_t, dtype=torch.float)
    return input_image_y


def get_minvalue(distance, label):
    smallest_three = []
    knn_label = []
    distance_list = distance.copy()
    distance_list.sort()
    for i in range(3):
        smallest_three.append(distance_list[i])
        print(distance.index(distance_list[i]))
        knn_label.append(label[distance.index(distance_list[i])])
        # print(smallest_three)
    collection_num = Counter(knn_label)
    most_counterNum = collection_num.most_common(1)
    most_counterNum = np.array(most_counterNum)
    return most_counterNum[0][0]


def compute_knn_testset(tensor2array, new_output, label):
    value_all = []
    for i in range(len(tensor2array)):
        value = math.sqrt(sum((new_output - tensor2array[i]) * (new_output - tensor2array[i])))
        value_all.append(value)
    min_index = get_minvalue(value_all, label)
    return min_index


# main function (yes, it needs a comment too)
def main(argv):
    greek_symbol = ['alpha', 'beta', 'gamma', 'pi', 'theta', 'mu']
    learning_rate = 0.01
    momentum = 0.5

    torch.backends.cudnn.enabled = False
    torch.manual_seed(42)
    # A. Create a greek symbol data set
    create_dataset('../extension/ext_train', '../extension/dataset.csv', '../extension/label.csv')

    # B. Create a truncated model
    Greek_submodel = Greek_Submodel()
    Greek_submodel_optimizer = optim.SGD(Greek_submodel.parameters(), lr=learning_rate,
                                         momentum=momentum)
    # load model
    network_state_dict = torch.load('../results/networkTrained/model.pth')
    Greek_submodel.load_state_dict(network_state_dict)

    optimizer_state_dict = torch.load('../results/networkTrained/optimizer.pth')
    Greek_submodel_optimizer.load_state_dict(optimizer_state_dict)

    # D. Project the greek symbols into the embedding space
    dataset, label = read_dataset('../extension/dataset.csv', '../extension/label.csv')
    dataset_y = change_datatype(dataset)
    Greek_submodel.eval()
    with torch.no_grad():
        output_greek = Greek_submodel(dataset_y)
    output_array = output_greek.numpy()

    # E. Get test dataset
    input_image, raw_img = get_test_greekdata('../extension/ext_test')
    input_image_y = change_datatype(input_image)
    Greek_submodel.eval()
    with torch.no_grad():
        output_new = Greek_submodel(input_image_y)

    # KNN

    for pic in range(len(output_new)):
        min_index = compute_knn_testset(output_array, output_new[pic], label)
        plt.subplot(3, 4, pic + 1)
        plt.tight_layout()
        plt.imshow(raw_img[pic], cmap='gray', interpolation='none')
        plt.title("Prediction: {}".format(greek_symbol[int(min_index)]))
        plt.xticks([])
        plt.yticks([])
    plt.show()


if __name__ == "__main__":
    main(sys.argv)
