package agent.valuation.strategy.gaussian;

import market.TradeResult;

public interface GaussianMeanProvider {

    /**
     * Update the information the mean provider knows about.
     * @param result the result of a trade.
     */
    void updateMean(TradeResult result);

    /**
     * Retrieve the mean for this mean provider
     * @return value of the mean of the Gaussian.
     */
    double getMean();

}
