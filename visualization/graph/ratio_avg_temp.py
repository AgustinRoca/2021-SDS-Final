import matplotlib.pyplot as plt

FILE_PATH = '../../data/experiment/ratioAvgTemp.txt'

f = open(FILE_PATH, 'r')
line = f.readline().strip()
ratio = []
times = []
temps = []
while line != "":
    params = line.split('-')
    ratio.append(float(params[1]))
    line = f.readline().strip()
    times.append([])
    temps.append([])
    while line != "":
        record = line.split(':')
        times[-1].append(float(record[0]))
        temps[-1].append(float(record[1]))
        line = f.readline().strip()
    line = f.readline()

plt.rcParams["figure.figsize"] = (10, 10)
plt.rcParams.update({'font.size': 12})
plt.figure()
plt.title("Temperatura promedio a través del tiempo")
plt.ylabel("Temperatura (K)")
plt.xlabel("Tiempo (min)")
labels = []
for i in range(0, len(times)):
    plt.plot(times[i], temps[i], label=("{:.0%}".format(ratio[i])))
plt.legend()
plt.show()
