import matplotlib.pyplot as plt

FILE_PATH = '../../data/experiment/ratioAliveTrees.txt'

f = open(FILE_PATH, 'r')
line = f.readline().strip()
ratio = []
alive_trees = []
while line != "":
    params = line.split('-')
    ratio.append(float(params[1]))
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
    labels.append("{:.0%}".format( ratio[i]))
plt.bar(labels, alive_trees)
plt.show()
