package integration;

import agent.Agent;
import agent.AgentFactory;
import agent.initialization.AgentInitializer;
import agent.valuation.strategy.CompositeValuationStrategy;
import agent.valuation.strategy.gaussian.FixedVarianceNormalDistributionValuationStrategy;
import agent.valuation.strategy.gaussian.LearnedNormalDistributionValuationStrategy;
import agent.valuation.strategy.ValuationStrategy;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import goods.GoodId;
import goods.GoodInfoDatabase;
import market.Marketplace;
import market.TradeResult;

public class Main {
    public static void main(String[] args) {

        AgentFactory agentFactory = new AgentFactory();
        AgentInitializer agentInitializer = new AgentInitializer();

        ImmutableList<Agent> agents = ImmutableList.of(
                agentFactory.makeFarmer(), agentFactory.makeFisherman(), agentFactory.makeLumberjack());

        agents.forEach(agentInitializer::train);
        agents.forEach(Agent::monitor);

        Marketplace marketplace = new Marketplace(agents);

        /*for (int i = 0; i < 2; i++) {
            agents.forEach(Agent::produce);
            marketplace.runMarket();
            agents.forEach(Agent::monitor);
        }*/


        System.out.println("Managed to run without blowing up, congrats.");

        /*GoodId goodId = GoodId.CABBAGE;
        int history = 1000;

        GoodInfoDatabase.create();

        ValuationStrategy learnedStrategy = new LearnedNormalDistributionValuationStrategy(goodId, history);

        ValuationStrategy fixedStrategy = new FixedVarianceNormalDistributionValuationStrategy(goodId, history, 0.1);

        ImmutableMap<Double, ValuationStrategy> composite = ImmutableMap.of(0.7, learnedStrategy, 0.3, fixedStrategy);

        ValuationStrategy overallStrategy = new CompositeValuationStrategy(goodId, composite);


        TradeResult trade1 = new TradeResult(goodId, 100, 1, 1, 50);
        TradeResult trade2 = new TradeResult(goodId, 250, 3, 1, 30);
        TradeResult trade3 = new TradeResult(goodId, 100, 5, 1, 60);

        overallStrategy.processTradeResult(trade1);
        System.out.println(overallStrategy.valueItem(0.98));
        overallStrategy.processTradeResult(trade2);
        System.out.println(overallStrategy.valueItem(0.6));
        overallStrategy.processTradeResult(trade3);
        System.out.println(overallStrategy.valueItem(0.6));*/
    }

    // initialize agents
    // make a new marketplace

}
