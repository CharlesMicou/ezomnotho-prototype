package market;

import agent.Agent;
import goods.GoodId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TradeOffer {
    public final GoodId goodId;
    public final int initialQuantity;
    public final double pricePerItem;
    public final Agent creator;
    private int remainingQuantity;
    private List<TradeResponse> responses;

    public TradeOffer(GoodId goodId, double pricePerItem, int quantity, Agent creator) {
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
            if (tradeQuantity != 0) {
                AtomicTrade.makeTrade(response.agent, creator, goodId, tradeQuantity, pricePerItem);
            }
        }

        return new TradeResult(goodId, initialQuantity, initialQuantity - remainingQuantity, desiredQuantity, pricePerItem);
    }

    @Override
    public String toString() {
        return "TradeOffer{" +
            "\n\tgoodId=" + goodId +
            "\n\tcreator=" + creator.id() +
            "\n\tinitialQuantity=" + initialQuantity +
            ", pricePerItem=" + String.format("%.2f", pricePerItem) +
            ", remainingQuantity=" + remainingQuantity +
            "\n\tresponses=" + responses +
            '}';
    }
}
