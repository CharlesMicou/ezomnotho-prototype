import os
import string

from parsing.agent_data import AgentData
from plotting.agent_plotter import AgentPlotter

logs_location = os.path.abspath("../../logs")

# get the latest data unless we specify otherwise
run_dir = "0"
for dir in os.listdir(logs_location):
    if string.atoi(dir) > string.atoi(run_dir):
        run_dir = dir

print "Loading run: " + run_dir
run_dir_abspath = os.path.join(logs_location, run_dir)
print "Found log files: " + str(os.listdir(run_dir_abspath))

# For now let's just have the fisherman:
fisherman_log = os.path.join(run_dir_abspath, "Farmer.log")

fisherman_data = AgentData(fisherman_log)
plotter = AgentPlotter()
plotter.plot_perceived_values(fisherman_data, ["CABBAGE", "WOOD", "FISH"])
