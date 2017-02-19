package agent;

import agent.inventory.Inventory;
import agent.production.ProductionCapability;
import agent.valuation.ValuationStrategy;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import goods.GoodInfoDatabase;
import market.TradeOffer;
import market.TradeResponse;
import market.TradeResult;

public class AgentImpl implements Agent {
    private Inventory inventory;
    private GoodInfoDatabase goodInfoDatabase;
    private final ImmutableList<ProductionCapability> productionCapabilities;
    private final ImmutableMap<Integer, ValuationStrategy> valuationStrategies;
    private static final int TIME_PER_PRODUCTION_CYCLE = 10;

    public AgentImpl(double initialMoney,
                     GoodInfoDatabase goodInfoDatabase,
                     ImmutableList<ProductionCapability> productionCapabilities,
                     ImmutableMap<Integer, ValuationStrategy> valuationStrategies) {
        this.goodInfoDatabase = goodInfoDatabase;
        this.inventory = new Inventory(initialMoney, ImmutableMap.of(), goodInfoDatabase);
        this.productionCapabilities = productionCapabilities;
        this.valuationStrategies = valuationStrategies;
    }


    @Override
    public ImmutableList<TradeOffer> createTradeOffers() {
        return null;
    }

    @Override
    public TradeResponse processOffer(TradeOffer offer) {
        return null;
    }

    @Override
    public void processTradeResult(TradeResult result) {
        valuationStrategies.values().forEach(strategy -> strategy.processTradeResult(result));
    }

    @Override
    public void sellGoodsTo(int goodId, int quantity, double pricePerItem) {
        inventory.addGoods(goodId, quantity);
        inventory.removeMoney(pricePerItem * quantity);
    }

    @Override
    public void buyGoodsFrom(int goodId, int quantity, double pricePerItem) {
        inventory.removeGoods(goodId, quantity);
        inventory.addMoney(pricePerItem * quantity);
    }

    @Override
    public void produce() {
        // The production phase starts with giving the agent time to produce things in.
        inventory.addGoods(GoodInfoDatabase.TIME_GOOD_ID, TIME_PER_PRODUCTION_CYCLE);
    }
}
