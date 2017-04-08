package agent.valuation;

import agent.valuation.strategy.ValuationStrategy;
import goods.GoodId;

public interface ValuationStrategyFactory {

    ValuationStrategy makeStrategyFor(GoodId goodId);

}
