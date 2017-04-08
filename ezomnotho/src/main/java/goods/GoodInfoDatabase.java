package goods;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static goods.GoodId.*;

public class GoodInfoDatabase {

    private ImmutableMap<GoodTag, ImmutableList<GoodId>> tagToIds;
    private ImmutableMap<GoodId, GoodInfo> goodIdToInfo;

    private GoodInfoDatabase(ImmutableMap<GoodId, GoodInfo> goodIdToInfo,
                             ImmutableMap<GoodTag, ImmutableList<GoodId>> tagToIds) {
        this.tagToIds = tagToIds;
        this.goodIdToInfo = goodIdToInfo;
    }

    public ImmutableList<GoodInfo> allGoods() {
        return goodIdToInfo.values().asList();
    }

    public GoodInfo infoFor(GoodId goodId) {
        if (!goodIdToInfo.containsKey(goodId)) {
            throw new IllegalArgumentException("Requested unknown goodId: " + goodId);
        }
        return goodIdToInfo.get(goodId);
    }

    public ImmutableList<GoodId> goodsWithTag(GoodTag tag) {
        return tagToIds.getOrDefault(tag, ImmutableList.of());
    }

    public static GoodInfoDatabase create() {
        /**
         * Just define goods inline here
         */
        List<GoodInfo> goodsToAdd = makeGoods();

        Map<GoodTag, List<GoodId>> tempTagToIds = new HashMap<>();
        Map<GoodId, GoodInfo> tempIdToInfo = new HashMap<>();

        for (GoodInfo info : goodsToAdd) {
            tempIdToInfo.put(info.id, info);
            for (GoodTag tag : info.tags) {
                if (tempTagToIds.containsKey(tag)) {
                    tempTagToIds.get(tag).add(info.id);
                } else {
                    List<GoodId> ids = new ArrayList<>();
                    ids.add(info.id);
                    tempTagToIds.put(tag, ids);
                }
            }
        }

        ImmutableMap.Builder <GoodTag, ImmutableList<GoodId>> builder = ImmutableMap.builder();
        tempTagToIds.entrySet().forEach(entry -> builder.put(entry.getKey(), ImmutableList.copyOf(entry.getValue())));
        ImmutableMap.copyOf(tempIdToInfo);

        return new GoodInfoDatabase(ImmutableMap.copyOf(tempIdToInfo), builder.build());
    }

    private static List<GoodInfo> makeGoods() {
        List<GoodInfo> goods = new ArrayList<>();

        // YEAAAAH
        goods.add(GoodInfo.createSpecial(TIME, ImmutableList.of(), ImmutableList.of(SpecialGoodProperties.UNTRADEABLE)));

        // todo: make id assignment automatic because this is embarrassing
        // in fact just factor out goodids entirely
        goods.add(GoodInfo.create(FISH, ImmutableList.of(GoodTag.FOOD)));
        goods.add(GoodInfo.create(WOOD, ImmutableList.of(GoodTag.FUEL)));
        goods.add(GoodInfo.create(GEM, ImmutableList.of(GoodTag.LUXURY)));
        goods.add(GoodInfo.create(CABBAGE, ImmutableList.of(GoodTag.FOOD)));
        goods.add(GoodInfo.create(COAL, ImmutableList.of(GoodTag.FUEL)));
        goods.add(GoodInfo.create(POETRY, ImmutableList.of(GoodTag.ENTERTAINMENT)));
        goods.add(GoodInfo.create(SALMON_MEUNIERE, ImmutableList.of(GoodTag.LUXURY, GoodTag.FOOD)));
        goods.add(GoodInfo.create(SAUERKRAUT, ImmutableList.of(GoodTag.LUXURY, GoodTag.FOOD)));
        goods.add(GoodInfo.create(RING, ImmutableList.of(GoodTag.LUXURY)));
        goods.add(GoodInfo.create(WOODEN_HORSE, ImmutableList.of(GoodTag.LUXURY, GoodTag.ENTERTAINMENT)));

        return goods;
    }

}
