package com.acme.orders.api;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String orderNumber) {
        super("Order not found: " + orderNumber);
    }
}
