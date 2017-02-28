package util.math.probability;

/**
 * This has the behaviour of cutting off the gaussian for X < 0, as we don't want to assign any probability
 * to goods having negative value. In order to make this a true probability distribution, we need to
 * normalize the positive side of the Gaussian.
 */
public class TruncatedGaussian {
    //todo: be lazy we the approximation for now
    //but for when you get off your lazy ass and implement this, remember that:
    // [-----|#######|~~~~~~~]
    // [  A  0   B   |   C   ]
    // where A is the region below X = 0,
    // A + B + C = 1
    // q ( B + C ) = 1
    // so the scaled value of B, B', is just qB.
}
