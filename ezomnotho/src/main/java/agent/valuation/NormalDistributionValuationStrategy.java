package agent.valuation;

import market.TradeResult;

public class NormalDistributionValuationStrategy implements ValuationStrategy{
    @Override
    public void processTradeResult(TradeResult result) {

    }

    @Override
    public double valueItem(int goodId, double percentile) {
        return 0;
    }

    @Override
    public double probabilityOfGoodTrade(int goodId, double offeredPrice) {
        return 0;
    }
}
