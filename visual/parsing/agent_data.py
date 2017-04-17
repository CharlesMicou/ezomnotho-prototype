import json


# TODO: what do we want to actually visualize with this?
import os


class AgentData:
    def __init__(self, market_file):
        self.known_goods, self.inventory_history, self.item_values = extract_data(market_file)
        self.name = os.path.basename(market_file).strip(".log")
        print "Extracted data from " + market_file

    def values_of_good(self, good_id):
        if good_id not in self.known_goods:
            print "Tried to value a good not found in the data: " + str(good_id)
            return None

        data_series = {}
        time_series = {}
        for timestamp, all_probabilities in self.item_values.iteritems():
            good_probabilities = all_probabilities.get(good_id)
            for probability, value in good_probabilities.iteritems():
                data_series[probability] = append_or_create(data_series.get(probability), value)
                time_series[probability] = append_or_create(time_series.get(probability), timestamp)

        return time_series, data_series

    """
    what do we want to plot over time?
    - inventory contents
    - perceived value (by good)
    """


def extract_data(market_file):
    known_goods = set()
    item_values = {}
    inventory_history = {}
    for line in open(market_file):
        parsed = json.loads(line)
        if "ITEM_VALUES" in parsed:
            for item in parsed.get("ITEM_VALUES"):
                if item not in known_goods:
                    known_goods.add(item)
            item_values[parsed.get("TIMESTAMP")] = parsed.get("ITEM_VALUES")

        if "INVENTORY" in parsed:
            inventory_history[parsed.get("TIMESTAMP")] = parsed.get("INVENTORY")

    return known_goods, inventory_history, item_values


def append_or_create(existing, entry):
    if existing is None:
        return [entry]
    else:
        existing.append(entry)
        return existing
