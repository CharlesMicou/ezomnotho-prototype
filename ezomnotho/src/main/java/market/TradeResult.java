package market;

/**
 *  A result of a trade containing information describing the trade, so that agents may update their
 *  value of the traded goodId based on outcome.
 */

public class TradeResult {
    public final int goodId;
    public final int quantityOffered;
    public final int quantityTraded;
    public final double pricePerItem;

    public TradeResult(int goodId, int quantityOffered, int quantityTraded, double pricePerItem) {
        this.goodId = goodId;
        this.quantityOffered = quantityOffered;
        this.quantityTraded = quantityTraded;
        this.pricePerItem = pricePerItem;
    }
}
