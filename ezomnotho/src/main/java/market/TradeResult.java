package market;

import goods.GoodId;

/**
 *  A result of a trade containing information describing the trade, so that agents may update their
 *  value of the traded goodId based on outcome.
 */

public class TradeResult {
    public final GoodId goodId;
    public final int quantityOffered;
    public final int quantityDesired;
    public final int quantityTraded;
    public final double pricePerItem;

    public TradeResult(GoodId goodId, int quantityOffered, int quantityTraded, int quantityDesired, double pricePerItem) {
        this.goodId = goodId;
        this.quantityDesired = quantityDesired;
        this.quantityOffered = quantityOffered;
        this.quantityTraded = quantityTraded;
        this.pricePerItem = pricePerItem;
    }

    @Override
    public String toString() {
        return "TradeResult{" +
            "goodId=" + goodId +
            ", quantityOffered=" + quantityOffered +
            ", quantityDesired=" + quantityDesired +
            ", quantityTraded=" + quantityTraded +
            ", pricePerItem=" + pricePerItem +
            '}';
    }
}
