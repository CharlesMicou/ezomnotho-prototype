package agent.production;

import com.google.common.collect.ImmutableMap;

/**
 * A production order contains a good to produce, and which materials are used up in the production of that good.
 */
public class ProductionOrder {
    public final ImmutableMap<Integer, Integer> producedGoods;
    public final ImmutableMap<Integer, Integer> consumedGoods;

    public ProductionOrder(ImmutableMap<Integer, Integer> producedGoods, ImmutableMap<Integer, Integer> consumedGoods) {
        this.producedGoods = producedGoods;
        this.consumedGoods = consumedGoods;
    }
}
