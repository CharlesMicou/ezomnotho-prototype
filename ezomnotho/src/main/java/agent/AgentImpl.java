package agent;

import com.google.common.collect.ImmutableList;
import market.TradeOffer;
import market.TradeResponse;
import market.TradeResult;

public class AgentImpl implements Agent {
    @Override
    public ImmutableList<TradeOffer> createTradeOffers() {
        return null;
    }

    @Override
    public TradeResponse processOffer(TradeOffer offer) {
        return null;
    }

    @Override
    public void processTradeResult(TradeResult result) {

    }

    @Override
    public void sellGoodsTo(int goodId, int quantity, double pricePerItem) {

    }

    @Override
    public void buyGoodsFrom(int goodId, int quantity, double pricePerItem) {

    }
}
