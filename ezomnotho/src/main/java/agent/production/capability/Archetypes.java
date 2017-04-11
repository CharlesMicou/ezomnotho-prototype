package agent.production.capability;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import goods.GoodId;

/**
 * A producer archetype is a static set of production capabilities provided for ease of constructing agents.
 */
public class Archetypes {
    public static ImmutableSet<ProductionCapability> Fisherman() {
        return ImmutableSet.of(SimpleProductionCapabilityImpl.create(GoodId.FISH, ImmutableMap.of(GoodId.TIME, 5)));
    }

    public static ImmutableSet<ProductionCapability> Farmer() {
        return ImmutableSet.of(SimpleProductionCapabilityImpl.create(GoodId.CABBAGE, ImmutableMap.of(GoodId.TIME, 3)));
    }

    public static ImmutableSet<ProductionCapability> Lumberjack() {
        return ImmutableSet.of(SimpleProductionCapabilityImpl.create(GoodId.WOOD, ImmutableMap.of(GoodId.TIME, 2)));
    }

    public static ImmutableSet<ProductionCapability> Miner() {
        return ImmutableSet.of(
                SimpleProductionCapabilityImpl.create(GoodId.COAL, ImmutableMap.of(GoodId.TIME, 3)),
                SimpleProductionCapabilityImpl.create(GoodId.GEM, ImmutableMap.of(GoodId.TIME, 11)));
    }

    public static ImmutableSet<ProductionCapability> Poet() {
        return ImmutableSet.of(SimpleProductionCapabilityImpl.create(GoodId.POETRY, ImmutableMap.of(GoodId.TIME, 10)));
    }

    public static ImmutableSet<ProductionCapability> Artisan() {
        return ImmutableSet.of(
                SimpleProductionCapabilityImpl.create(GoodId.RING, ImmutableMap.of(GoodId.TIME, 10, GoodId.GEM, 1)),
                SimpleProductionCapabilityImpl.create(GoodId.WOODEN_HORSE, ImmutableMap.of(GoodId.TIME, 20, GoodId.WOOD, 2)));
    }

    public static ImmutableSet<ProductionCapability> Chef() {
        return ImmutableSet.of(
                SimpleProductionCapabilityImpl.create(GoodId.SALMON_MEUNIERE, ImmutableMap.of(GoodId.TIME, 10, GoodId.FISH, 1)),
                SimpleProductionCapabilityImpl.create(GoodId.SAUERKRAUT, ImmutableMap.of(GoodId.TIME, 5, GoodId.CABBAGE, 3)));
    }
}
