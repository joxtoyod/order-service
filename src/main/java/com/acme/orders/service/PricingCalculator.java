package com.acme.orders.service;

import com.acme.orders.domain.OrderItem;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Pure pricing math for orders. Kept free of persistence concerns so it can be
 * reused by the upcoming invoice service.
 */
@Component
public class PricingCalculator {

    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
    private static final int PERCENT_SCALE = 2;

    public record Totals(int itemCount, BigDecimal subtotal, BigDecimal discountTotal) {

        public BigDecimal total() {
            return subtotal.subtract(discountTotal);
        }
    }

    public Totals totals(List<OrderItem> items) {
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal discountTotal = BigDecimal.ZERO;
        int itemCount = 0;

        for (OrderItem item : items) {
            subtotal = subtotal.add(lineSubtotal(item));
            discountTotal = discountTotal.add(item.getDiscount());
            itemCount += item.getQuantity();
        }
        return new Totals(itemCount, subtotal, discountTotal);
    }

    public BigDecimal lineSubtotal(OrderItem item) {
        return item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
    }

    public BigDecimal discountPercent(Totals totals) {
        return totals.discountTotal()
                .divide(totals.subtotal(), PERCENT_SCALE + 2, RoundingMode.HALF_UP)
                .multiply(HUNDRED)
                .setScale(PERCENT_SCALE, RoundingMode.HALF_UP);
    }
}
