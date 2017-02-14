package agent.valuation;

import market.TradeResult;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import util.BoundedCounter;

public class LearnedNormalDistributionValuationStrategy implements ValuationStrategy {

    private final int goodId;
    private final BoundedCounter numberOfSamples;
    private final RandomGenerator rng;
    private double mean;
    private double variance;

    public LearnedNormalDistributionValuationStrategy(int goodId, int maxSamplesToRemember) {
        this.goodId = goodId;
        this.numberOfSamples = BoundedCounter.create(maxSamplesToRemember);
        this.rng = null; // We're not sampling, so no need to incur initialization overhead
    }

    @Override
    public double valueItem(double percentile) {
        if (percentile <= 0) return 0;
        if (variance == 0) {
            return mean;
        }
        return new NormalDistribution(rng, mean, Math.sqrt(variance)).inverseCumulativeProbability(percentile);
    }

    @Override
    public double probabilityOfGoodTrade(double offeredPrice) {
        if (offeredPrice <= 0) return 0;
        if (variance == 0) {
            if (offeredPrice > mean) {
                return 0;
            } else return 1;
        }
        return new NormalDistribution(rng, mean, Math.sqrt(variance)).cumulativeProbability(offeredPrice);
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
            updateVariance(result.pricePerItem, result.quantityTraded);
        }
    }

    private void updateMean(double newSample, int sampleCount) {
        int n = numberOfSamples.get();
        mean = mean * (n - sampleCount) / n + newSample * sampleCount / n;
    }

    private void updateVariance(double newSample, int sampleCount) {
        int n = numberOfSamples.get();
        variance = variance * (n - sampleCount) / n + Math.pow((newSample - mean), 2) * sampleCount / n;
    }
}
