package agent.production;

import com.google.common.collect.ImmutableMap;

public interface ProductionCapability {

    int producedGood();

    ImmutableMap<Integer, Integer> costToProduce(int numberToProduce);

    int maxQuantityAvailable(ImmutableMap<Integer, Integer> availableGoods);

}
