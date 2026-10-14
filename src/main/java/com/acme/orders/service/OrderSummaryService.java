package com.acme.orders.service;

import com.acme.orders.api.OrderNotFoundException;
import com.acme.orders.domain.Order;
import com.acme.orders.domain.OrderRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderSummaryService {

    private final OrderRepository orders;
    private final PricingCalculator pricing;

    public OrderSummaryService(OrderRepository orders, PricingCalculator pricing) {
        this.orders = orders;
        this.pricing = pricing;
    }

    @Cacheable(cacheNames = "order-summaries", key = "#orderNumber")
    @Transactional(readOnly = true)
    public OrderSummary summarize(String orderNumber) {
        return orders.findByOrderNumber(orderNumber)
                .map(this::summarize)
                .orElseThrow(() -> new OrderNotFoundException(orderNumber));
    }

    OrderSummary summarize(Order order) {
        PricingCalculator.Totals totals = pricing.totals(order.getItems());
        return new OrderSummary(
                order.getOrderNumber(),
                totals.itemCount(),
                totals.subtotal(),
                totals.discountTotal(),
                totals.total(),
                pricing.discountPercent(totals));
    }
}
