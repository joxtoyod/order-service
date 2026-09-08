package com.acme.orders;

import static org.assertj.core.api.Assertions.assertThat;

import com.acme.orders.domain.OrderItem;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class OrderResponseTest {

    @Test
    void orderItemKeepsPriceAndDiscount() {
        OrderItem item = new OrderItem("KB-001", "Keyboard", 1, new BigDecimal("129.00"), BigDecimal.ZERO);

        assertThat(item.getUnitPrice()).isEqualByComparingTo("129.00");
        assertThat(item.getDiscount()).isEqualByComparingTo("0");
    }
}
