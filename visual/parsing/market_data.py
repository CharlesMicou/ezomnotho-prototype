import json


# TODO: what do we want to actually visualize with this?
class MarketData:
    def __init__(self, market_file):
        raw_data = self.extract_data(market_file)

    def extract_data(self, market_file):
        tmp = []
        for line in open(market_file):
            tmp.append(json.loads(line))
        return tmp
