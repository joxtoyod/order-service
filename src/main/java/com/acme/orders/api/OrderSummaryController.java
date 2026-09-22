package com.acme.orders.api;

import com.acme.orders.service.OrderSummary;
import com.acme.orders.service.OrderSummaryService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@ConditionalOnProperty(name = "features.order-summary", havingValue = "true")
public class OrderSummaryController {

    private final OrderSummaryService summaries;

    public OrderSummaryController(OrderSummaryService summaries) {
        this.summaries = summaries;
    }

    @GetMapping("/{orderNumber}/summary")
    public OrderSummary summary(@PathVariable String orderNumber) {
        return summaries.summarize(orderNumber);
    }
}
