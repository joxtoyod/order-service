package com.acme.orders.service;

import java.io.Serializable;
import java.math.BigDecimal;

public record OrderSummary(
        String orderNumber,
        int itemCount,
        BigDecimal subtotal,
        BigDecimal discountTotal,
        BigDecimal total,
        BigDecimal discountPercent) implements Serializable {
}
