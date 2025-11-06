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

-- Productos variados con diferentes categorías
INSERT INTO product (id, name, barcode, category_id, gender_id, sale_price, cost_price) VALUES
(101, 'Camiseta Logo Premium', '8400000111222', 12, 5, 35000.00, 15000.00),
(102, 'Pantalón Vaquero Clásico', '8400000333444', 7, 5, 85000.00, 40000.00),
(103, 'Vestido Floral', '8400000555666', 13, 1, 120000.00, 55000.00),
(104, 'Camisa Oxford', '8400000777888', 4, 2, 65000.00, 30000.00),
(105, 'Polo Manga Corta', '8400000999000', 14, 2, 45000.00, 20000.00),
(106, 'Falda Plisada', '8400001112223', 5, 1, 55000.00, 25000.00),
(107, 'Buzo Deportivo', '8400001114445', 3, 5, 75000.00, 35000.00),
(108, 'Bermuda Cargo', '8400001116667', 2, 3, 40000.00, 18000.00),
(109, 'Pijama Algodón', '8400001118889', 9, 5, 50000.00, 22000.00),
(110, 'Abrigo Invierno', '8400001120001', 1, 5, 150000.00, 70000.00);

-- Stock inicial en las tiendas
INSERT INTO stock (id, store_id, product_id, size_id, quantity) VALUES
-- Tienda 1 (FUP Principal)
(1, 1, 101, 3, 50), (2, 1, 101, 4, 75), (3, 1, 101, 5, 60),
(4, 1, 102, 4, 40), (5, 1, 102, 5, 45), (6, 1, 102, 6, 30),
(7, 1, 103, 3, 25), (8, 1, 103, 4, 35), (9, 1, 103, 5, 20),
(10, 1, 104, 4, 30), (11, 1, 104, 5, 40), (12, 1, 104, 6, 25),
(13, 1, 105, 3, 45), (14, 1, 105, 4, 55), (15, 1, 105, 5, 40),
-- Tienda 2 (Centro Comercial)
(16, 2, 101, 3, 60), (17, 2, 101, 4, 80), (18, 2, 101, 5, 70),
(19, 2, 106, 3, 30), (20, 2, 106, 4, 40), (21, 2, 106, 5, 25),
(22, 2, 107, 4, 50), (23, 2, 107, 5, 60), (24, 2, 107, 6, 45),
(25, 2, 108, 7, 35), (26, 2, 108, 8, 40), (27, 2, 108, 9, 30),
-- Tienda 3 (Outlet Sur)
(28, 3, 109, 3, 40), (29, 3, 109, 4, 50), (30, 3, 109, 5, 35),
(31, 3, 110, 4, 20), (32, 3, 110, 5, 25), (33, 3, 110, 6, 15);

-- Ventas del año 2025 (Enero - Diciembre)
-- Ventas de Enero
INSERT INTO sale (id, store_id, sale_date, total_amount) VALUES
(1, 1, '2025-01-05 10:30:00', 105000.00),
(2, 1, '2025-01-10 14:20:00', 170000.00),
(3, 2, '2025-01-15 11:45:00', 200000.00),
(4, 3, '2025-01-20 16:00:00', 150000.00);

INSERT INTO sale_item (id, sale_id, product_id, size_id, quantity, unit_price) VALUES
(1, 1, 101, 4, 3, 35000.00),
(2, 2, 102, 5, 2, 85000.00),
(3, 3, 103, 4, 1, 120000.00), (4, 3, 105, 4, 1, 45000.00), (5, 3, 101, 3, 1, 35000.00),
(6, 4, 109, 4, 3, 50000.00);

-- Ventas de Febrero
INSERT INTO sale (id, store_id, sale_date, total_amount) VALUES
(5, 1, '2025-02-03 09:15:00', 140000.00),
(6, 2, '2025-02-08 13:30:00', 220000.00),
(7, 3, '2025-02-14 15:45:00', 300000.00),
(8, 1, '2025-02-20 10:00:00', 90000.00);

INSERT INTO sale_item (id, sale_id, product_id, size_id, quantity, unit_price) VALUES
(7, 5, 104, 5, 2, 65000.00), (8, 5, 101, 4, 1, 35000.00),
(9, 6, 107, 5, 2, 75000.00), (10, 6, 106, 4, 1, 55000.00),
(11, 7, 110, 5, 2, 150000.00),
(12, 8, 105, 4, 2, 45000.00);

-- Ventas de Marzo
INSERT INTO sale (id, store_id, sale_date, total_amount) VALUES
(9, 1, '2025-03-05 11:20:00', 255000.00),
(10, 2, '2025-03-12 14:10:00', 180000.00),
(11, 3, '2025-03-18 16:30:00', 120000.00),
(12, 1, '2025-03-25 09:45:00', 150000.00);

INSERT INTO sale_item (id, sale_id, product_id, size_id, quantity, unit_price) VALUES
(13, 9, 102, 5, 3, 85000.00),
(14, 10, 103, 4, 1, 120000.00), (15, 10, 104, 5, 1, 65000.00),
(16, 11, 108, 8, 3, 40000.00),
(17, 12, 110, 5, 1, 150000.00);

-- Ventas de Abril a Diciembre (datos agregados para mayor volumen)
INSERT INTO sale (id, store_id, sale_date, total_amount) VALUES
(13, 1, '2025-04-10 10:00:00', 280000.00),
(14, 2, '2025-05-15 12:30:00', 320000.00),
(15, 3, '2025-06-20 14:45:00', 275000.00),
(16, 1, '2025-07-08 11:15:00', 190000.00),
(17, 2, '2025-08-12 13:20:00', 240000.00),
(18, 3, '2025-09-18 15:30:00', 210000.00),
(19, 1, '2025-10-22 10:45:00', 300000.00),
(20, 2, '2025-11-14 12:00:00', 260000.00),
(21, 3, '2025-12-05 14:15:00', 350000.00);

INSERT INTO sale_item (id, sale_id, product_id, size_id, quantity, unit_price) VALUES
(18, 13, 101, 4, 8, 35000.00),
(19, 14, 107, 5, 4, 75000.00), (20, 14, 105, 4, 1, 45000.00),
(21, 15, 103, 4, 2, 120000.00), (22, 15, 101, 3, 1, 35000.00),
(23, 16, 104, 5, 2, 65000.00), (24, 16, 106, 4, 1, 55000.00),
(25, 17, 102, 5, 2, 85000.00), (26, 17, 105, 4, 2, 45000.00),
(27, 18, 109, 4, 4, 50000.00), (28, 18, 101, 4, 1, 35000.00),
(29, 19, 110, 5, 2, 150000.00),
(30, 20, 107, 5, 3, 75000.00), (31, 20, 101, 4, 1, 35000.00),
(32, 21, 103, 4, 2, 120000.00), (33, 21, 106, 4, 2, 55000.00);
