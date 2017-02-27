package agent.valuation.strategy.gaussian;

import agent.valuation.strategy.ValuationStrategy;
import market.TradeResult;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.RandomGenerator;

/**
 * This makes the assumption that the certainty of the agent about a value of a good at any given point in
 * time is normally distributed. The agent adapts the variance and mean of the distribution as it observes
 * trades.
 */
public class GaussianValuationStrategy implements ValuationStrategy {

    private final int goodId;
    private final RandomGenerator rng;
    private final GaussianMeanProvider meanProvider;
    private final GaussianVarianceProvider varianceProvider;

    public GaussianValuationStrategy(int goodId,
                                     GaussianMeanProvider meanProvider,
                                     GaussianVarianceProvider varianceProvider) {
        this.goodId = goodId;
        this.meanProvider = meanProvider;
        this.varianceProvider = varianceProvider;
        this.rng = null; // We're not sampling, so we don't want to incur any initialization overhead.
    }

    @Override
    public void processTradeResult(TradeResult result) {
        if (result.goodId == goodId) {
            meanProvider.updateMean(result);
            varianceProvider.updateVariance(result);
        }
    }

    @Override
    public double valueItem(double percentile) {
        if (percentile <= 0) return 0;
        if (varianceProvider.getVariance() == 0) {
            return meanProvider.getMean();
        }

        return new NormalDistribution(rng, meanProvider.getMean(), Math.sqrt(varianceProvider.getVariance()))
                .inverseCumulativeProbability(percentile);
    }

    @Override
    public double probabilityOfGoodTrade(double offeredPrice) {
        if (offeredPrice <= 0) return 0;
        if (varianceProvider.getVariance() == 0) {
            if (offeredPrice > meanProvider.getMean()) {
                return 0;
            } else return 1;
        }
        return (1.0 - new NormalDistribution(rng, meanProvider.getMean(), Math.sqrt(varianceProvider.getVariance()))
                .cumulativeProbability(offeredPrice));
    }

    @Override
    public int getGoodId() {
        return goodId;
    }
}
