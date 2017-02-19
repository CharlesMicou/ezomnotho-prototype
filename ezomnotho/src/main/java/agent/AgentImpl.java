package agent;

import agent.inventory.Inventory;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import goods.GoodInfoDatabase;
import market.TradeOffer;
import market.TradeResponse;
import market.TradeResult;

import java.util.concurrent.ConcurrentHashMap;

public class AgentImpl implements Agent {

    private Inventory inventory;
    private GoodInfoDatabase goodInfoDatabase;

    public AgentImpl(double initialMoney, GoodInfoDatabase goodInfoDatabase) {
        this.goodInfoDatabase = goodInfoDatabase;
        this.inventory = new Inventory(initialMoney, ImmutableMap.of(), goodInfoDatabase);
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
}
