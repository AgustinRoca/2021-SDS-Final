JAVA_SOURCES = $(shell find src -name *.java)
OUT_FOLDER = out
DATA_FOLDER = data
EXPERIMENT_FOLDER = $(DATA_FOLDER)/experiment

compile:$(JAVA_SOURCES)
	mkdir -p $(OUT_FOLDER)
	javac -d $(OUT_FOLDER) $(JAVA_SOURCES)

generate_data_folder:
	mkdir -p $(DATA_FOLDER)
generate_experiment_folder:
	mkdir -p $(EXPERIMENT_FOLDER)

run_simulation:compile generate_data_folder
	java -cp $(OUT_FOLDER) simulation.SimulationApp

run_varyingAlpha_AverageTemperatureByTime:compile generate_experiment_folder
	java -cp $(OUT_FOLDER) simulation.experiment.varyingAlpha.AverageTemperatureByTime
run_varyingAlpha_AliveTreesAfterFire:compile generate_experiment_folder
	java -cp $(OUT_FOLDER) simulation.experiment.varyingAlpha.AliveTreesAfterFire
run_varyingAlpha_BurntTreesByTime:compile generate_experiment_folder
	java -cp $(OUT_FOLDER) simulation.experiment.varyingAlpha.BurntTreesByTime
run_varyingAlpha_TimeToExtinguish:compile generate_experiment_folder
	java -cp $(OUT_FOLDER) simulation.experiment.varyingAlpha.TimeToExtinguish
run_varyingAlpha_EllipseSizeAfterTime:compile generate_experiment_folder
	java -cp $(OUT_FOLDER) simulation.experiment.varyingAlpha.EllipseSizeAfterTime

run_varyingTreeRatio.AverageTemperatureByTime:compile generate_experiment_folder
	java -cp $(OUT_FOLDER) simulation.experiment.varyingTreeRatio.AverageTemperatureByTime
run_varyingTreeRatio.AliveTreesAfterFire:compile generate_experiment_folder
	java -cp $(OUT_FOLDER) simulation.experiment.varyingTreeRatio.AliveTreesAfterFire
run_varyingTreeRatio.BurntTreesByTime:compile generate_experiment_folder
	java -cp $(OUT_FOLDER) simulation.experiment.varyingTreeRatio.BurntTreesByTime
run_varyingTreeRatio.TimeToExtinguish:compile generate_experiment_folder
	java -cp $(OUT_FOLDER) simulation.experiment.varyingTreeRatio.TimeToExtinguish
run_varyingTreeRatio.EllipseSizeAfterTime:compile generate_experiment_folder
	java -cp $(OUT_FOLDER) simulation.experiment.varyingTreeRatio.EllipseSizeAfterTime


# You should run simulation before this
visualizer:
	bash -c "cd visualization;source .env/bin/activate; python visualizer.py"

graph_alpha_alive_trees:
	bash -c "cd visualization;source .env/bin/activate;cd graph; python alpha_alive_trees.py"
graph_alpha_avg_temp:
	bash -c "cd visualization;source .env/bin/activate;cd graph; python alpha_avg_temp.py"
graph_alpha_burnt_trees:
	bash -c "cd visualization;source .env/bin/activate;cd graph; python alpha_burnt_trees.py"
graph_alpha_ellipse_size:
	bash -c "cd visualization;source .env/bin/activate;cd graph; python alpha_ellipse_size.py"
graph_alpha_time_to_extinguish:
	bash -c "cd visualization;source .env/bin/activate;cd graph; python alpha_time_to_extinguish.py"

graph_ratio_alive_trees:
	bash -c "cd visualization;source .env/bin/activate;cd graph; python ratio_alive_trees.py"
graph_ratio_avg_temp:
	bash -c "cd visualization;source .env/bin/activate;cd graph; python ratio_avg_temp.py"
graph_ratio_burnt_trees:
	bash -c "cd visualization;source .env/bin/activate;cd graph; python ratio_burnt_trees.py"
graph_ratio_ellipse_size:
	bash -c "cd visualization;source .env/bin/activate;cd graph; python ratio_ellipse_size.py"
graph_ratio_time_to_extinguish:
	bash -c "cd visualization;source .env/bin/activate;cd graph; python ratio_time_to_extinguish.py"