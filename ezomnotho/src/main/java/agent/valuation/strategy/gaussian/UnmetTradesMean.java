package agent.valuation.strategy.gaussian;

import market.TradeResult;
import util.BoundedCounter;


/**
 * Todo: give this a not crap name
 * The idea is that if we receive a trade result with 100 goods offered and only 10 required, we would expect the
 * true value of the good to be lower than that offered.
 * The question is: how much?
 */
public class UnmetTradesMean implements GaussianMeanProvider {

    private double mean;
    private final BoundedCounter numberOfSamples;

    UnmetTradesMean(int maxHistory) {
        this.numberOfSamples = BoundedCounter.create(maxHistory);
    }

    @Override
    public void updateMean(TradeResult result) {
        //todo
    }

    @Override
    public double getMean() {
        return mean;
    }
}
