import matplotlib.pyplot as plt

FILE_PATH = '../../data/experiment/alphaAliveTrees.txt'

f = open(FILE_PATH, 'r')
line = f.readline().strip()
alpha_mins = []
alpha_maxs = []
alive_trees = []
errors = []
while line != "":
    params = line.split(';')
    print(params)
    alpha_mins.append(float(params[2]))
    alpha_maxs.append(float(params[1]))
    line = f.readline().strip()
    params = line.split(';')
    alive_trees.append(float(params[0]))
    errors.append(float(params[1]))
    line = f.readline()
    line = f.readline()

plt.rcParams["figure.figsize"] = (16, 16)
plt.rcParams.update({'font.size': 20})
plt.figure()
plt.title("Árboles vivos después del fuego")
plt.ylabel("Cantidad de árboles vivos")
labels = []
for i in range(0, len(alive_trees)):
    labels.append("{:.2f} - {:.2f}".format(alpha_mins[i], alpha_maxs[i]))
plt.bar(labels, alive_trees,yerr=errors,ecolor='red',capsize=12)
plt.show()
