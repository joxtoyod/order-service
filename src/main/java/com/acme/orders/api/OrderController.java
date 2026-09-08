package com.acme.orders.api;

import com.acme.orders.domain.OrderRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orders;

    public OrderController(OrderRepository orders) {
        this.orders = orders;
    }

    @GetMapping("/{orderNumber}")
    @Transactional(readOnly = true)
    public OrderResponse get(@PathVariable String orderNumber) {
        return orders.findByOrderNumber(orderNumber)
                .map(OrderResponse::from)
                .orElseThrow(() -> new OrderNotFoundException(orderNumber));
    }
}
