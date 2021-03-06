package agent.valuation;


import agent.valuation.strategy.CompositeValuationStrategy;
import agent.valuation.strategy.ValuationStrategy;
import com.google.common.collect.ImmutableMap;
import goods.GoodId;
import goods.GoodInfoDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Abstraction layer to just make all the strategies and bundle them into a composite.
 */
public class ArchValuationStrategyFactory {

    private static final GoodInfoDatabase goodInfoDatabase = GoodInfoDatabase.create();
    private static final BasicValuationStrategyFactory basicStrategyFactory = new BasicValuationStrategyFactory();
    private static final SubstituteValuationStrategyFactory substituteStrategyFactory = new SubstituteValuationStrategyFactory(goodInfoDatabase);
    private Random random;

    public ArchValuationStrategyFactory() {
        random = new Random();
    }


    public ImmutableMap<GoodId, ValuationStrategy> makeArchStrategy() {
        // Make basic strategies
        Map<GoodId, ValuationStrategy> basicStrategiesMutable = new HashMap<>();

        goodInfoDatabase.allGoods().forEach(
                goodInfo -> {
                    GoodId id = goodInfo.id;
                    basicStrategiesMutable.put(id, basicStrategyFactory.makeStrategyFor(id));
                });

        ImmutableMap<GoodId, ValuationStrategy> basicStrategies = ImmutableMap.copyOf(basicStrategiesMutable);

        // TODO: Production based valuation strategies

        // Make substitution-augmented strategies
        Map<GoodId, ValuationStrategy> substitutionStrategies = new HashMap<>();

        basicStrategies.entrySet().forEach(entry -> {
            substitutionStrategies.put(
                    entry.getKey(),
                    substituteStrategyFactory.makeStrategyFor(entry.getKey(), basicStrategies));
        });

        double substitute_weighting = 0.2;

        Map<GoodId, ValuationStrategy> finalStrategies = new HashMap<>();

        basicStrategies.entrySet().forEach(entry -> {
            GoodId goodId = entry.getKey();
            finalStrategies.put(goodId, new CompositeValuationStrategy(goodId,
                    ImmutableMap.of(
                            basicStrategies.get(goodId), 1 - substitute_weighting,
                            substitutionStrategies.get(goodId), substitute_weighting)));
        });

        // Generate some dummy trades of all items to do initialisation.

        // Maybe an initial value of 0; i.e. worthless until proven otherwise, is sensible.
        //return ImmutableMap.copyOf(finalStrategies);
        // for now just use basic strategies
        return basicStrategies;
    }
}
