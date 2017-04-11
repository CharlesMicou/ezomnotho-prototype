package agent.valuation;


import agent.valuation.strategy.CompositeValuationStrategy;
import agent.valuation.strategy.ValuationStrategy;
import agent.valuation.strategy.gaussian.FixedVarianceNormalDistributionValuationStrategy;
import agent.valuation.strategy.gaussian.LearnedNormalDistributionValuationStrategy;
import com.google.common.collect.ImmutableMap;
import goods.GoodId;
import goods.GoodInfoDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Abstraction layer to just make all the strategies and bundle them into a composite.
 */
public class ArchValuationStrategyFactory {

    private static final GoodInfoDatabase goodInfoDatabase = GoodInfoDatabase.create();
    private Random random;

    public ArchValuationStrategyFactory() {
        random = new Random();
    }


    public ValuationStrategy makeArchStrategy() {
        // Make basic strategies

        // TODO: Production based valuation strategies

        // Make substitution-augmented strategies

        // Generate some dummy trades of all items to do initialisation.

        // Maybe an initial value of 0; i.e. worthless until proven otherwise, is sensible.
        return null;
    }

    // temporary measures to do integration testing:
    public ImmutableMap<GoodId, ValuationStrategy> yoloStrategies() {
        int max_history = 100;

        Map<GoodId, ValuationStrategy> strategies = new HashMap<>();

        goodInfoDatabase.allGoods().forEach(
                goodInfo -> {

                    double breadth = 0.1 + 0.1 * random.nextDouble();

                    double weightingA = random.nextDouble();
                    double weightingB = 1 - weightingA;

                    ValuationStrategy learnedStrategy =
                            new LearnedNormalDistributionValuationStrategy(goodInfo.id, max_history);
                    ValuationStrategy fixedStrategy =
                            new FixedVarianceNormalDistributionValuationStrategy(goodInfo.id, max_history, breadth);
                    ImmutableMap<Double, ValuationStrategy> composite =
                            ImmutableMap.of(weightingA, learnedStrategy, weightingB, fixedStrategy);
                    ValuationStrategy overallStrategy =
                            new CompositeValuationStrategy(goodInfo.id, composite);
                    strategies.put(goodInfo.id, overallStrategy);
                });
        return ImmutableMap.copyOf(strategies);
    }
}
