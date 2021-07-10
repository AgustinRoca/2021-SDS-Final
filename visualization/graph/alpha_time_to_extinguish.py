import matplotlib.pyplot as plt

FILE_PATH = '../../data/experiment/alphaTimeToExtinguish.txt'

f = open(FILE_PATH, 'r')
line = f.readline().strip()
alpha_mins = []
alpha_maxs = []
errors = []
time_to_extinguish = []
while line != "":
    params = line.split('-')
    print(params)
    alpha_mins.append(float(params[2]))
    alpha_maxs.append(float(params[1]))
    line = f.readline().strip()
    params = line.split('-')
    time_to_extinguish.append(float(params[0]))
    errors.append(float(params[1]))
    line = f.readline()
    line = f.readline()

plt.rcParams["figure.figsize"] = (10, 10)
plt.rcParams.update({'font.size': 12})
plt.figure()
plt.title("Tiempo total de incendio promedio")
plt.ylabel("Tiempo (min)")
labels = []
for i in range(0, len(time_to_extinguish)):
    labels.append("{:.2f} - {:.2f}".format(alpha_mins[i], alpha_maxs[i]))
plt.bar(labels, time_to_extinguish,yerr=errors,ecolor='red',capsize=12)
plt.show()
