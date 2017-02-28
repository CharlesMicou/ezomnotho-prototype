package util.math.attenuation;

public interface Attenuator {

    /**
     * Supply an attenuation.
     * IMPORTANT DETAIL: as the provided attenuation is always positive, it's up to the caller to determine whether
     * to multiply or divide by the attenuation factor.
     * @param fractionalDelta: delta from the origin that we want to attenuate for (range -1 to 1 inclusive)
     * @return an attenuation value
     */
    double getAttenuation(double fractionalDelta);
}
