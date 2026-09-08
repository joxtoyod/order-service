-- Development seed data. Fake customers and orders only.
-- Usage: docker compose exec -T db psql -U orders -d orders < db/seed/dev-seed.sql

TRUNCATE order_items, orders, customers RESTART IDENTITY CASCADE;

INSERT INTO customers (name, email) VALUES
    ('Ada Lovelace',   'ada@example.test'),
    ('Grace Hopper',   'grace@example.test'),
    ('Linus Pauling',  'linus@example.test');

INSERT INTO orders (order_number, customer_id, status, created_at) VALUES
    ('ORD-10001', 1, 'PAID',    '2026-10-01T09:15:00Z'),
    ('ORD-10002', 2, 'PAID',    '2026-10-02T14:30:00Z'),
    ('ORD-10003', 3, 'SHIPPED', '2026-10-03T11:05:00Z');

INSERT INTO order_items (order_id, sku, name, quantity, unit_price, discount) VALUES
    (1, 'KB-001',  'Mechanical Keyboard', 1, 129.00, 0.00),
    (1, 'MS-010',  'Wireless Mouse',      2,  39.50, 5.00),
    (2, 'MN-270',  '27" Monitor',         1, 319.99, 32.00),
    (3, 'CB-USB',  'USB-C Cable',         3,   9.99, 0.00);
