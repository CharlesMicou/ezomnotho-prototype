package market;

import agent.Agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TradeOffer {
    public final int goodId;
    public final int initialQuantity;
    public final double pricePerItem;
    public final Agent creator;
    private int remainingQuantity;
    private List<TradeResponse> responses;

    TradeOffer(int goodId, double pricePerItem, int quantity, Agent creator) {
        this.goodId = goodId;
        this.pricePerItem = pricePerItem;
        this.initialQuantity = quantity;
        this.remainingQuantity = quantity;
        this.responses = new ArrayList<>();
        this.creator = creator;
    }

    public void registerResponse(TradeResponse response) {
        responses.add(response);
    }

    TradeResult resolve() {
        // Randomise buyers
        Collections.shuffle(responses);

        int desiredQuantity = 0;
        for (TradeResponse response : responses) {
            desiredQuantity += response.quantity;
            int tradeQuantity = Math.min(remainingQuantity, response.quantity);
            creator.buyGoodsFrom(goodId, tradeQuantity, pricePerItem);
            response.agent.sellGoodsTo(goodId, tradeQuantity, pricePerItem);
        }

        return new TradeResult(goodId, initialQuantity, desiredQuantity, pricePerItem);
    }
}