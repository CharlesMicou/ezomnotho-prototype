package agent.valuation;

import agent.valuation.strategy.ValuationStrategy;
import goods.GoodId;

public class BasicValuationStrategyFactory implements ValuationStrategyFactory {

    @Override
    public ValuationStrategy makeStrategyFor(GoodId goodId) {
        return null;
    }

    // basic strategies to implement:
    // simple learned variance gaussian: historic mean and variance
    // simple gaussian: historic mean, fixed fraction variance
    // simple smoothed mean gaussian: historic expo-smoothed mean, fixed fraction variance
    // simple unmet trades: unmnet trades augmented mean, learned variance
    // learned unmet trades:
}
