package agent.demand;

import goods.GoodId;

import java.util.Random;

/**
 * Value everything at face value until I actually implement this.
 */
public class DummyDemandModel implements DemandModel {

    Random random = new Random();

    public DummyDemandModel() {}

    @Override
    public double needForGood(GoodId goodId) {
        return 0.3 + random.nextDouble() * 0.4;
    }
}
