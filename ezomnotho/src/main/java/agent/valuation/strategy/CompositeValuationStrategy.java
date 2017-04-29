package agent.valuation.strategy;

import com.google.common.collect.ImmutableMap;
import goods.GoodId;
import market.TradeResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Composite Valuation Strategy takes several existing weighting strategies and combines them
 * via a set of weights. The weights are fixed, rather than learned, because screw making this
 * even more complicated.
 */
public class CompositeValuationStrategy implements ValuationStrategy {
    private final ImmutableMap<ValuationStrategy, Double> weightedStrategies;
    private final GoodId goodId;

    public CompositeValuationStrategy(GoodId goodId, ImmutableMap<ValuationStrategy, Double> weightedStrategies) {
        this.weightedStrategies = normaliseWeightings(weightedStrategies);
        this.goodId = goodId;
    }

    public static CompositeValuationStrategy uniformStrategy(GoodId goodId, List<ValuationStrategy> strategies) {
        Map<ValuationStrategy, Double> strategyToWeighting = new HashMap<>();
        strategies.forEach(strategy -> {
                    strategyToWeighting.put(strategy, 1.0);
                });
        return new CompositeValuationStrategy(goodId, ImmutableMap.copyOf(strategyToWeighting));
    }

    @Override
    public GoodId getGoodId() {
        return goodId;
    }

    @Override
    public void processTradeResult(TradeResult result) {
        weightedStrategies.keySet().forEach(strategy -> strategy.processTradeResult(result));
    }

    @Override
    public double valueItem(double percentile) {
        return weightedStrategies.entrySet().stream()
                .map(entry -> entry.getValue() * entry.getKey().valueItem(percentile))
                .reduce(0.0, (a, b) -> a + b);
    }

    @Override
    public double probabilityOfGoodTrade(double offeredPrice) {
        return weightedStrategies.entrySet().stream()
                .map(entry -> entry.getValue() * entry.getKey().probabilityOfGoodTrade(offeredPrice))
                .reduce(0.0, (a, b) -> a + b);
    }

    private ImmutableMap<ValuationStrategy, Double> normaliseWeightings(
            ImmutableMap<ValuationStrategy, Double> initial) {
        final double sumOfWeights = initial.values().stream().reduce(0.0, (a, b) -> a + b);
        ImmutableMap.Builder<ValuationStrategy, Double> builder = ImmutableMap.builder();
        initial.entrySet().forEach(entry -> builder.put(entry.getKey(), entry.getValue() / sumOfWeights));
        return builder.build();
    }
}
