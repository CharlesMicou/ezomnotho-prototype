package common;

import agent.Agent;

import java.util.HashMap;

public class TradeOffer {
    public final int good;
    public final int initialQuantity;
    public final double pricePerItem;
    public final Agent creator;
    private int remainingQuantity;
    private HashMap<Agent, TradeResponse> responses;

    TradeOffer(int goodId, double pricePerItem, int quantity, Agent creator) {
        this.good = goodId;
        this.pricePerItem = pricePerItem;
        this.initialQuantity = quantity;
        this.remainingQuantity = quantity;
        this.responses = new HashMap<>();
        this.creator = creator;
    }

    public void registerResponse(Agent agent, TradeResponse response) {
        responses.put(agent, response);
    }

    public TradeResult resolve() {
        new TradeResult()
    }
}
