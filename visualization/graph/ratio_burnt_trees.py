import matplotlib.pyplot as plt

FILE_PATH = '../../data/experiment/ratioBurnTree.txt'

f = open(FILE_PATH, 'r')
line = f.readline().strip()
ratio = []
times = []
burnt_trees = []
while line != "":
    params = line.split('-')
    ratio.append(float(params[1]))
    line = f.readline().strip()
    times.append([])
    burnt_trees.append([])
    while line != "":
        record = line.split(':')
        times[-1].append(float(record[0]))
        burnt_trees[-1].append(float(record[1]))
        line = f.readline().strip()
    line = f.readline()

plt.rcParams["figure.figsize"] = (10, 10)
plt.rcParams.update({'font.size': 12})
plt.figure()
plt.title("Árboles quemados a través del tiempo")
plt.ylabel("Árboles quemados")
plt.xlabel("Tiempo (min)")
labels = []
for i in range(0, len(times)):
    plt.plot(times[i], burnt_trees[i], label=("{:.0%}".format(ratio[i])))
plt.legend()
plt.show()
