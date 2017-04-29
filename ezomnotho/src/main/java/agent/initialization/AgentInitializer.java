package agent.initialization;

import agent.Agent;
import goods.GoodId;
import goods.GoodInfo;
import goods.GoodInfoDatabase;
import market.TradeResult;

import java.util.Random;

/**
 * For the moment this is a hack to generate a load of dummy trades to start the agents off.
 * In future, training data sets would be really sensible to have.
 */
public class AgentInitializer {
    private Random random;
    private GoodInfoDatabase goodInfoDatabase;

    private static final int MAX_QUANTITY_OFFERED = 20;
    private static final double MAX_ITEM_PRICE = 500.0;
    private static final double OVERDEMAND_FACTOR = 2.5;

    public AgentInitializer() {
        random = new Random();
        this.goodInfoDatabase = GoodInfoDatabase.create();
    }

    public void train(Agent agent) {
        for (GoodInfo goodInfo : goodInfoDatabase.allGoods()) {
            for (int i = 0; i < 50; i++) {
                agent.processTradeResult(makeRandomTradeResult(goodInfo.id));
            }
        }
    }

    private TradeResult makeRandomTradeResult(GoodId goodId) {
        int quantityOffered = random.nextInt(MAX_QUANTITY_OFFERED) + 1;
        double askingPrice = MAX_ITEM_PRICE * random.nextDouble();
        int quantityTraded;
        int quantityDesired;
        // decide whether to make supply greater or less than demand
        if (random.nextDouble() > 0.5) {
            // supply > demand
            quantityDesired = (int) (quantityOffered * random.nextDouble());
            quantityTraded = quantityDesired;
        } else {
            quantityTraded = quantityOffered;
            quantityDesired = (int) ((1 + OVERDEMAND_FACTOR * random.nextDouble()) * quantityOffered);
        }
        return new TradeResult(goodId, quantityOffered, quantityTraded, quantityDesired, askingPrice);
    }
}
