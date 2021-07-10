import matplotlib.pyplot as plt

FILE_PATH = '../../data/experiment/ratioEllipse.txt'

f = open(FILE_PATH, 'r')
line = f.readline().strip()
ratio = []
ellipse_lengths = []
ellipse_widths = []
length_errors = []
width_errors = []
dt_sys = []
while line != "":
    params = line.split('-')
    dt_sys.append(float(params[0]))
    print(params)
    ratio.append(float(params[1]))
    line = f.readline().strip()
    params = line.split('-')
    print(params)
    ellipse_lengths.append(float(params[0]))
    length_errors.append(float(params[1]))
    ellipse_widths.append(float(params[2]))
    width_errors.append(float(params[3]))
    line = f.readline()
    line = f.readline()

plt.rcParams["figure.figsize"] = (10, 10)
plt.rcParams.update({'font.size': 12})

# lengths
plt.figure()
plt.title("Longitud de la elipse luego de {:.0f} mins".format(dt_sys[0]))
plt.ylabel("Longitud (m)")
labels = []
for i in range(0, len(ellipse_lengths)):
    labels.append("{:.0%}".format(ratio[i]))
plt.bar(labels, ellipse_lengths,yerr=length_errors,ecolor='red',capsize=12)

# widths
plt.figure()
plt.title("Longitud de la elipse luego de {:.0f} mins".format(dt_sys[0]))
plt.ylabel("Ancho (m)")
labels = []
for i in range(0, len(ellipse_widths)):
    labels.append("{:.0%}".format(ratio[i]))
plt.bar(labels, ellipse_widths,yerr=width_errors,ecolor='red',capsize=12)


plt.show()
