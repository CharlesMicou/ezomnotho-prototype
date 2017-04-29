package agent.valuation.strategy.gaussian;

import market.TradeResult;
import util.BoundedCounter;


/**
 * The simplest possible provider: it's just the mean of all samples available in history.
 */
public class SimpleAverageMean implements GaussianMeanProvider {

    private double mean;
    private final BoundedCounter numberOfSamples;

    public SimpleAverageMean(int maxHistory) {
        this.numberOfSamples = BoundedCounter.create(maxHistory);
    }

    @Override
    public void updateMean(TradeResult result) {
        numberOfSamples.inc();
        int numberTraded = result.quantityTraded;
        double priceTraded = result.pricePerItem;
        int n = numberOfSamples.get();
        mean = mean * (n - numberTraded) / n + priceTraded * numberTraded / n;
    }

    @Override
    public double getMean() {
        return mean;
    }
}
