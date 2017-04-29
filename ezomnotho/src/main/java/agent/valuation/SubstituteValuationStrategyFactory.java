package agent.valuation;

import agent.valuation.strategy.CompositeValuationStrategy;
import agent.valuation.strategy.ProxyValuationStrategy;
import agent.valuation.strategy.ValuationStrategy;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import goods.GoodId;
import goods.GoodInfoDatabase;
import goods.GoodTag;


/**
 * Makes valuation strategies that link similar good values together based on tags.
 *
 * We construct proxy valuation strategies to map into existing strategies.
 */
public class SubstituteValuationStrategyFactory {

    // WARNING: underlying strategies may not contain substitute strategies themselves, otherwise we end up violating
    // the directed acyclic graph that makes up our arch-strategy.

    private final GoodInfoDatabase goodInfoDatabase;

    //todo: make this take a map of existing valuation strategies.
    //private final ValuationStrategyFactory underlyingFactory;

    public SubstituteValuationStrategyFactory(GoodInfoDatabase infoDatabase) {
        this.goodInfoDatabase = infoDatabase;
    }

    public ValuationStrategy makeStrategyFor(GoodId goodId, ImmutableMap<GoodId, ValuationStrategy> existingStrategies) {
        ImmutableList.Builder<ValuationStrategy> strategyBuilder = ImmutableList.builder();

        for (GoodTag tag : goodInfoDatabase.infoFor(goodId).tags) {
            goodInfoDatabase.goodsWithTag(tag).forEach(
                    id -> {
                        if (existingStrategies.containsKey(id) && goodId != id) {
                            strategyBuilder.add(new ProxyValuationStrategy(existingStrategies.get(id)));
                        }
                    });
        }

        ImmutableList<ValuationStrategy> strategies = strategyBuilder.build();

        if (strategies.isEmpty()) {
            if (!existingStrategies.containsKey(goodId)) {
                System.out.println("Trying to make a substitution strategy for " + goodId.name() + ", but no " +
                        "substitutable strategies were found, and no existing strategy for the good was found either");
            } else {
                return new ProxyValuationStrategy(existingStrategies.get(goodId));
            }
        }

        return CompositeValuationStrategy.uniformStrategy(goodId, strategyBuilder.build());
    }
}
