package com.acme.orders.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id")
    private Order order;

    private String sku;
    private String name;
    private int quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    /** Absolute discount applied to the whole line, in the order currency. */
    private BigDecimal discount;

    protected OrderItem() {
    }

    public OrderItem(String sku, String name, int quantity, BigDecimal unitPrice, BigDecimal discount) {
        this.sku = sku;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.discount = discount;
    }

    public Long getId() { return id; }
    public String getSku() { return sku; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public BigDecimal getDiscount() { return discount; }
}
