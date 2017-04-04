package agent.production.capability;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class SimpleProductionCapabilityImpl implements ProductionCapability {

    private final int producedGood;
    private final ImmutableMap<Integer, Integer> requiredGoods;

    /**
     * This is very basic production. A constant number of ingredients go into making a single item.
     * There are no modifiers or anything fancy.
     */
    private SimpleProductionCapabilityImpl(int producedGood, ImmutableMap<Integer, Integer> requiredGoods) {
        this.producedGood = producedGood;
        this.requiredGoods = requiredGoods;
    }

    public static SimpleProductionCapabilityImpl create(int producedGood, ImmutableMap<Integer, Integer> requiredGoods) {
        for (int quantity : requiredGoods.values()) {
            if (quantity < 1) {
                throw new IllegalArgumentException("Tried to create a production impl for good " + producedGood +
                " with a required ingredient quantity of less than one.");
            }
        }
        return new SimpleProductionCapabilityImpl(producedGood, requiredGoods);
    }

    @Override
    public int producedGoodId() {
        return producedGood;
    }

    @Override
    public ImmutableMap<Integer, Integer> costToProduce(int numberToProduce) {
        return requiredGoods;
    }

    @Override
    public int maxQuantityAvailable(ImmutableMap<Integer, Integer> availableGoods) {
        int quantityAvailable = Integer.MAX_VALUE;

        for (Map.Entry<Integer, Integer> requiredGoodAndQuantity : requiredGoods.entrySet()) {
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
