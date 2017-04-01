package market;

import agent.Agent;

public class TradeResponse {
    public final int quantity;
    public final Agent agent;

    public TradeResponse(Agent agent, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Tried to trade negative goods!");
        }
        this.agent = agent;
        this.quantity = quantity;
    }
}
