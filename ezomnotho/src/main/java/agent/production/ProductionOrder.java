package agent.production;

import com.google.common.collect.ImmutableMap;
import goods.GoodId;

/**
 * A production order contains a good to produce, and which materials are used up in the production of that good.
 */
public class ProductionOrder {
    public final ImmutableMap<GoodId, Integer> producedGoods;
    public final ImmutableMap<GoodId, Integer> consumedGoods;

    public ProductionOrder(ImmutableMap<GoodId, Integer> producedGoods, ImmutableMap<GoodId, Integer> consumedGoods) {
        this.producedGoods = producedGoods;
        this.consumedGoods = consumedGoods;
    }
}
