package market;

import agent.Agent;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class Marketplace {
    private final ImmutableList<Agent> agents;
    private List<TradeResult> results;

    public Marketplace(ImmutableList<Agent> agents) {
        this.agents = agents;
        this.results = new ArrayList<>();
    }

    public void runMarket() {
        results.clear();
        for (Agent agent : agents) {
            for (TradeOffer offer : agent.createTradeOffers()) {
                System.out.println(offer.toString());
                agents.forEach(potentialBuyers -> potentialBuyers.processOffer(offer));
                results.add(offer.resolve());
            }
        }
        for (TradeResult result : results) {
            System.out.println(result.toString());
            for (Agent agent : agents) {
                agent.processTradeResult(result);
            }
        }
    }
}
