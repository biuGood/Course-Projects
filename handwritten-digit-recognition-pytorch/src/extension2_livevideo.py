#

# Your name here and a short header
# Hongyu Wan
# import statements
import torch
from torch.utils.data import Dataset
from torch.utils.data import DataLoader
from task1_main import *
from torchvision import transforms
from task1_main import *
import cv2
import pandas as pd
import numpy as np


# class definitions


# useful functions with a comment for each function
def change_datatype(input_image):
    input_image = np.array(input_image)
    input_image_x = input_image.astype(float)
    input_image_t = torch.tensor(input_image_x)
    input_image_t = input_image_t.unsqueeze(1)
    input_image_y = torch.tensor(input_image_t, dtype=torch.float)
    return input_image_y

def pro_example(test_loader):
    examples = enumerate(test_loader)
    batch_idx, (example_data) = next(examples)
    return batch_idx, example_data
# main function (yes, it needs a comment too)
def main(argv):
    # handle any command line arguments in argv
    batch_size_train = 64
    batch_size_test = 10
    learning_rate = 0.01
    momentum = 0.5
    torch.backends.cudnn.enabled = False
    torch.manual_seed(42)
    # interval when printing results
    interval = 0

    continued_network = Net()
    continued_optimizer = optim.SGD(continued_network.parameters(), lr=learning_rate,
                                    momentum=momentum)
    network_state_dict = torch.load('../results/networkTrained/model.pth')
    continued_network.load_state_dict(network_state_dict)
    optimizer_state_dict = torch.load('../results/networkTrained/optimizer.pth')
    continued_optimizer.load_state_dict(optimizer_state_dict)
    cap = cv2.VideoCapture(0)
    while 1:
        interval += 1
        ret, frame = cap.read()

        frame = cv2.resize(frame, (280, 280))
        cv2.imshow("source", frame)

        frame = cv2.cvtColor(frame, cv2.COLOR_RGB2GRAY)
        res, frame = cv2.threshold(frame, 90, 255, cv2.THRESH_BINARY_INV)
        cv2.imshow("gray", frame)

        frame = cv2.resize(frame, (140, 140))
        cv2.imshow("28*28", frame)
        cv2.waitKey(100)

        testimg = cv2.resize(frame, (28, 28))
        # batch_idx, example_data = pro_example(testimg)# 转换成28*28大小
        testimg = torch.Tensor(testimg)
        testimg = torch.unsqueeze(testimg, dim=0)
        testimg = torch.unsqueeze(testimg, dim=0)
        # testimg = change_datatype(testimg)
        continued_network.eval()
        with torch.no_grad():
            output = continued_network(testimg)
            predict = output.data.max(1, keepdim=True)[1]
            if interval % 10 == 0:
                print("The Prediction of the input image is: \t", predict[0][0].item())


if __name__ == "__main__":
    main(sys.argv)