import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
from matplotlib.colors import ListedColormap
import numpy as np
import math
import matplotlib.colors as mptcolors
from matplotlib import cm
import os
import argparse

DATA_PATH = "../data/output.txt"
ANIMATION_PATH = "./animation"
ANIMATION_FILENAME = "animation.avi"

f = open(DATA_PATH)
system_properties = f.readline().strip().split("-")
dt = float(system_properties[0])
alpha_min = float(system_properties[1])
alpha_max = float(system_properties[2])
times = []
matrixes = []
i = 0

line = f.readline().strip()
while line:
    times.append(int(line))
    matrixes.append([])
    line = f.readline().strip()
    j = 0
    while line:
        matrixes[i].append([])
        row = line.split(" ")
        for s in row:
            s = s.strip()
            if s == "N":
                matrixes[i][j].append(0)
            elif s == "D":
                matrixes[i][j].append(100)
            else:
                matrixes[i][j].append(float(s))
        line = f.readline().strip()
        j += 1
    line = f.readline().strip()
    i += 1
    print(i)



mat = plt.matshow(matrixes[0], cmap=cm.turbo, interpolation='none', vmin=0, vmax=900)

def update_func2d(zip, *fargs):
    global mat
    global ax
    ax.set_title("Time:{}".format(zip[1]/60), fontdict={'fontsize': 20})
    mat.set_data(zip[0])
    return mat

ani = FuncAnimation(plt.gcf(), update_func2d, frames=zip(matrixes, times), interval=50, repeat=True, repeat_delay=50,
                    save_count=len(matrixes))

ax = plt.gca()
ax.figure.set_size_inches((12, 12))
# minor_ticks_x = np.arange(0.5, simdata.sim_size[0] - 0.5, 1)
# minor_ticks_y = np.arange(0.5, simdata.sim_size[1] - 0.5, 1)
# ax.set_xticks(minor_ticks_x, minor=True)
# ax.set_yticks(minor_ticks_y, minor=True)
ax.grid(b=True, which='minor', color='gray', linestyle='-', linewidth=0.5)

def progress_callback(cur, tot):
    if cur%10 == 0:
        print(f"frame {cur} of {tot}")
if(not os.path.isdir(ANIMATION_PATH)):
    os.makedirs(ANIMATION_PATH)
ani.save(os.path.join(ANIMATION_PATH,ANIMATION_FILENAME), progress_callback=progress_callback)

for tic in ax.xaxis.get_major_ticks():
    tic.tick1On = tic.tick2On = False
for tic in ax.yaxis.get_major_ticks():
    tic.tick1On = tic.tick2On = False

plt.show()
