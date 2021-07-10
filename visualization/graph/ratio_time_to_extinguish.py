import matplotlib.pyplot as plt

FILE_PATH = '../../data/experiment/ratioTimeToExtinguish.txt'

f = open(FILE_PATH, 'r')
line = f.readline().strip()
ratio = []
errors = []
time_to_extinguish = []
while line != "":
    params = line.split(';')
    print(params)
    ratio.append(float(params[1]))
    line = f.readline().strip()
    params = line.split(';')
    time_to_extinguish.append(float(params[0]))
    errors.append(float(params[1]))
    line = f.readline()
    line = f.readline()

plt.rcParams["figure.figsize"] = (16, 16)
plt.rcParams.update({'font.size': 20})
plt.figure()
plt.title("Tiempo total de incendio promedio")
plt.ylabel("Tiempo (min)")
labels = []
for i in range(0, len(time_to_extinguish)):
    labels.append("{:.0%}".format(ratio[i]))
plt.bar(labels, time_to_extinguish,yerr=errors,ecolor='red',capsize=12)
plt.show()
