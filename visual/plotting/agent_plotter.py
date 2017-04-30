import matplotlib.pyplot as plt


class AgentPlotter:
    def __init__(self):
        self.colors = ['blue', 'red', 'green', 'magenta', 'cyan', 'purple', 'orange', 'yellow']
        pass

    def make_all_plots(self, agent_data, good_ids):
        fig, ax = plt.subplots()
        self.plot_perceived_values(agent_data, good_ids)
        self.plot_inventory_contents(agent_data, good_ids)
        fig.suptitle('Agent ' + agent_data.name)
        fig.subplots_adjust(hspace=0.4)
        plt.show()

    def plot_perceived_values(self, agent_data, good_ids):
        ax = plt.subplot(2, 1, 1)
        i = 0
        for good_id in good_ids:
            self.plot_perceived_value(agent_data, good_id, self.colors[i % len(self.colors)], ax)
            i += 1

        plt.title('Perceived good values')
        plt.xlabel("time (market cycle)")
        plt.ylabel("value")
        plt.legend()

    def plot_inventory_contents(self, agent_data, good_ids):
        ax = plt.subplot(2, 1, 2)
        i = 0
        for good_id in good_ids:
            self.plot_inventory_item(agent_data, good_id, self.colors[i % len(self.colors)], ax)
            i += 1

        plt.title('Inventory contents')
        plt.xlabel("time (market cycle)")
        plt.ylabel("quantity")
        plt.legend()

    @staticmethod
    def plot_inventory_item(agent_data, good_id, color, ax):
        time_series, data_series = agent_data.count_of_good(good_id)
        plt.plot(time_series, data_series, color=color, label=str(good_id))

    @staticmethod
    def plot_perceived_value(agent_data, good_id, color, ax):
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
                         alpha=1, label=str(good_id))
            else:
                print "Unrecognised probability histogram. You really ought to generalize this"

        ax.fill_between(time_series[min(time_series.iterkeys())], p_series[max(time_series.iterkeys())],
                        p_series[min(time_series.iterkeys())], color=color, alpha=0.1)

        return
