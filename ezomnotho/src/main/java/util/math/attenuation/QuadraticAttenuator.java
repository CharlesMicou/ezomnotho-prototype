package util.math.attenuation;

/**
 * Attenuation scales with the square of the delta.
 */
public class QuadraticAttenuator implements Attenuator {

    /**
     * The maximum attenuation (for a full delta)
     */
    private final double maxAttenuation;

    public QuadraticAttenuator(double maxAttenuation) {
        this.maxAttenuation = maxAttenuation;
    }

    @Override
    public double getAttenuation(double fractionalDelta) {
        return fractionalDelta * fractionalDelta * maxAttenuation;
    }
}
