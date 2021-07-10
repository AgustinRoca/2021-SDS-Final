import matplotlib.pyplot as plt

FILE_PATH = '../../data/experiment/ratioBurnTree.txt'

f = open(FILE_PATH, 'r')
line = f.readline().strip()
ratio = []
times = []
burnt_trees = []
errors = []
while line != "":
    params = line.split('-')
    ratio.append(float(params[1]))
    line = f.readline().strip()
    times.append([])
    burnt_trees.append([])
    errors.append([])
    while line != "":
        record = line.split(':')
        times[-1].append(float(record[0]))
        burnt_tree_record = record[1].split('-')
        burnt_trees[-1].append(float(burnt_tree_record[0]))
        errors[-1].append(float(burnt_tree_record[1]))
        line = f.readline().strip()
    line = f.readline()

plt.rcParams["figure.figsize"] = (16, 16)
plt.rcParams.update({'font.size': 20})
plt.figure()
plt.title("Árboles quemados a través del tiempo")
plt.ylabel("Árboles quemados")
plt.xlabel("Tiempo (min)")
labels = []

def filter_by_i(arr, mod):
    return list([arr[j] for j in range(0,len(arr)) if j%mod == 0])


for i in range(0, len(times)):
    plt.errorbar(filter_by_i(times[i],10),filter_by_i(burnt_trees[i],10),yerr=filter_by_i(errors[i],10),fmt='o')
    plt.plot(times[i], burnt_trees[i], label=("{:.0%}".format(ratio[i])))
plt.legend()
plt.show()
