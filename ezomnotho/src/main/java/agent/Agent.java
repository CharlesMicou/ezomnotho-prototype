package agent;

import com.google.common.collect.ImmutableList;
import goods.GoodId;
import market.TradeOffer;
import market.TradeResponse;
import market.TradeResult;

/**
 * Agents make up the core of the simulation, but what the agents do to make their decisions
 * happens "behind closed doors". This interface defines the bare minimum of the interaction
 * that the agent has with the simulation.
 */
public interface Agent {
    /**
     * Asks the agent to produce some trade offers.
     * @return The trade offers to put on the market.
     */
    ImmutableList<TradeOffer> createTradeOffers();

    /**
     * Asks the agent if it would accept a given trade.
     * @param offer the offer to consider
     * @return the response to the trade
     */
    TradeResponse processOffer(TradeOffer offer);

    /**
     * Informs the agent of a trade that has happened.
     * @param result the result of an observed trade
     */
    void processTradeResult(TradeResult result);

    /**
     * Sell goods to the agent in exchange for money.
     * @param goodId the id of the good being traded.
     * @param quantity the number of goods being traded.
     * @param pricePerItem the price paid per good.
     */
    void sellGoodsTo(GoodId goodId, int quantity, double pricePerItem);

    /**
     * Buy goods from the agent.
     * @param goodId the id of the good being traded.
     * @param quantity the number of goods being traded.
     * @param pricePerItem the price paid per good.
     */
    void buyGoodsFrom(GoodId goodId, int quantity, double pricePerItem);

    /**
     * @return an identifier (name) of the agent
     */
    String id();

    /**
     *  The agent performs its actions for the production cycle.
     */
    void produce();

    /**
     *  Inform the agent that we've moved to a new market phase so that it can do internal bookkeeping.
     */
    void marketTick();
}

