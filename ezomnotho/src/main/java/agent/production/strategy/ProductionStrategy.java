package agent.production.strategy;

import agent.inventory.Inventory;
import agent.production.ProductionOrder;
import com.google.common.collect.ImmutableList;

/**
 * A production strategy decides on production of goods based on existing inventory and production capabilities.
 * It does not change the inventory.
 */
public interface ProductionStrategy {
    ImmutableList<ProductionOrder> makeProductionOrders(Inventory currentInventory);
}
