package agent.valuation;

import market.TradeResult;


/**
 * A valuation strategy that uses similar goods to value an item.
 */
public class SubstituteValuationStrategy implements ValuationStrategy {

    /**
     * Todo implement this
     * @param result result of the trade
     */

    // WARNING: underlying valuation strategies shouldn't in turn have substitution strategies that require this one
    // otherwise we end up in cyclical hell.
    // maybe there's an approximate way to do it?

    @Override
    public void processTradeResult(TradeResult result) {

    }

    @Override
    public double valueItem(double percentile) {
        return 0;
    }

    @Override
    public double probabilityOfGoodTrade(double offeredPrice) {
        return 0;
    }

    @Override
    public int getGoodId() {
        return 0;
    }
}
