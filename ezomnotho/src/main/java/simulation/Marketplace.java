package simulation;

import agent.Agent;
import com.google.common.collect.ImmutableList;
import common.TradeOffer;
import common.TradeResponse;
import common.TradeResult;

import java.util.HashMap;
import java.util.List;

public class Marketplace {
    private final ImmutableList<Agent> agents;
    private List<TradeResult> results;

    Marketplace(ImmutableList<Agent> agents) {
        this.agents = agents;
    }

    public void runMarket() {
        agents.forEach(agent -> agent.createTradeOffers().forEach(offer -> results.add(offer.resolve())));
        agents.forEach(agent -> results.forEach(result -> agent.processTradeResult(result)));
    }

}
