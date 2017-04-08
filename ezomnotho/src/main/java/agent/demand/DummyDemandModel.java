package agent.demand;

import goods.GoodId;

/**
 * Value everything at face value until I actually implement this.
 */
public class DummyDemandModel implements DemandModel {

    DummyDemandModel() {

    }

    @Override
    public double needForGood(GoodId goodId) {
        return 0.5;
    }
}
