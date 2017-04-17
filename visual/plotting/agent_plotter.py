import matplotlib.pyplot as plt


class AgentPlotter:
    def __init__(self):
        pass

    def plot_perceived_value(self, agent_data, good_id):
        fig, ax = plt.subplots()
        time_series, p_series = agent_data.values_of_good(good_id)

        plt.plot(time_series["0.5"], p_series["0.5"])
        plt.show()
        return
