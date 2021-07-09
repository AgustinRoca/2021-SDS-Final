import matplotlib.pyplot as plt

FILE_PATH = '../../data/experiment/alphaEllipse.txt'

f = open(FILE_PATH, 'r')
line = f.readline().strip()
alpha_mins = []
alpha_maxs = []
ellipse_lengths = []
ellipse_widths = []
dt_sys = []
while line != "":
    params = line.split('-')
    dt_sys.append(float(params[0]))
    print(params)
    alpha_mins.append(float(params[2]))
    alpha_maxs.append(float(params[1]))
    line = f.readline().strip()
    params = line.split('-')
    print(params)
    ellipse_lengths.append(float(params[0]))
    ellipse_widths.append(float(params[1]))
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
    labels.append("{:.2f} - {:.2f}".format(alpha_mins[i], alpha_maxs[i]))
plt.bar(labels, ellipse_lengths)

# widths
plt.figure()
plt.title("Longitud de la elipse luego de {:.0f} mins".format(dt_sys[0]))
plt.ylabel("Ancho (m)")
labels = []
for i in range(0, len(ellipse_widths)):
    labels.append("{:.2f} - {:.2f}".format(alpha_mins[i], alpha_maxs[i]))
plt.bar(labels, ellipse_widths)


plt.show()
