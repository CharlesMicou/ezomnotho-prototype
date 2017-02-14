package agent;

import com.google.common.collect.ImmutableList;
import market.TradeOffer;
import market.TradeResponse;
import market.TradeResult;

import java.util.concurrent.ConcurrentHashMap;

public class AgentImpl implements Agent {

    private ConcurrentHashMap<Integer, Integer> ownedGoods;
    private double ownedMoney;

    public AgentImpl(double initialMoney){
        this.ownedMoney = initialMoney;
        this.ownedGoods = new ConcurrentHashMap<>();
    };


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
        ownedGoods.compute(goodId, (k, v) -> v == null ? quantity : v + quantity);
        ownedMoney -= pricePerItem * quantity;
    }

    @Override
    public void buyGoodsFrom(int goodId, int quantity, double pricePerItem) {
        ownedGoods.compute(goodId, (k, v) -> v == null ? 0 : v + quantity);
        ownedMoney -= pricePerItem * quantity;
    }
}
