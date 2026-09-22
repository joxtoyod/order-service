package com.acme.orders.service;

import java.math.BigDecimal;

public record OrderSummary(
        String orderNumber,
        int itemCount,
        BigDecimal subtotal,
        BigDecimal discountTotal,
        BigDecimal total,
        BigDecimal discountPercent) {
}
