package util;


/**
 * A counter that won't increase above a maximum value.
 * Set max to 0 to unbound.
 */
public class BoundedCounter {
    private final int maxValue;
    private int currentValue;

    private BoundedCounter(int maxValue, int currentValue) {
        this.maxValue = maxValue;
        this.currentValue = currentValue;
    }

    public static BoundedCounter create(int maxValue) {
        if (maxValue == 0) {
            return new BoundedCounter(Integer.MAX_VALUE, 0);
        }
        return new BoundedCounter(maxValue, 0);
    }

    public void inc() {
        this.inc(1);
    }

    public void inc(int count) {
        currentValue += count;
    }

    public int get() {
        return Math.min(currentValue, maxValue);
    }

    private final int getMaxValue() {
        return maxValue;
    }
}
