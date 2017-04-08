package agent.valuation.strategy;

import com.google.common.collect.ImmutableMap;
import goods.GoodId;
import market.TradeResult;

import java.util.List;

/**
 * A Composite Valuation Strategy takes several existing weighting strategies and combines them
 * via a set of weights. The weights are fixed, rather than learned, because screw making this
 * even more complicated.
 */
public class CompositeValuationStrategy implements ValuationStrategy {
    private final ImmutableMap<Double, ValuationStrategy> weightedStrategies;
    private final GoodId goodId;

    public CompositeValuationStrategy(GoodId goodId, ImmutableMap<Double, ValuationStrategy> weightedStrategies) {
        this.weightedStrategies = normaliseWeightings(weightedStrategies);
        this.goodId = goodId;
    }

    public static CompositeValuationStrategy uniformStrategy(GoodId goodId, List<ValuationStrategy> strategies) {
        ImmutableMap.Builder<Double, ValuationStrategy> builder = ImmutableMap.builder();
        strategies.forEach(strategy -> builder.put(1.0, strategy));
        return new CompositeValuationStrategy(goodId, builder.build());
    }

    @Override
    public GoodId getGoodId() {
        return goodId;
    }

    @Override
    public void processTradeResult(TradeResult result) {
        weightedStrategies.values().forEach(strategy -> strategy.processTradeResult(result));
    }

    @Override
    public double valueItem(double percentile) {
        return weightedStrategies.entrySet().stream()
                .map(entry -> entry.getKey() * entry.getValue().valueItem(percentile))
                .reduce(0.0, (a, b) -> a + b);
    }

    @Override
    public double probabilityOfGoodTrade(double offeredPrice) {
        return weightedStrategies.entrySet().stream()
                .map(entry -> entry.getKey() * entry.getValue().probabilityOfGoodTrade(offeredPrice))
                .reduce(0.0, (a, b) -> a + b);
    }

    private ImmutableMap<Double, ValuationStrategy> normaliseWeightings(
            ImmutableMap<Double, ValuationStrategy> initial) {
        final double sumOfWeights = initial.keySet().stream().reduce(0.0, (a, b) -> a + b);
        ImmutableMap.Builder<Double, ValuationStrategy> builder = ImmutableMap.builder();
        initial.entrySet().forEach(entry -> builder.put(entry.getKey() / sumOfWeights, entry.getValue()));
        return builder.build();
    }
}
