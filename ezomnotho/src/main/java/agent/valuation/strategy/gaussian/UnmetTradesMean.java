package agent.valuation.strategy.gaussian;

import market.TradeResult;
import util.BoundedCounter;
import util.math.attenuation.Attenuator;
import util.math.attenuation.QuadraticAttenuator;


/**
 * Todo: give this a not crap name
 * The idea is that if we receive a trade result with 100 goods offered and only 10 required, we would expect the
 * true value of the good to be lower than that offered.
 * The question is: how much?
 * We'll use an underlying mean provider, and 'spoof' updates by creating fake trade results for unmet trades.
 */
public class UnmetTradesMean implements GaussianMeanProvider {
    private final GaussianMeanProvider underlyingProvider;
    private final Attenuator attenuator;

    public UnmetTradesMean(GaussianMeanProvider underlyingProvider) {
        this.underlyingProvider = underlyingProvider;
        // todo: expose the attenuation strategy, don't hard code max attenuation of 50%
        this.attenuator = new QuadraticAttenuator(0.5);
    }

    @Override
    public void updateMean(TradeResult result) {
        underlyingProvider.updateMean(makeFakeTrade(result));
    }

    @Override
    public double getMean() {
        return underlyingProvider.getMean();
    }

    private TradeResult makeFakeTrade(TradeResult original) {
        int totalInFakeTrade = Math.max(original.quantityDesired, original.quantityOffered);
        double delta = (double) (original.quantityDesired - original.quantityTraded) / original.quantityOffered;
        double attenuation = attenuator.getAttenuation(delta);
        double price = original.pricePerItem * (1 + Math.signum(delta) * attenuation);
        if (delta > 0) {
            // there was more desired than available, we should raise the price
            price = original.pricePerItem * (1 + attenuation);
        } else {
            // there was less desired than available, we should lower the price
            price = original.pricePerItem * (1 - attenuation);
        }

        // don't spoof negative trades, that would be bad.
        price = Math.max(price, 0);

        System.out.println("spoofing a trade of " + original.goodId.name() + " at value " + price);

        return new TradeResult(original.goodId, totalInFakeTrade, totalInFakeTrade, totalInFakeTrade, price);
    }
}
