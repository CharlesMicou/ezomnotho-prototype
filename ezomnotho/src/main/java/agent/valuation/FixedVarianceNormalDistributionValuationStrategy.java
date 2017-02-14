package agent.valuation;

import market.TradeResult;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import util.BoundedCounter;

public class FixedVarianceNormalDistributionValuationStrategy implements ValuationStrategy {
    private final int goodId;
    private final BoundedCounter numberOfSamples;
    private final RandomGenerator rng;
    private double mean;
    private final double breadth; // the breadth defines the fraction of the mean value to make the standard deviation

    public FixedVarianceNormalDistributionValuationStrategy(int goodId, int maxSamplesToRemember, double breadth) {
        this.goodId = goodId;
        this.numberOfSamples = BoundedCounter.create(maxSamplesToRemember);
        this.rng = null; // We're not sampling, so no need to incur initialization overhead
        this.breadth = breadth;
    }

    @Override
    public double valueItem(double percentile) {
        if (percentile <= 0) return 0;
        return new NormalDistribution(rng, mean, sd()).inverseCumulativeProbability(percentile);
    }

    @Override
    public double probabilityOfGoodTrade(double offeredPrice) {
        if (offeredPrice <= 0) return 0;
        return new NormalDistribution(rng, mean, sd()).cumulativeProbability(offeredPrice);
    }

    @Override
    public int getGoodId() {
        return goodId;
    }

    @Override
    public void processTradeResult(TradeResult result) {
        if (result.goodId == goodId) {
            numberOfSamples.inc(result.quantityTraded);
            updateMean(result.pricePerItem, result.quantityTraded);
        }
    }

    private void updateMean(double newSample, int sampleCount) {
        int n = numberOfSamples.get();
        mean = mean * (n - sampleCount) / n + newSample * sampleCount / n;
    }

    private double sd() {
        return mean * breadth;
    }
}
