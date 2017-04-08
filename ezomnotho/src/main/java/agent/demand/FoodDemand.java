package agent.demand;

import goods.GoodId;
import goods.GoodTag;

public class FoodDemand implements Demand {

    /**
     * An example demand for food. Generalise this later.
     * @param tag
     */

    FoodDemand(GoodTag tag) {

    }

    @Override
    public double needForGood(GoodId goodId) {
        return 0;
    }
}
