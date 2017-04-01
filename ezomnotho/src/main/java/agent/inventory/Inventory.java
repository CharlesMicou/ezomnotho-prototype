package agent.inventory;

import com.google.common.collect.ImmutableMap;
import goods.GoodInfoDatabase;

import java.util.concurrent.ConcurrentHashMap;

public class Inventory {

    private GoodInfoDatabase goodInfoDatabase;
    private ConcurrentHashMap<Integer, Integer> ownedGoods;


    private double ownedMoney;

    public Inventory(double initialMoney,
                     ImmutableMap<Integer, Integer> initialInventory,
                     GoodInfoDatabase goodInfoDatabase){
        this.ownedMoney = initialMoney;
        this.ownedGoods = new ConcurrentHashMap<>();
        this.goodInfoDatabase = goodInfoDatabase;

        // Initialize inventory
        goodInfoDatabase.allGoods().forEach(goodInfo -> ownedGoods.put(goodInfo.id, 0));
        initialInventory.entrySet().forEach(entry -> ownedGoods.put(entry.getKey(), entry.getValue()));
    }

    public double getOwnedMoney() {
        return ownedMoney;
    }

    public int getQuantityOfGood(int goodId) {
        return ownedGoods.get(goodId);
    }

    public ImmutableMap<Integer, Integer> getAllGoods() {
        return ImmutableMap.copyOf(ownedGoods);
    }

    public void removeMoney(double money) {
        addMoney(-money);
    }

    public void addMoney(double money) {
        ownedMoney += money;
    }

    public void addGoods(int goodId, int quantity) {
        ownedGoods.put(goodId, ownedGoods.get(goodId) + quantity);
    }

    public void removeGoods(int goodId, int quantity) {
        addGoods(goodId, -quantity);
    }

}
