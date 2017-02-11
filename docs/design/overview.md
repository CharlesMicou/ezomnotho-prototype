# Overview

### Simulation framework

* The simulation harness is follows:
  * Agents are initialised with personalities and initial inventories
  * We loop through two stages:
    * The _marketplace_ stage:
      * Agents post offers to the marketplace
      * Agents decide on offers in the marketplace, resulting in an exchange goods
    * The _work_ stage:
      * Agents produce and consume goods
* The marketplace provides a framework for agents to exchange goods

### Agents

* An agent makes _decisions_:
    * Given a _trade offer_, "I would like to exchange my set of goods for your set of goods",
      the agent can either accept or decline the offer.
    * In a more advanced model, the agent
      might provide some form of feedback in the event of declining,
      e.g.: "I think you undervalued my items by 40%".
* An agent knows about its _inventory_:
    * Inventory consists of a set of _goods_.
    * An agent must be able to put a probabilistic _value_ on these goods:
        * A value has an average level, e.g. $50
        * A value has an uncertainty level, e.g. normally distributed
        * These are algorithmically learned.
* An agent _consumes_ and _produces_:
    * An agent may add goods to its inventory.
    * An agent may remove goods from its inventory.
    * In a more advanced model, producing a good may consume other goods.
* An agent has a _personality_:
    * Agents should not all behave in the same way.
    * _Desires_ modify which items the agent seeks to consume.
    * _Professions_ modify which items the agent is able to produce.
    * _Valuation Strategy_ modifies how the values of goods are learned.
    * _Trade Strategy_ modifies how aggressively decisions can be made.
* An agent has _needs_:
    * An agent must determine how to make trade offers to other agents.
    * A consumption need is an output of a function that takes desires and inventory
      as inputs.
    * A production need is an output of a function that takes professions and inventory
      as inputs.
    * Consumption needs must not be expressed in currency, but in terms of some internal ranking.
    * Production needs may be expressed in terms of currency and estimated profit
    * Example of a consumption need for a good, e.g. fish:
        * If the agent has less than 10 'food' items, need is high: 0.95
        * If the agent has 10 or more, need is low: 0.05; agent does not like fish!
        * A simplistic approach of what 0.95 could represent: the agent is willing to spend
          up to 95% of its currency on fish.
        * This is essentially a personal demand curve
