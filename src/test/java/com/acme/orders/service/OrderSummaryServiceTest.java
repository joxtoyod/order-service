package com.acme.orders.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.acme.orders.domain.Order;
import com.acme.orders.domain.OrderItem;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class OrderSummaryServiceTest {

    private final OrderSummaryService service = new OrderSummaryService(null, new PricingCalculator());

    @Test
    void summarizesTotalsAndDiscountPercent() throws Exception {
        Order order = order("ORD-1",
                new OrderItem("KB-001", "Keyboard", 1, new BigDecimal("100.00"), new BigDecimal("10.00")),
                new OrderItem("MS-010", "Mouse", 2, new BigDecimal("50.00"), BigDecimal.ZERO));

        OrderSummary summary = service.summarize(order);

        assertThat(summary.itemCount()).isEqualTo(3);
        assertThat(summary.subtotal()).isEqualByComparingTo("200.00");
        assertThat(summary.discountTotal()).isEqualByComparingTo("10.00");
        assertThat(summary.total()).isEqualByComparingTo("190.00");
        assertThat(summary.discountPercent()).isEqualByComparingTo("5.00");
    }

    static Order order(String number, OrderItem... items) throws Exception {
        Constructor<Order> ctor = Order.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        Order order = ctor.newInstance();
        ReflectionTestUtils.setField(order, "orderNumber", number);
        ReflectionTestUtils.setField(order, "items", List.of(items));
        return order;
    }
}
