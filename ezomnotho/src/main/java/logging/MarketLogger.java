package logging;

import market.TradeOffer;
import market.TradeResult;
import org.json.simple.JSONObject;

import static logging.LogKeys.*;

@SuppressWarnings("unchecked")
public class MarketLogger {
    MiniLogger logger;

    private MarketLogger(MiniLogger logger) {
        this.logger = logger;
    }

    public static MarketLogger create(MiniLogger logger) {
        return new MarketLogger(logger);
    }

    public void logTradeOffer(TradeOffer tradeOffer, int timestamp) {
        JSONObject obj = new JSONObject();
        obj.put(TIMESTAMP.toString(), timestamp);
        obj.put(TRADE_OFFER.toString(), tradeOfferToJSON(tradeOffer));
        logger.write(obj);
    }

    public void logTradeResult(TradeResult tradeResult, int timestamp) {
        JSONObject obj = new JSONObject();
        obj.put(TIMESTAMP.toString(), timestamp);
        obj.put(TRADE_RESULT.toString(), tradeResultToJSON(tradeResult));
        logger.write(obj);
    }

    private JSONObject tradeResultToJSON(TradeResult result) {
        JSONObject obj = new JSONObject();
        obj.put(GOOD_ID.toString(), result.goodId.toString());
        obj.put(QUANTITY_OFFERED.toString(), result.quantityOffered);
        obj.put(QUANTITY_DESIRED.toString(), result.quantityDesired);
        obj.put(QUANTITY_TRADED.toString(), result.quantityTraded);
        obj.put(PRICE_PER_ITEM.toString(), String.format("%.2f", result.pricePerItem));
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
