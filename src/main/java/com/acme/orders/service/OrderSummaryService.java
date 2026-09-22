package com.acme.orders.service;

import com.acme.orders.api.OrderNotFoundException;
import com.acme.orders.domain.Order;
import com.acme.orders.domain.OrderItem;
import com.acme.orders.domain.OrderRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderSummaryService {

    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

    private final OrderRepository orders;

    public OrderSummaryService(OrderRepository orders) {
        this.orders = orders;
    }

    @Transactional(readOnly = true)
    public OrderSummary summarize(String orderNumber) {
        Order order = orders.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new OrderNotFoundException(orderNumber));
        return summarize(order);
    }

    OrderSummary summarize(Order order) {
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal discountTotal = BigDecimal.ZERO;
        int itemCount = 0;

        for (OrderItem item : order.getItems()) {
            subtotal = subtotal.add(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            discountTotal = discountTotal.add(item.getDiscount());
            itemCount += item.getQuantity();
        }

        BigDecimal total = subtotal.subtract(discountTotal);
        BigDecimal discountPercent = discountPercent(discountTotal, subtotal);

        return new OrderSummary(order.getOrderNumber(), itemCount, subtotal, discountTotal, total, discountPercent);
    }

    private BigDecimal discountPercent(BigDecimal discountTotal, BigDecimal subtotal) {
        return discountTotal
                .divide(subtotal, 4, RoundingMode.HALF_UP)
                .multiply(HUNDRED)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
