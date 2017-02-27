package agent.valuation.strategy.gaussian;

import market.TradeResult;

/**
 * Use exponential smoothing to determine the mean.
 * Alpha is a weighting between 0 and 1, the nearer we are to 0,
 * the less we respect new data.
 */
public class ExponentialSmoothedMean implements GaussianMeanProvider {
    private final double alpha;
    private double mean;

    ExponentialSmoothedMean(double alpha) {
        this.alpha = alpha;
        this.mean = 0;
    }

    @Override
    public void updateMean(TradeResult result) {
        //TODO
        // to do this for multiple trades I'm going to need to do a derivation of this by hand
    }

    @Override
    public double getMean() {
        return 0;
    }
}
