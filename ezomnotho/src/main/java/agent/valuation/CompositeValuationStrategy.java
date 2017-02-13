package agent.valuation;

import com.google.common.collect.ImmutableMap;
import market.TradeResult;

/**
 * A Composite Valuation Strategy takes several existing weighting strategies and combines them
 * via a set of weights. The weights are fixed, rather than learned, because screw making this
 * even more complicated.
 */
public class CompositeValuationStrategy implements ValuationStrategy {
    private final ImmutableMap<Double, ValuationStrategy> weightings;

    CompositeValuationStrategy(ImmutableMap<Double, ValuationStrategy> weightings) {
        this.weightings = normaliseWeightings(weightings);
    }

    @Override
    public void processTradeResult(TradeResult result) {
        weightings.values().forEach(strategy -> strategy.processTradeResult(result));
    }

    @Override
    public double valueItem(int goodId, double percentile) {
        return 0;
    }

    @Override
    public double probabilityOfGoodTrade(int goodId, double offeredPrice) {
        return 0;
    }

    private ImmutableMap<Double, ValuationStrategy> normaliseWeightings(
            ImmutableMap<Double, ValuationStrategy> initial) {
        final double sumOfWeights = initial.keySet().stream().reduce(0.0, (a, b) -> a + b);
        ImmutableMap.Builder<Double, ValuationStrategy> builder = ImmutableMap.builder();
        initial.entrySet().forEach(entry -> builder.put(entry.getKey()/sumOfWeights, entry.getValue()));
        return builder.build();
    }
}
