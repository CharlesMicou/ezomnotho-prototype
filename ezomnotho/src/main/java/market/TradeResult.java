package market;

/**
 *  A result of a trade containing information describing the trade, so that agents may update their
 *  value of the traded goodId based on outcome.
 */

public class TradeResult {
    public final int goodId;
    public final int quantityOffered;
    public final int quantityDesired;
    public final double pricePerItem;

    TradeResult(int goodId, int quantityOffered, int quantityDesired, double pricePerItem) {
        this.goodId = goodId;
        this.quantityOffered = quantityOffered;
        this.quantityDesired = quantityDesired;
        this.pricePerItem = pricePerItem;
    }
}
