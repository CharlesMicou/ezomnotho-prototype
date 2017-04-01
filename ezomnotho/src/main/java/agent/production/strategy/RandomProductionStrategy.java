package agent.production.strategy;

import agent.inventory.Inventory;
import agent.production.ProductionOrder;
import agent.production.capability.ProductionCapability;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import goods.GoodInfoDatabase;

import java.util.*;

/**
 * Produce goods at random from all of the goods we can produce, until we have exhausted all the
 * time available to make goods with.
 */
public class RandomProductionStrategy implements ProductionStrategy {
    private final ImmutableList<ProductionCapability> productionCapabilities;
    private final Random random;

    public RandomProductionStrategy(ImmutableList<ProductionCapability> productionCapabilities) {
        this.productionCapabilities = ImmutableList.copyOf(productionCapabilities);
        this.random = new Random();
    }

    @Override
    public ImmutableList<ProductionOrder> makeProductionOrders(Inventory currentInventory) {
        HashMap<Integer, Integer> remainingGoods = new HashMap<>();
        remainingGoods.putAll(currentInventory.getAllGoods());
        List<ProductionCapability> remainingCapabilities = new ArrayList<>();
        remainingCapabilities.addAll(productionCapabilities);
        List<ProductionOrder> ordersToMake = new ArrayList<>();

        while (!remainingCapabilities.isEmpty()) {
            int i = random.nextInt(remainingCapabilities.size());
            ProductionCapability capability = remainingCapabilities.get(i);
            int available = capability.maxQuantityAvailable(ImmutableMap.copyOf(remainingGoods));
            if (available > 0) {
                int numberToMake = random.nextInt(available + 1);
                ImmutableMap<Integer, Integer> costIncurred = capability.costToProduce(numberToMake);
                ordersToMake.add(new ProductionOrder(
                        ImmutableMap.of(capability.producedGood(), numberToMake), costIncurred));
                // update the goods
                costIncurred.entrySet().forEach(
                        entry -> remainingGoods.put(
                                entry.getKey(),
                                remainingGoods.get(entry.getKey()) - entry.getValue()));
            } else {
                remainingCapabilities.remove(i);
            }
        }
        return null;
    }
}