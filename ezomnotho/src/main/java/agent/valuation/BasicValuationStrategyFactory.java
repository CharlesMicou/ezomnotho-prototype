package agent.valuation;

import agent.valuation.strategy.CompositeValuationStrategy;
import agent.valuation.strategy.ValuationStrategy;
import agent.valuation.strategy.gaussian.*;
import com.google.common.collect.Range;
import goods.GoodId;

import java.util.ArrayList;
import java.util.List;

public class BasicValuationStrategyFactory {

    // todo configurable random ranges
    private static final Range<Integer> HISTORY_RANGE = Range.closed(25, 75);

    public BasicValuationStrategyFactory() {
    }

    public ValuationStrategy makeStrategyFor(GoodId goodId) {
        List<ValuationStrategy> strategies = new ArrayList<>();

        strategies.add(makeSimpleLearnedGaussian(goodId, 100));
        strategies.add(makeSimpleLearnedGaussian(goodId, 200));
        strategies.add(makeSimpleLearnedGaussian(goodId, 25));
        strategies.add(makeFixedGaussian(goodId, 100, 0.2));
        strategies.add(makeSmoothedMeanGaussian(goodId, 0.5, 0.3));
        strategies.add(makeUnmetTradesFixedVarianceExpoMean(goodId, 0.5, 0.2));
        /*strategies.add(makeUnmetTradesFixedVarianceExpoMean(goodId, 0.25, 0.2));
        strategies.add(makeUnmetTradesFixedVarianceExpoMean(goodId, 0.75, 0.2));
        strategies.add(makeUnmetTradesLearnedVarianceGaussian(goodId, 100));
        strategies.add(makeUnmetTradesLearnedVarianceGaussian(goodId, 50));*/

        return CompositeValuationStrategy.uniformStrategy(goodId, strategies);
    }

    // simple learned variance gaussian: historic mean and variance
    private ValuationStrategy makeSimpleLearnedGaussian(GoodId goodId, int history) {
        SimpleAverageMean meanProvider = new SimpleAverageMean(history);
        SimpleLearnedVariance varianceProvider = new SimpleLearnedVariance(history);
        return new GaussianValuationStrategy(goodId, meanProvider, varianceProvider);
    }

    // simple gaussian: historic mean, fixed fraction variance
    private ValuationStrategy makeFixedGaussian(GoodId goodId, int history, double breadth) {
        SimpleAverageMean meanProvider = new SimpleAverageMean(history);
        FixedFractionVariance varianceProvider = new FixedFractionVariance(breadth, meanProvider);
        return new GaussianValuationStrategy(goodId, meanProvider, varianceProvider);
    }

    // simple smoothed mean gaussian: historic expo-smoothed mean, fixed fraction variance
    private ValuationStrategy makeSmoothedMeanGaussian(GoodId goodId, double alpha, double breadth) {
        ExponentialSmoothedMean meanProvider = new ExponentialSmoothedMean(alpha);
        FixedFractionVariance varianceProvider = new FixedFractionVariance(breadth, meanProvider);
        return new GaussianValuationStrategy(goodId, meanProvider, varianceProvider);
    }

    // simple unmet trades: unmnet trades augmented mean, learned variance
    private ValuationStrategy makeUnmetTradesLearnedVarianceGaussian(GoodId goodId, int history) {
        SimpleAverageMean underlyingMeanProvider = new SimpleAverageMean(history);
        UnmetTradesMean meanProvider = new UnmetTradesMean(underlyingMeanProvider);
        SimpleLearnedVariance varianceProvider = new SimpleLearnedVariance(history);
        return new GaussianValuationStrategy(goodId, meanProvider, varianceProvider);
    }

    // learned unmet trades:
    private ValuationStrategy makeUnmetTradesFixedVarianceExpoMean(GoodId goodId, double alpha, double breadth) {
        ExponentialSmoothedMean underlyingMeanProvider = new ExponentialSmoothedMean(alpha);
        UnmetTradesMean meanProvider = new UnmetTradesMean(underlyingMeanProvider);
        FixedFractionVariance varianceProvider = new FixedFractionVariance(breadth, meanProvider);
        return new GaussianValuationStrategy(goodId, meanProvider, varianceProvider);
    }
}
