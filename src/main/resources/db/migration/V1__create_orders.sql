CREATE TABLE customers (
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR(200) NOT NULL,
    email VARCHAR(320) NOT NULL UNIQUE
);

CREATE TABLE orders (
    id           BIGSERIAL PRIMARY KEY,
    order_number VARCHAR(32)  NOT NULL UNIQUE,
    customer_id  BIGINT       NOT NULL REFERENCES customers (id),
    status       VARCHAR(32)  NOT NULL,
    created_at   TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE TABLE order_items (
    id         BIGSERIAL PRIMARY KEY,
    order_id   BIGINT         NOT NULL REFERENCES orders (id),
    sku        VARCHAR(64)    NOT NULL,
    name       VARCHAR(200)   NOT NULL,
    quantity   INT            NOT NULL CHECK (quantity > 0),
    unit_price NUMERIC(10, 2) NOT NULL CHECK (unit_price >= 0),
    discount   NUMERIC(10, 2) NOT NULL DEFAULT 0 CHECK (discount >= 0)
);

CREATE INDEX idx_order_items_order_id ON order_items (order_id);
