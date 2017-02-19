package goods;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodInfoDatabase {

    private ImmutableMap<GoodTag, ImmutableList<Integer>> tagToIds;
    private ImmutableMap<Integer, GoodInfo> goodIdToInfo;
    private ImmutableMap<String, Integer> friendlyNameMapping;

    private GoodInfoDatabase(ImmutableMap<Integer, GoodInfo> goodIdToInfo,
                             ImmutableMap<GoodTag, ImmutableList<Integer>> tagToIds,
                             ImmutableMap<String, Integer> friendlyNameMapping) {
        this.tagToIds = tagToIds;
        this.goodIdToInfo = goodIdToInfo;
        this.friendlyNameMapping = friendlyNameMapping;
    }

    public ImmutableList<GoodInfo> allGoods() {
        return goodIdToInfo.values().asList();
    }

    public GoodInfo infoFor(int goodId) {
        if (!goodIdToInfo.containsKey(goodId)) {
            throw new IllegalArgumentException("Requested unknown goodId: " + goodId);
        }
        return goodIdToInfo.get(goodId);
    }

    public ImmutableList<Integer> goodsWithTag(GoodTag tag) {
        return tagToIds.getOrDefault(tag, ImmutableList.of());
    }

    /**
     *  Time is a special case.
     */
    public static int TIME_GOOD_ID = 0;

    public static GoodInfoDatabase create() {
        /**
         * Just define goods inline here
         */
        List<GoodInfo> goodsToAdd = makeGoods();

        Map<String, Integer> tempNameToId = new HashMap<>();
        Map<GoodTag, List<Integer>> tempTagToIds = new HashMap<>();
        Map<Integer, GoodInfo> tempIdToInfo = new HashMap<>();

        for (GoodInfo info : goodsToAdd) {
            tempIdToInfo.put(info.id, info);
            tempNameToId.put(info.name, info.id);
            for (GoodTag tag : info.tags) {
                if (tempTagToIds.containsKey(tag)) {
                    tempTagToIds.get(tag).add(info.id);
                } else {
                    List<Integer> ids = new ArrayList<>();
                    ids.add(info.id);
                    tempTagToIds.put(tag, ids);
                }
            }
        }

        ImmutableMap.Builder <GoodTag, ImmutableList<Integer>> builder = ImmutableMap.builder();
        tempTagToIds.entrySet().forEach(entry -> builder.put(entry.getKey(), ImmutableList.copyOf(entry.getValue())));


        ImmutableMap.copyOf(tempIdToInfo);

        return new GoodInfoDatabase(ImmutableMap.copyOf(tempIdToInfo), builder.build(), ImmutableMap.copyOf(tempNameToId));
    }

    private static List<GoodInfo> makeGoods() {
        List<GoodInfo> goods = new ArrayList<>();

        // YEAAAAH
        goods.add(GoodInfo.createSpecial(TIME_GOOD_ID, "Time", ImmutableList.of(), ImmutableList.of(SpecialGoodProperties.UNTRADEABLE)));

        goods.add(GoodInfo.create(1, "Fish", ImmutableList.of(GoodTag.FOOD)));
        goods.add(GoodInfo.create(2, "Firewood", ImmutableList.of(GoodTag.FUEL)));
        goods.add(GoodInfo.create(3, "Sparkling Gem", ImmutableList.of(GoodTag.LUXURY)));
        goods.add(GoodInfo.create(4, "Potato", ImmutableList.of(GoodTag.FOOD)));

        return goods;
    }

}
