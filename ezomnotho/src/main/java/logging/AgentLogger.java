package logging;

import agent.valuation.strategy.ValuationStrategy;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import goods.GoodId;
import market.TradeOffer;
import org.json.simple.JSONObject;

import java.util.Map;

import static logging.LogKeys.*;

@SuppressWarnings("unchecked")
public class AgentLogger {
    private MiniLogger logger;
    private static ImmutableList<Double> valuationPoints = ImmutableList.of(0.1, 0.25, 0.4, 0.5, 0.6, 0.75, 0.9);

    private AgentLogger(MiniLogger logger) {
        this.logger = logger;
    }

    public static AgentLogger create(MiniLogger logger) {
        return new AgentLogger(logger);
    }

    public void logTradeOffer(TradeOffer tradeOffer, int timestamp) {
        JSONObject obj = new JSONObject();
        obj.put(TIMESTAMP.toString(), timestamp);
        obj.put(TRADE_OFFER.toString(), tradeOfferToJSON(tradeOffer));
        logger.write(obj);
    }

    public void logItemValues(ImmutableMap<GoodId, ValuationStrategy> valuationStrategies, int timestamp) {
        JSONObject values = new JSONObject();
        valuationStrategies.entrySet().forEach(entry -> {
            values.put(entry.getKey(), strategyToJSON(entry.getValue()));
        });

        JSONObject obj = new JSONObject();
        obj.put(TIMESTAMP.toString(), timestamp);
        obj.put(ITEM_VALUES.toString(), values);
        logger.write(obj);
    }

    private JSONObject strategyToJSON(ValuationStrategy strategy) {
        JSONObject obj = new JSONObject();
        for (Double valuationPoint : valuationPoints) {
            obj.put(valuationPoint, strategy.valueItem(valuationPoint));
        }
        return obj;
    }

    private JSONObject tradeOfferToJSON(TradeOffer offer) {
        JSONObject obj = new JSONObject();
        obj.put(GOOD_ID.toString(), offer.goodId.toString());
        obj.put(CREATOR.toString(), offer.creator.id());
        obj.put(QUANTITY_OFFERED.toString(), offer.initialQuantity);
        obj.put(PRICE_PER_ITEM.toString(), String.format("%.2f", offer.pricePerItem));
        return obj;
    }
}
