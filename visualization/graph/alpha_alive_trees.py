import matplotlib.pyplot as plt

FILE_PATH = '../../data/experiment/alphaAliveTrees.txt'

f = open(FILE_PATH, 'r')
line = f.readline().strip()
alpha_mins = []
alpha_maxs = []
alive_trees = []
while line != "":
    params = line.split('-')
    print(params)
    alpha_mins.append(float(params[1]))
    alpha_maxs.append(float(params[2]))
    line = f.readline().strip()
    alive_trees.append(float(line))
    line = f.readline()
    line = f.readline()

plt.rcParams["figure.figsize"] = (10, 10)
plt.rcParams.update({'font.size': 12})
plt.figure()
plt.title("Árboles vivos después del fuego")
plt.ylabel("Cantidad de árboles vivos")
labels = []
for i in range(0, len(alive_trees)):
    labels.append("{:.2f} - {:.2f}".format(alpha_mins[i], alpha_maxs[i]))
plt.bar(labels, alive_trees)
plt.show()
