package market;

import agent.Agent;
import com.google.common.collect.ImmutableList;
import logging.LoggingFactory;
import logging.MarketLogger;

import java.util.ArrayList;
import java.util.List;

public class Marketplace {
    private final ImmutableList<Agent> agents;
    private MarketLogger logger;
    private int marketClock;

    public Marketplace(ImmutableList<Agent> agents) {
        this.agents = agents;
        this.logger = LoggingFactory.createMarketLogger();
        this.marketClock = 0;
    }

    public void runMarket() {
        marketClock++;
        List<TradeResult> results = new ArrayList<>();
        for (Agent agent : agents) {
            for (TradeOffer offer : agent.createTradeOffers()) {
                logger.logTradeOffer(offer, marketClock);
                agents.forEach(potentialBuyers -> potentialBuyers.processOffer(offer));
                results.add(offer.resolve());
            }
        }
        for (TradeResult result : results) {
            logger.logTradeResult(result, marketClock);
            for (Agent agent : agents) {
                agent.processTradeResult(result);
            }
        }
    }
}
