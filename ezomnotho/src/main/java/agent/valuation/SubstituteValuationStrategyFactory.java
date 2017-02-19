package agent.valuation;

import agent.valuation.strategy.CompositeValuationStrategy;
import agent.valuation.strategy.ValuationStrategy;
import com.google.common.collect.ImmutableList;
import goods.GoodInfoDatabase;
import goods.GoodTag;


/**
 * A valuation strategy that uses similar goods to value an item.
 */
public class SubstituteValuationStrategyFactory implements ValuationStrategyFactory {

    // WARNING: underlying valuation strategies shouldn't in turn have substitution strategies that require this one
    // otherwise we end up in cyclical hell.
    // maybe there's an approximate way to do it?

    private final GoodInfoDatabase goodInfoDatabase;
    private final ValuationStrategyFactory underlyingFactory;

    public SubstituteValuationStrategyFactory(int goodId,
                                              GoodInfoDatabase infoDatabase,
                                              ValuationStrategyFactory underlyingFactory) {
        this.goodInfoDatabase = infoDatabase;
        this.underlyingFactory = underlyingFactory;
    }


    @Override
    public ValuationStrategy makeStrategyFor(int goodId) {
        ImmutableList.Builder<ValuationStrategy> strategyBuilder = ImmutableList.builder();

        for (GoodTag tag : goodInfoDatabase.infoFor(goodId).tags) {
            goodInfoDatabase.goodsWithTag(tag).forEach(good -> strategyBuilder.add(underlyingFactory.makeStrategyFor(good)));
        }

        return CompositeValuationStrategy.uniformStrategy(goodId, strategyBuilder.build());
    }
}
