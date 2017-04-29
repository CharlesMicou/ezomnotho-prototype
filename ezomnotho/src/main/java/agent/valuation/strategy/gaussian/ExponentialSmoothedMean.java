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

    public ExponentialSmoothedMean(double alpha) {
        if (alpha > 1 || alpha < 0) {
            throw new java.lang.IllegalArgumentException("Tried to make exponential smoothing with alpha " + alpha);
        }

        this.alpha = alpha;
        this.mean = 0;
    }

    @Override
    public void updateMean(TradeResult result) {
        double scaling = Math.pow((1-alpha), result.quantityTraded);
        mean = (1 - scaling) * result.pricePerItem + scaling * mean;
    }

    @Override
    public double getMean() {
        return mean;
    }
}
