import matplotlib.pyplot as plt


class AgentPlotter:
    def __init__(self):
        pass

    def plot_perceived_values(self, agent_data, good_ids):
        fig, ax = plt.subplots()
        colors = ['blue', 'red', 'green', 'magenta', 'cyan', 'purple', 'orange', 'yellow']
        i = 0
        for good_id in good_ids:
            self.plot_perceived_value(agent_data, good_id, colors[i % len(colors)])
            i += 1

        plt.show()

    @staticmethod
    def plot_perceived_value(agent_data, good_id, color):
        time_series, p_series = agent_data.values_of_good(good_id)

        for probability in time_series.iterkeys():
            if probability == "0.1" or probability == "0.9":
                plt.plot(time_series[probability], p_series[probability], color=color, linewidth=0.5, linestyle="--",
                         alpha=0.3)
            elif probability == "0.25" or probability == "0.75":
                plt.plot(time_series[probability], p_series[probability], color=color, linewidth=1, linestyle="--",
                         alpha=0.5)
            elif probability == "0.4" or probability == "0.6":
                plt.plot(time_series[probability], p_series[probability], color=color, linewidth=1.5, linestyle="--",
                         alpha=0.75)
            elif probability == "0.5":
                plt.plot(time_series[probability], p_series[probability], color=color, linewidth=2.5, linestyle="-",
                         alpha=1)
            else:
                print "Unrecognised probability histogram. You really ought to generalize this"

        return
