package agent;

import agent.demand.DemandModel;
import agent.inventory.Inventory;
import agent.production.ProductionOrder;
import agent.production.capability.ProductionCapability;
import agent.production.strategy.ProductionStrategy;
import agent.valuation.strategy.ValuationStrategy;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import goods.GoodId;
import goods.GoodInfoDatabase;
import market.TradeOffer;
import market.TradeResponse;
import market.TradeResult;

import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static goods.GoodId.TIME;

public class AgentImpl implements Agent {
    private Inventory inventory;
    private final ImmutableSet<ProductionCapability> productionCapabilities;
    private final ProductionStrategy productionStrategy;
    private final ImmutableMap<GoodId, ValuationStrategy> valuationStrategies;
    private final DemandModel demandModel;
    private Random random;
    private static final int TIME_PER_PRODUCTION_CYCLE = 10;
    public final String agentName;

    public AgentImpl(double initialMoney,
                     GoodInfoDatabase goodInfoDatabase,
                     ImmutableSet<ProductionCapability> productionCapabilities,
                     ImmutableMap<GoodId, ValuationStrategy> valuationStrategies,
                     ProductionStrategy productionStrategy,
                     DemandModel demandModel,
                     String agentName) {
        this.inventory = new Inventory(initialMoney, ImmutableMap.of(), goodInfoDatabase);
        this.productionCapabilities = productionCapabilities;
        this.valuationStrategies = valuationStrategies;
        this.productionStrategy = productionStrategy;
        this.demandModel = demandModel;
        this.random = new Random();
        this.agentName = agentName;
    }


    @Override
    public ImmutableList<TradeOffer> createTradeOffers() {
        // Valuation strategy: How much are the goods worth to us?
        // DemandModel strategy: Which goods are worth more on the market than they are to us?
        //TODO: Better logic for which goods to actually put up for sale
        Map<GoodId, Integer> itemsToSell = inventory.getAllGoods().entrySet().stream()
                .filter(entry -> entry.getValue() < 1)
                .filter(
                        entry -> {
                            GoodId goodId = entry.getKey();
                            double ourDesire = valuationStrategies.get(goodId).valueItem(demandModel.needForGood(goodId));
                            double marketDesire = valuationStrategies.get(goodId).valueItem(0.5);
                            return ourDesire < marketDesire;
                        })
                .collect(Collectors.toMap(Map.Entry::getKey, entry ->  random.nextInt(entry.getValue() + 1)));
        // Sell Decision strategy: Produce a shortlist of the above, ranked by most lucrative.
        // Todo: for now we'll do all for them
        return ImmutableList.copyOf(itemsToSell.entrySet().stream()
                .map(entry -> new TradeOffer(
                        entry.getKey(),
                        valuationStrategies.get(entry.getKey()).valueItem(0.5),
                        entry.getValue(),
                        this))
                .collect(Collectors.toList()));
    }

    @Override
    public TradeResponse processOffer(TradeOffer offer) {
        //TODO: Logic for determining how many we should buy

        // Use our valuation strategy for the good to determine what the value of the good is with respect
        // to how much our demand strategy says we want it.
        double perceivedValue = valuationStrategies.get(offer.goodId).valueItem(demandModel.needForGood(offer.goodId));
        if (offer.pricePerItem <= perceivedValue) {
            // for the moment, buy a random number that we can afford
            int toBuy = random.nextInt((int) Math.ceil(inventory.getOwnedMoney() / offer.pricePerItem));
            return new TradeResponse(this, toBuy);
        } else {
            return new TradeResponse(this, 0);
        }
    }

    @Override
    public void processTradeResult(TradeResult result) {
        valuationStrategies.values().forEach(strategy -> strategy.processTradeResult(result));
    }

    @Override
    public void sellGoodsTo(GoodId goodId, int quantity, double pricePerItem) {
        inventory.addGoods(goodId, quantity);
        inventory.removeMoney(pricePerItem * quantity);
    }

    @Override
    public void buyGoodsFrom(GoodId goodId, int quantity, double pricePerItem) {
        inventory.removeGoods(goodId, quantity);
        inventory.addMoney(pricePerItem * quantity);
    }

    @Override
    public void produce() {
        // The production phase starts with giving the agent time to produce things in.
        inventory.addGoods(TIME, TIME_PER_PRODUCTION_CYCLE);
        for (ProductionOrder order : productionStrategy.makeProductionOrders(inventory)) {
            order.producedGoods.entrySet().forEach(entry -> inventory.addGoods(entry.getKey(), entry.getValue()));
            order.consumedGoods.entrySet().forEach(entry -> inventory.removeGoods(entry.getKey(), entry.getValue()));
        }
    }
}
