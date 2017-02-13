package market;

import agent.Agent;

public class TradeResponse {
    public final int quantity;
    public final Agent agent;

    TradeResponse(Agent agent, int quantity) {
        this.agent = agent;
        this.quantity = quantity;
    }
}
