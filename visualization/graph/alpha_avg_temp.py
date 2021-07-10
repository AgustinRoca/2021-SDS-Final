import matplotlib.pyplot as plt

FILE_PATH = '../../data/experiment/alphaAvgTemp.txt'

f = open(FILE_PATH, 'r')
line = f.readline().strip()
alpha_mins = []
alpha_maxs = []
times = []
temps = []
errors = []
while line != "":
    params = line.split('-')
    print(params)
    alpha_mins.append(float(params[2]))
    alpha_maxs.append(float(params[1]))
    line = f.readline().strip()
    times.append([])
    temps.append([])
    errors.append([])
    while line != "":
        record = line.split(':')
        times[-1].append(float(record[0]))
        temp_record = record[1].split('-')
        temps[-1].append(float(temp_record[0]))
        errors[-1].append(float(temp_record[1]))
        line = f.readline().strip()
    line = f.readline()

plt.rcParams["figure.figsize"] = (10, 10)
plt.rcParams.update({'font.size': 12})
plt.figure()
plt.title("Temperatura promedio a través del tiempo")
plt.ylabel("Temperatura (K)")
plt.xlabel("Tiempo (min)")
labels = []

def filter_by_i(arr, mod):
    return list([arr[j] for j in range(0,len(arr)) if j%mod == 0])

for i in range(0, len(times)):
    plt.errorbar(filter_by_i(times[i],10),filter_by_i(temps[i],10),yerr=filter_by_i(errors[i],10),fmt='none')
    plt.plot(times[i], temps[i], label=("{:.2f} - {:.2f}".format(alpha_mins[i], alpha_maxs[i])))
plt.legend()
plt.show()
