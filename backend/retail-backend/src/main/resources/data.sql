-- Datos iniciales para tiendas
INSERT INTO store (id, name, city, address) VALUES
(1, 'FUP Principal', 'Popayán', 'Carrera 9 # 12N-13, B/ Campamento'),
(2, 'Centro Comercial', 'Cali', 'Avenida Sexta # 30-01'),
(3, 'Outlet Sur', 'Bogotá', 'Calle 13 # 77-01');

-- Datos iniciales para tallas
INSERT INTO size (id, name, size_order) VALUES
(1, 'XXS', 1), (2, 'XS', 2), (3, 'S', 3), (4, 'M', 4),
(5, 'L', 5), (6, 'XL', 6), (7, '4', 7), (8, '6', 8),
(9, '8', 9), (10, '10', 10), (11, '12', 11), (12, '14', 12),
(13, '16', 13);

-- Datos iniciales para géneros
INSERT INTO gender (id, name) VALUES (1, 'WOMEN'), (2, 'MEN'), (3, 'BOY'), (4, 'GIRL'), (5, 'UNISEX');

-- Datos iniciales para categorías
INSERT INTO category (id, name) VALUES
(1, 'ABRIGO'), (2, 'BERMUDA'), (3, 'BUZO'), (4, 'CAMISAS'),
(5, 'FALDA'), (6, 'HOGAR'), (7, 'JEANS TERMINADOS'), (8, 'PANTALONES'),
(9, 'PIJAMAS'), (10, 'ROPA INTERIOR'), (11, 'TERCERAS PIEZAS'),
(12, 'TSHIRT TERMINADA'), (13, 'VESTIDOS'), (14, 'POLOS'), (15, 'ROPA DE BAÑO');

-- Datos iniciales para productos
INSERT INTO product (id, name, barcode, category_id, gender_id, sale_price, cost_price) VALUES
(101, 'Camiseta Logo', '8400000111222', 12, 5, 25.00, 10.00),
(102, 'Pantalón Vaquero', '8400000333444', 7, 5, 60.00, 25.00);
