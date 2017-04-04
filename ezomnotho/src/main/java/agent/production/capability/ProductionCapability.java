package agent.production.capability;

import com.google.common.collect.ImmutableMap;

/**
 * A production capability describes the ability to produce a good based on some existing goods as materials.
 */
public interface ProductionCapability {

    /**
     * The good id for the good that this production capability represents.
     */
    int producedGoodId();

    /**
     * Calculate the number of resources required to produce a good with this strategy.
     * @param numberToProduce: the number of the good we want.
     * @return mapping of good id to quantity available to produce with.
     */
    ImmutableMap<Integer, Integer> costToProduce(int numberToProduce);

    /**
     * Calculate the maximum number of goods we can produce using this strategy, for a given number
     * of provided resources.
     * @param availableGoods: a mapping from good id to quantity available of that good.
     * @return the maximum number of goods we can produce.
     */
    int maxQuantityAvailable(ImmutableMap<Integer, Integer> availableGoods);

}
