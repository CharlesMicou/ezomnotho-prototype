package agent.valuation.strategy;

import goods.GoodId;
import market.TradeResult;

public interface ValuationStrategy {
    /**
     * Inform the valuation strategy that a trade has been completed, so that it can update its understanding
     * of the value of the relevant good.
     * @param result result of the trade
     */
    void processTradeResult(TradeResult result);

    /**
     * Ask for a probablistic assessment of the value of the good. Requesting a percentile of 0.9 means we get back
     * a value of the good that we are 90% confident the item is worth less than.
     * This means that requesting a percentile of 0.5 gives us the expected value of the item.
     * @param percentile the confidence level we wish to know that the good is worth less than.
     * @return the estimated value of an item at the provided percentile level
     */
    double valueItem(double percentile);

    /**
     * Perform a probabilistic assessment of whether or not a trade is deemed favourable.
     * A value of 0.5 means the offer is neither a win nor a loss, a value of 0.2 would mean a bad deal.
     * @param offeredPrice the price being offered
     * @return a probability, a value between 0 and 1, of the trade being beneficial to the agent.
     */
    double probabilityOfGoodTrade(double offeredPrice);

    /**
     * Valuation strategies evaluate a single good, but they are capable of processing the results of trades of
     * all goods. Therefore, all ValuationStrategies need to be aware of which good they are evaluating.
     * @return the good id of the valuated good
     */
    GoodId getGoodId();
}
