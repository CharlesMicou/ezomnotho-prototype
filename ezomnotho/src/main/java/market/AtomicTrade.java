package market;

import agent.Agent;

/**
 * Okay so this isn't an atomic trade now but it totally should be
 */
public class AtomicTrade {

    private AtomicTrade(){};

    public static void makeTrade(Agent buyer, Agent seller, int goodId, int quantity, double pricePerItem) {
        buyer.sellGoodsTo(goodId, quantity, pricePerItem);
        seller.buyGoodsFrom(goodId, quantity, pricePerItem);
    }
}
