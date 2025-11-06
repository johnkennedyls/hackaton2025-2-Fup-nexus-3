-- =============================================
-- SAMPLE DATA
-- =============================================

INSERT INTO gender (name) VALUES
('WOMEN'), ('MEN'), ('BOY'), ('GIRL'), ('UNISEX');

INSERT INTO category (name) VALUES
('COATS'), ('SHORTS'), ('SWEATSHIRTS'), ('SHIRTS'),
('SKIRTS'), ('JEANS'), ('SWIMWEAR'), ('ACCESSORIES');

INSERT INTO size (name, size_order) VALUES
('XS', 1), ('S', 2), ('M', 3), ('L', 4), ('XL', 5),
('28', 6), ('30', 7), ('32', 8), ('34', 9), ('36', 10);

INSERT INTO store (name, city, address) VALUES
('Main Store', 'New York', '123 Fashion Ave'),
('Downtown', 'Los Angeles', '456 Style Street'),
('Mall Branch', 'Chicago', '789 Trend Plaza');

