package agent;

import com.google.common.collect.ImmutableList;
import common.TradeOffer;
import common.TradeResponse;
import common.TradeResult;

public interface Agent {
    public ImmutableList<TradeOffer> createTradeOffers();

    public TradeResponse processOffer(TradeOffer offer);

    public void processTradeResult(TradeResult result);
}

