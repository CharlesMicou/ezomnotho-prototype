package agent.valuation.strategy.gaussian;

import market.TradeResult;

public class FixedFractionVariance implements GaussianVarianceProvider {

    private final double breadth; // the breadth defines the fraction of the mean value to make the standard deviation
    private GaussianMeanProvider meanProvider;

    public FixedFractionVariance(double breadth, GaussianMeanProvider meanProvider) {
        this.meanProvider = meanProvider;
        this.breadth = breadth;
    }

    @Override
    public void updateVariance(TradeResult result) {
        // no op
    }

    @Override
    public double getVariance() {
        return Math.pow((meanProvider.getMean() * breadth), 2);
    }
}
