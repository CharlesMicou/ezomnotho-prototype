package agent.valuation;

import agent.valuation.strategy.ValuationStrategy;

public interface ValuationStrategyFactory {

    ValuationStrategy makeStrategyFor(int goodId);

}
