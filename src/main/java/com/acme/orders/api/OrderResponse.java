package com.acme.orders.api;

import com.acme.orders.domain.Order;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record OrderResponse(
        String orderNumber,
        String customerName,
        String status,
        String currency,
        OffsetDateTime createdAt,
        List<Item> items) {

    public record Item(String sku, String name, int quantity, BigDecimal unitPrice, BigDecimal discount) {
    }

    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getOrderNumber(),
                order.getCustomer().getName(),
                order.getStatus(),
                order.getCurrency(),
                order.getCreatedAt(),
                order.getItems().stream()
                        .map(i -> new Item(i.getSku(), i.getName(), i.getQuantity(), i.getUnitPrice(), i.getDiscount()))
                        .toList());
    }
}
