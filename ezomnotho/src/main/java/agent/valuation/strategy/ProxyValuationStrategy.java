package agent.valuation.strategy;

import market.TradeResult;

/**
 * The Proxy Valuation Strategy references an existing valuation strategy to get the value of an item.
 * As such, it doesn't do anything when processing a trade result.
 */
public class ProxyValuationStrategy implements ValuationStrategy {

    private final ValuationStrategy underlyingStrategy;

    ProxyValuationStrategy(ValuationStrategy underlyingStrategy) {
        this.underlyingStrategy = underlyingStrategy;
    }

    @Override
    public void processTradeResult(TradeResult result) {
        // no-op
    }

    @Override
    public double valueItem(double percentile) {
        return underlyingStrategy.valueItem(percentile);
    }

    @Override
    public double probabilityOfGoodTrade(double offeredPrice) {
        return underlyingStrategy.probabilityOfGoodTrade(offeredPrice);
    }

    @Override
    public int getGoodId() {
        return underlyingStrategy.getGoodId();
    }
}
