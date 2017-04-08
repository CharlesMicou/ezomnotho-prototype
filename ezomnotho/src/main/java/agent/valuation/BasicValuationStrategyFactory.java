package agent.valuation;

import agent.valuation.strategy.ValuationStrategy;
import goods.GoodId;

public class BasicValuationStrategyFactory implements ValuationStrategyFactory {

    @Override
    public ValuationStrategy makeStrategyFor(GoodId goodId) {
        return null;
    }
}
