package integration;

import agent.valuation.strategy.CompositeValuationStrategy;
import agent.valuation.strategy.gaussian.FixedVarianceNormalDistributionValuationStrategy;
import agent.valuation.strategy.gaussian.LearnedNormalDistributionValuationStrategy;
import agent.valuation.strategy.ValuationStrategy;
import com.google.common.collect.ImmutableMap;
import goods.GoodInfoDatabase;
import market.TradeResult;

public class Main {
    public static void main(String [] args) {
        int goodId = 1234;
        int history = 1000;

        GoodInfoDatabase.create();

        ValuationStrategy learnedStrategy = new LearnedNormalDistributionValuationStrategy(goodId, history);

        ValuationStrategy fixedStrategy = new FixedVarianceNormalDistributionValuationStrategy(goodId, history, 0.1);

        ImmutableMap<Double, ValuationStrategy> composite = ImmutableMap.of(0.7, learnedStrategy, 0.3, fixedStrategy);

        ValuationStrategy overallStrategy = new CompositeValuationStrategy(goodId, composite);


        TradeResult trade1 = new TradeResult(goodId, 100, 1, 50);
        TradeResult trade2 = new TradeResult(goodId, 250, 3, 30);
        TradeResult trade3 = new TradeResult(goodId, 100, 5, 60);

        overallStrategy.processTradeResult(trade1);
        System.out.println(overallStrategy.valueItem(0.98));
        overallStrategy.processTradeResult(trade2);
        System.out.println(overallStrategy.valueItem(0.6));
        overallStrategy.processTradeResult(trade3);
        System.out.println(overallStrategy.valueItem(0.6));
    }

    // initialize agents
    // make a new marketplace

}
