package common;

import com.google.common.collect.ImmutableMap;

public final class GoodNames {
    private GoodNames() {}

    private static ImmutableMap<Integer, String> nameMapping = ImmutableMap.<Integer, String>builder()
            .put(0, "potato")
            .put(1, "another good")
            .build();

    public static String nameForGoodId(int goodId) {
        return nameMapping.getOrDefault(goodId, "Unknown good ID");
    }
}
