package integration;

import agent.Agent;
import agent.AgentFactory;
import agent.initialization.AgentInitializer;
import com.google.common.collect.ImmutableList;
import logging.MiniLogger;
import market.Marketplace;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        AgentFactory agentFactory = new AgentFactory();
        AgentInitializer agentInitializer = new AgentInitializer();

        ImmutableList<Agent> agents = ImmutableList.of(
                agentFactory.makeFarmer(), agentFactory.makeFisherman(), agentFactory.makeLumberjack());

        agents.forEach(agentInitializer::train);
        agents.forEach(Agent::marketTick);

        Marketplace marketplace = new Marketplace(agents);

        for (int i = 0; i < 30; i++) {
            agents.forEach(Agent::produce);
            marketplace.runMarket();
            agents.forEach(Agent::marketTick);
        }

        System.out.println("Managed to run without blowing up, congrats.");
    }
}
