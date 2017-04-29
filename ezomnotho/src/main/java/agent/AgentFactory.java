package agent;

import agent.demand.DummyDemandModel;
import agent.production.capability.Archetypes;
import agent.production.strategy.RandomProductionStrategy;
import agent.valuation.ArchValuationStrategyFactory;
import goods.GoodInfoDatabase;

public class AgentFactory {
    // Grab an archetype from production archetypes
    // Grab a set of tastes

    private final GoodInfoDatabase goodInfoDatabase;
    private static final double INITIAL_MONEY = 100;
    private ArchValuationStrategyFactory valuationStrategyFactory;

    public AgentFactory() {
        this.goodInfoDatabase = GoodInfoDatabase.create();
        this.valuationStrategyFactory = new ArchValuationStrategyFactory();
    }

    public Agent makeFarmer() {
        return new AgentImpl(
                INITIAL_MONEY,
                goodInfoDatabase,
                Archetypes.Farmer(),
                valuationStrategyFactory.makeArchStrategy(),
                new RandomProductionStrategy(Archetypes.Farmer()),
                new DummyDemandModel(),
                "Farmer");
    }

    public Agent makeFisherman() {
        return new AgentImpl(
                INITIAL_MONEY,
                goodInfoDatabase,
                Archetypes.Fisherman(),
                valuationStrategyFactory.makeArchStrategy(),
                new RandomProductionStrategy(Archetypes.Fisherman()),
                new DummyDemandModel(),
                "Fisherman");
    }

    public Agent makeLumberjack() {
        return new AgentImpl(
                INITIAL_MONEY,
                goodInfoDatabase,
                Archetypes.Lumberjack(),
                valuationStrategyFactory.makeArchStrategy(),
                new RandomProductionStrategy(Archetypes.Lumberjack()),
                new DummyDemandModel(),
                "Lumberjack");
    }
}
