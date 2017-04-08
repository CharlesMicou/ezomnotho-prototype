package agent.valuation;

import agent.valuation.strategy.CompositeValuationStrategy;
import agent.valuation.strategy.ValuationStrategy;
import com.google.common.collect.ImmutableList;
import goods.GoodId;
import goods.GoodInfoDatabase;
import goods.GoodTag;


/**
 * A valuation strategy that uses similar goods to value an item.
 */
public class SubstituteValuationStrategyFactory implements ValuationStrategyFactory {

    // WARNING: underlying strategies may not contain substitute strategies themselves.

    private final GoodInfoDatabase goodInfoDatabase;

    //todo: make this take a map of existing valuation strategies.
    private final ValuationStrategyFactory underlyingFactory;

    public SubstituteValuationStrategyFactory(GoodId goodId,
                                              GoodInfoDatabase infoDatabase,
                                              ValuationStrategyFactory underlyingFactory) {
        this.goodInfoDatabase = infoDatabase;
        this.underlyingFactory = underlyingFactory;
    }


    @Override
    public ValuationStrategy makeStrategyFor(GoodId goodId) {
        ImmutableList.Builder<ValuationStrategy> strategyBuilder = ImmutableList.builder();

        for (GoodTag tag : goodInfoDatabase.infoFor(goodId).tags) {
            goodInfoDatabase.goodsWithTag(tag).forEach(good -> strategyBuilder.add(underlyingFactory.makeStrategyFor(good)));
        }

        return CompositeValuationStrategy.uniformStrategy(goodId, strategyBuilder.build());
    }
}
