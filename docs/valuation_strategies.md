# Valuation Strategies and Machine Learning

The logic used by agents in determining the values of goods in the simulation is essentially an unsupervised learning problem.

What we want the valuation strategy to do is give us a probabilistic description of how valuable we think a good is. Concretely, we want to be able to calculate:

![p = P(x <= X)]

The probability p of the true value x of a good being less than or equal to some value X. This lets us answer a question like "what is the chance that buying cabbage on offer for 10 coins is worth it?".

In order to calculate this, we need some model of the probability distribution.

So let's say that we model the value of x as a Gaussian with mean mu and variance sigma. We can track the mean and the variance of the value of the good for, say, the last 100 instances of that good being traded. If we want to calculate p, all we need to do is to calculate it based on our normal distribution.

But... that calculation for p is only valid if our model is valid, so actually what we have just calculated is ![P(x <= X | M)]: p given that the model M is correct.

So let's use Bayes' theorem to get an expression for p:

![p = P(x <= X) = P(x <= X | M) * P(M) / P(M | x <= X)]

There are two new terms here, can we assign any real meaning to them to attempt to calculate them?

![P(M | x <= X)] the probability that the model is correct, given the observation X. This can be calculated via conjugate prior for Gaussians, which is why we're using them.

![P(M)] 'the probability of the model'. This is slightly trickier in a vacuum: what is the probability our model is correct, considering _all other possible models_? There are ways to try to get at this (e.g. using a Gaussian process), but those are very out-of-scope for what we want to try to accomplish here.

Instead, what we're going to do is consider these two terms relating to the model together. The statement ![P(M) / P(M | x <= X)] can be interpreted as "what is the probability that the model describes my current scenario?".

Imagine some node that knows about multiple modelling strategies (for simplicity, consider that all the models are some Gaussian with differing means and values). This node is fed ![P(x <= X | Mi)] for all of the models. The node is then going to weight each model, such that the weightings w<sub>i</sub> sum to 1.

 <center>![sum(wi) = 1]</center>

 <p><center>![Single Node](https://github.com/CharlesMicou/ezomnotho-prototype/blob/master/docs/img/validation_strategies/single_node.png)</center></p>

These weights serve as an approximation to ![P(M) / P(M | x <= X)] . When we want to calculate ![p = P(x <= X)] , we perform the the sum over all the models of the probability given each model multiplied by the weight of that model (caveat: this makes some independence assumptions):
<center>![sum(wi)pm]</center>

But how do we actually get these weights? This is where the unsupervised learning comes in: given our first observation of some data, we calculate the posterior probabilities ![P(Mi | x = X)] (note that this is the PDF _not_ the CDF). We then proportionally normalize these posteriors such that they sum to 1 (this is equivalent using ![P(M)]). These values become our weights w<sub>i</sub>. When we receive subsequent data, we calculate new values of w<sub>i</sub>, and update the existing values of w<sub>i</sub> based on a combination of the old value and a new value. Simplistically, this can be done with a basic learning rate. We could come up with more sophisticated update strategies--but I haven't yet got around to giving it too much thought.

In practice, it is also helpful to randomize the starting weightings rather than doing it from an initial datapoint (see below).

The output of our 'node' is ![p = P(x <= X)]. _Or is it?_ Well, it's just an approximation to p. So what we've actually calculated is ![P(x <= X | N)] : the probability given that our node is the correct model. This sounds familiar!

<p><center>![Multi node](https://github.com/CharlesMicou/ezomnotho-prototype/blob/master/docs/img/validation_strategies/node_network.png)</center></p>

By having many such nodes, with different models feeding into each, and each initialized differently such that it is possible for them to settle at different local maxima, we're able to build up a better overall model of 'p'. Note that the nodes need to be arranged in a directed acyclic graph, otherwise we can't solve the network.

There is a snag, though: calculating the posterior distributions can not necessarily be done via a convenient conjugate prior, as the underlying distribution isn't necessarily known. We can do (somewhat coarse) numerical integration to brute-force the posterior calculation if necessary. I'll work on this!

## Why is this appropriate for Ezomnotho?

This is an unsupervised learning technique, so that agents can learn on-the-fly in the simulation.

It's _relatively_ computationally lightweight for the quality of results it produces:
 * Each node needs update a set of weightings. In practice the number of links for any given node is going to be less than or equal to the number of underlying models. So a bound on our complexity is O(number of nodes * number of underlying models), plus any complexity inherent to updating those underlying models.
 * We can get away with not having very many nodes by carefully crafting/biasing our network to features we expect in the data. This takes two forms:
  1. Selecting the underlying models we map into the network. Let's say that we have an underlying Gaussian model, there are a variety of ways we can calculate the mean and the variance. We can do a plain average over time, we can do a least squares linear fit and extrapolate, we can have a version of this same fit with a periodic component. We can put all of these underlying models into our overall strategy, and have the network assign weights to the ones that prove successful.
  2. Selecting how we map out the network. We can assign logical meaning to the layers of our network. For example, a 3 hidden layer system might have a 'base layer' (combinations of underlying models), a 'production layer' (models that value goods based on our ability to produce them _and_ how valuable we think they are), and a 'substitution layer' (a proxy to the strategy of other similar goods, but used before their own substitution layers to ensure our network is acyclic).

As we get probability distributions over the prices of goods, we are not only able to ask "what is the chance that buying cabbage on offer for 10 coins is worth it?", but we can also flip the question around ask "how many coins would I need to be offered for the trade to have a 70% chance of being worth it?".

## What does this actually look like in the code?

The agent.valuation package is the implementation of this setup. It models _both_ 'nodes' _and_ 'underlying models' as implementing the `ValuationStrategy` interface. The factories in this package assemble the nodes and tie them together.    


 ## Sources

I went to some [Machine Learning](http://mlg.eng.cam.ac.uk/teaching/4f13/1617/) and [Statistical Pattern Processing](http://mi.eng.cam.ac.uk/~mjfg/local/4F10/) lectures taught at the [CUED](http://www.eng.cam.ac.uk/) in 2015. This project is all hacked together from half-remembered courses, and will almost certainly offend anyone with a background in ML. But hey, it's probably better than my understanding of economics--and that didn't stop me.

 [p = P(x <= X)]: https://latex.codecogs.com/gif.latex?p&space;=&space;P(x&space;\leq&space;X)
 [P(x <= X | M)]: https://latex.codecogs.com/gif.latex?P(x&space;\leq&space;X&space;|M)
 [P(x <= X | N)]: https://latex.codecogs.com/gif.latex?P(x&space;\leq&space;X&space;|N)
 [p = P(x <= X) = P(x <= X | M) * P(M) / P(M | x <= X)]: https://latex.codecogs.com/gif.latex?p&space;=&space;P(x&space;\leq&space;X)&space;=&space;\frac{P(x&space;\leq&space;X&space;|M)&space;P(M)}{P(M|x&space;\leq&space;X)}
 [P(M | x <= X)]: https://latex.codecogs.com/gif.latex?P(M|x&space;\leq&space;X)
 [P(M)]: https://latex.codecogs.com/gif.latex?P(M)
 [P(M) / P(M | x <= X)]: https://latex.codecogs.com/gif.latex?\frac{P(M)}{P(M|x&space;\leq&space;X)}
 [P(x <= X | Mi)]: https://latex.codecogs.com/gif.latex?P(x&space;\leq&space;X&space;|&space;M_{i})
 [P(Mi | x = X)]: https://latex.codecogs.com/gif.latex?P(M_{i}&space;|&space;x=X)
 [sum(wi) = 1]: https://latex.codecogs.com/gif.latex?\sum_{i}^{N_{models}}w_{i}&space;=&space;1
 [sum(wi)pm]: https://latex.codecogs.com/gif.latex?\sum_{i}^{N_{models}}w_{i}P(x&space;\leq&space;X&space;|&space;M_{i})
