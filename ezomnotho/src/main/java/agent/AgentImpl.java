package agent;

import agent.inventory.Inventory;
import agent.production.ProductionOrder;
import agent.production.capability.ProductionCapability;
import agent.production.strategy.ProductionStrategy;
import agent.valuation.strategy.ValuationStrategy;
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
    private final ProductionStrategy productionStrategy;
    private final ImmutableMap<Integer, ValuationStrategy> valuationStrategies;
    private static final int TIME_PER_PRODUCTION_CYCLE = 10;

    public AgentImpl(double initialMoney,
                     GoodInfoDatabase goodInfoDatabase,
                     ImmutableList<ProductionCapability> productionCapabilities,
                     ImmutableMap<Integer, ValuationStrategy> valuationStrategies,
                     ProductionStrategy productionStrategy) {
        this.goodInfoDatabase = goodInfoDatabase;
        this.inventory = new Inventory(initialMoney, ImmutableMap.of(), goodInfoDatabase);
        this.productionCapabilities = productionCapabilities;
        this.valuationStrategies = valuationStrategies;
        this.productionStrategy = productionStrategy;
    }


    @Override
    public ImmutableList<TradeOffer> createTradeOffers() {
        // Valuation strategy: How much are the goods worth to us?
        // Demand strategy: Which goods are worth more on the market than they are to us?
        // Sell Decision strategy: Produce a shortlist of the above, ranked by most lucrative.
        return null;
    }

    @Override
    public TradeResponse processOffer(TradeOffer offer) {
        // Demand strategy: how much do we need the good?
        // Valuation strategy: how much do we think the good is worth?
        // Buy Decision strategy: should we take the trade? and if so, how much?
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
        for (ProductionOrder order : productionStrategy.makeProductionOrders(inventory)) {
            order.producedGoods.entrySet().forEach(entry -> inventory.addGoods(entry.getKey(), entry.getValue()));
            order.consumedGoods.entrySet().forEach(entry -> inventory.removeGoods(entry.getKey(), entry.getValue()));
        }
    }
}
