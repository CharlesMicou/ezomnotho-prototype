package agent;

import agent.valuation.CompositeValuationStrategy;
import agent.valuation.FixedVarianceNormalDistributionValuationStrategy;
import agent.valuation.LearnedNormalDistributionValuationStrategy;
import agent.valuation.ValuationStrategy;
import com.google.common.collect.ImmutableMap;

public class IntegrationTest {

    int goodId = 1234;
    int history = 1000;

    ValuationStrategy learnedStrategy = new LearnedNormalDistributionValuationStrategy(goodId, history);

    ValuationStrategy fixedStrategy = new FixedVarianceNormalDistributionValuationStrategy(goodId, history, 0.05);

    ImmutableMap<Double, ValuationStrategy> composite = ImmutableMap.of(0.7, learnedStrategy, 0.3, fixedStrategy);

    ValuationStrategy overallStrategy = new CompositeValuationStrategy(goodId, composite);

    Agent agent;

}
