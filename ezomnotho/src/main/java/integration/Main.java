package integration;

import agent.valuation.CompositeValuationStrategy;
import agent.valuation.FixedVarianceNormalDistributionValuationStrategy;
import agent.valuation.LearnedNormalDistributionValuationStrategy;
import agent.valuation.ValuationStrategy;
import com.google.common.collect.ImmutableMap;
import market.TradeResult;

public class Main {
    public static void main(String [] args) {
        int goodId = 1234;
        int history = 1000;

        ValuationStrategy learnedStrategy = new LearnedNormalDistributionValuationStrategy(goodId, history);

        ValuationStrategy fixedStrategy = new FixedVarianceNormalDistributionValuationStrategy(goodId, history, 0.05);

        ImmutableMap<Double, ValuationStrategy> composite = ImmutableMap.of(0.0, learnedStrategy, 1.0, fixedStrategy);

        ValuationStrategy overallStrategy = new CompositeValuationStrategy(goodId, composite);


        TradeResult trade1 = new TradeResult(goodId, 100, 100, 50);
        TradeResult trade2 = new TradeResult(goodId, 250, 250, 30);
        TradeResult trade3 = new TradeResult(goodId, 100, 100, 60);

        overallStrategy.processTradeResult(trade1);
        System.out.println(overallStrategy.probabilityOfGoodTrade(51));
        overallStrategy.processTradeResult(trade2);
        System.out.println(overallStrategy.probabilityOfGoodTrade(50));
    }

}
