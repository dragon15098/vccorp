from pyspark import SparkContext, SparkConf
from scipy.spatial.distance import cdist
import matplotlib.pyplot as plt
import numpy as np
import random

def kmean_init(data):
	a0 = random.randint(0, len(data)-1)
	a1 = random.randint(0, len(data)-1)
	a2 = random.randint(0, len(data)-1)
	return [data[a0], data[a1], data[a2]]

def kmean_assign_labels(centers, data):
	distance = cdist(data, centers)
	return np.argmin(distance, axis = 1) 

def not_change(centers, new_centers):
	if len(centers) != len(new_centers): 
		print("ERROR")
		return False
	for index in range(len(centers)):
		if centers[index][0] != new_centers[index][0] or  centers[index][1] != new_centers[index][1]:
	 		return False
	return True
	
def kmean_update_centers(data, labels, k, centers):
	result = []
	for i in range(k):
		#print (type(data[0]))
		data_sum = []
		for index in range(len(labels)):
			if(labels[index] == i) :
				data_sum.append(data[index])
		sum_x = 0
		sum_y = 0
		if(data_sum):
			for value in data_sum:
				sum_x += int(value[0])
				sum_y += int(value[1])
			mean_x = sum_x / len(data_sum)
			mean_y = sum_y / len(data_sum)
			result.append([mean_x, mean_y])
		else:
			result.append(centers[i])
	return result
	
def kmean_display(data, labels, k):
	X0 = []
	X1 = []
	X2 = []
	for i in range(len(data)):
		if labels[i] == 0:
			X0.append(data[i])
		elif labels[i] == 1:
			X1.append(data[i])
		else:
			X2.append(data[i])
	X0 = np.array(X0)
	X1 = np.array(X1)
	X2 = np.array(X2)
	plt.plot(X0[:, 0], X0[:, 1], 'b^', markersize = 4, alpha = .8)
	plt.plot(X1[:, 0], X1[:, 1], 'go', markersize = 4, alpha = .8)
	plt.plot(X2[:, 0], X2[:, 1], 'rs', markersize = 4, alpha = .8)

	plt.axis('equal')
	plt.plot()
	plt.show()
	
def kmean_start():
	conf = (SparkConf()
		 .setMaster("local")
		 .setAppName("My app")
		 .set("spark.executor.memory", "2g"))
	sc = SparkContext(conf = conf)
	point_str = sc.textFile("/user/nmq/kmean_data/data.txt")
	data = point_str.map(lambda x: [x.split("\t")[0], x.split("\t")[1]]).collect()
	centers = kmean_init(data)
	labels = []
	k = 3
	while True:
		labels = kmean_assign_labels(centers, data)
		new_centers = kmean_update_centers(data, labels, k, centers)
		if not_change(centers, new_centers):
			kmean_display(data, labels, k)
			print(centers)
			break
		centers = new_centers
kmean_start()
