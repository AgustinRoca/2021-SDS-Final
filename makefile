JAVA_SOURCES = $(shell find src -name *.java)
OUT_FOLDER = out
DATA_FOLDER = data

compile:$(JAVA_SOURCES)
	mkdir -p $(OUT_FOLDER)
	javac -d $(OUT_FOLDER) $(JAVA_SOURCES)

run_simulation:compile
	mkdir -p $(DATA_FOLDER)
	java -cp $(OUT_FOLDER) simulation.SimulationApp

# You should run simulation before this
visualizer:
	bash -c "cd visualization;source .env/bin/activate; python visualizer.py"