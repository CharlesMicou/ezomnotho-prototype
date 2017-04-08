package agent.production.capability;

import com.google.common.collect.ImmutableMap;
import goods.GoodId;

import java.util.Map;

/**
 * This is very basic production. A constant number of ingredients go into making a single item.
 * There are no modifiers or anything fancy.
 */
public class SimpleProductionCapabilityImpl implements ProductionCapability {

    private final GoodId producedGood;
    private final ImmutableMap<GoodId, Integer> requiredGoods;

    private SimpleProductionCapabilityImpl(GoodId producedGood, ImmutableMap<GoodId, Integer> requiredGoods) {
        this.producedGood = producedGood;
        this.requiredGoods = requiredGoods;
    }

    public static SimpleProductionCapabilityImpl create(GoodId producedGood, ImmutableMap<GoodId, Integer> requiredGoods) {
        for (int quantity : requiredGoods.values()) {
            if (quantity < 1) {
                throw new IllegalArgumentException("Tried to create a production impl for good " + producedGood +
                " with a required ingredient quantity of less than one.");
            }
        }
        return new SimpleProductionCapabilityImpl(producedGood, requiredGoods);
    }

    @Override
    public GoodId producedGoodId() {
        return producedGood;
    }

    @Override
    public ImmutableMap<GoodId, Integer> costToProduce(int numberToProduce) {
        return requiredGoods;
    }

    @Override
    public int maxQuantityAvailable(ImmutableMap<GoodId, Integer> availableGoods) {
        int quantityAvailable = Integer.MAX_VALUE;

        for (Map.Entry<GoodId, Integer> requiredGoodAndQuantity : requiredGoods.entrySet()) {
            if (!availableGoods.containsKey(requiredGoodAndQuantity.getKey())) {
                // Available goods don't contain something we need!
                return 0;
            }

            int required = availableGoods.get(requiredGoodAndQuantity.getKey()) / requiredGoodAndQuantity.getValue();
            quantityAvailable = Math.min(quantityAvailable, required);
        }
        return quantityAvailable;
    }
}
