package agent.valuation.strategy.gaussian;

import market.TradeResult;
import util.BoundedCounter;

public class SimpleLearnedVariance implements GaussianVarianceProvider {

    private final GaussianMeanProvider meanProvider;
    private double variance;
    private final BoundedCounter numberOfSamples;

    SimpleLearnedVariance(int maxHistory) {
        this.meanProvider = new SimpleAverageMean(maxHistory);
        this.numberOfSamples = BoundedCounter.create(maxHistory);
    }

    @Override
    public void updateVariance(TradeResult result) {
        numberOfSamples.inc();
        meanProvider.updateMean(result);
        int k = result.quantityTraded;
        int n = numberOfSamples.get();
        variance = variance * (n - k) / n + Math.pow((result.pricePerItem - meanProvider.getMean()), 2) * k / n;
    }

    @Override
    public double getVariance() {
        return variance;
    }
}
