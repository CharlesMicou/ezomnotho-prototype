package agent.demand;

import goods.GoodId;

public interface DemandModel {
    /**
     * A probabilistic description of the need for the good.
     * i.e. an agent can say that it needs a good so much that it will take a 75% chance of a deal being
     * bad.
     * @param goodId the good we express the need for.
     * @return a value between 0 and 1. 0 is no need for the good, 1 will take any offer of the good.
     */
    double needForGood(GoodId goodId);
}
