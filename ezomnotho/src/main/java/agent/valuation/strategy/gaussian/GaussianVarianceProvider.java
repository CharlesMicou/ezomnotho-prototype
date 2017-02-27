package agent.valuation.strategy.gaussian;

import market.TradeResult;

public interface GaussianVarianceProvider {

    /**
     * Update the information the mean provider knows about.
     * @param result the result of a trade.
     */
    void updateVariance(TradeResult result);

    /**
     * Retrieve the mean for this mean provider
     * @return value of the variance of the Gaussian.
     */
    double getVariance();
}
