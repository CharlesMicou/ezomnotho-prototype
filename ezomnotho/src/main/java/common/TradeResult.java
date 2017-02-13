package common;

/**
 *  A result of a trade containing information describing the trade, so that agents may update their
 *  value of the traded good based on outcome.
 */

public class TradeResult {
    public final Good good;
    public final int quantityOffered;
    public final int quantityDesired;

    TradeResult(Good good, int quantityOffered, int quantityDesired) {
        this.good = good;
        this.quantityOffered = quantityOffered;
        this.quantityDesired = quantityDesired;
    }
}
