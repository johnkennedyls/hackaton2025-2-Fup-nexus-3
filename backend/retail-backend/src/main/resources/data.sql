<<<<<<< HEAD
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
=======
-- ========================================
-- DATOS DE PRUEBA PARA SISTEMA DE RECOMENDACIONES
-- ========================================

DELETE FROM sale_item;
DELETE FROM sale;
DELETE FROM stock;
DELETE FROM product;
DELETE FROM category;
DELETE FROM gender;
DELETE FROM store;
DELETE FROM size;

-- ========================================
-- 1. GÉNEROS
-- ========================================
INSERT INTO gender (id, name) VALUES
(1, 'Unisex'),
(2, 'Hombre'),
(3, 'Mujer');

-- ========================================
-- 2. CATEGORÍAS
-- ========================================
INSERT INTO category (id, name) VALUES
(1, 'TSHIRT TERMINADA'),
(2, 'PANTALON'),
(3, 'CAMISA'),
(4, 'ZAPATOS');

-- ========================================
-- 3. TALLAS
-- ========================================
INSERT INTO size (id, name, size_order) VALUES
(1, 'XS', 1),
(2, 'S', 2),
(3, 'M', 3),
(4, 'L', 4),
(5, 'XL', 5);

-- ========================================
-- 4. TIENDAS
-- ========================================
INSERT INTO store (id, name, city, address) VALUES
(1, 'Tienda Centro', 'Bogotá', 'Av. Principal 123'),
(2, 'Tienda Norte', 'Medellín', 'Zona Norte 456'),
(3, 'Tienda Sur', 'Cali', 'Zona Sur 789');

-- ========================================
-- 5. PRODUCTOS (Escenarios de Recomendación)
-- ========================================

-- Producto 101: URGENT_RESTOCK (Stock crítico: 3 unidades, ventas altas)
INSERT INTO product (id, name, barcode, category_id, gender_id, sale_price, cost_price, is_active) VALUES
(101, 'Camiseta Logo Premium', '7501234567890', 1, 1, 59.99, 25.00, TRUE);

-- Producto 102: MODERATE_RESTOCK (Stock bajo: 8 unidades, ventas activas)
INSERT INTO product (id, name, barcode, category_id, gender_id, sale_price, cost_price, is_active) VALUES
(102, 'Polo Classic Fit', '7501234567891', 1, 2, 69.99, 30.00, TRUE);

-- Producto 103: APPLY_DISCOUNT (Stock alto: 75 unidades, sin ventas)
INSERT INTO product (id, name, barcode, category_id, gender_id, sale_price, cost_price, is_active) VALUES
(103, 'Camiseta Estampada Vintage', '7501234567892', 1, 1, 49.99, 20.00, TRUE);

-- Producto 104: APPLY_PROMOTION (Stock alto: 60 unidades, ventas lentas)
INSERT INTO product (id, name, barcode, category_id, gender_id, sale_price, cost_price, is_active) VALUES
(104, 'Camisa Lino Verano', '7501234567893', 3, 2, 89.99, 40.00, TRUE);

-- Producto 105: OPTIMIZE_STOCK (Rotación muy baja: 0.08)
INSERT INTO product (id, name, barcode, category_id, gender_id, sale_price, cost_price, is_active) VALUES
(105, 'Pantalón Cargo Utility', '7501234567894', 2, 2, 99.99, 45.00, TRUE);

-- Producto 106: Sin problemas (inventario óptimo)
INSERT INTO product (id, name, barcode, category_id, gender_id, sale_price, cost_price, is_active) VALUES
(106, 'Camiseta Básica Blanca', '7501234567895', 1, 1, 39.99, 15.00, TRUE);

-- Producto 107: URGENT_RESTOCK en otra tienda
INSERT INTO product (id, name, barcode, category_id, gender_id, sale_price, cost_price, is_active) VALUES
(107, 'Zapatos Deportivos Running', '7501234567896', 4, 1, 99.99, 50.00, TRUE);

-- Producto 108: APPLY_DISCOUNT diferente categoría
INSERT INTO product (id, name, barcode, category_id, gender_id, sale_price, cost_price, is_active) VALUES
(108, 'Pantalón Formal Slim', '7501234567897', 2, 2, 79.99, 35.00, TRUE);

-- ========================================
-- 6. STOCK (Configuración de Escenarios)
-- ========================================

-- ESCENARIO 1: URGENT_RESTOCK (Producto 101 - solo 3 unidades)
INSERT INTO stock (id, product_id, store_id, size_id, quantity, min_stock) VALUES
(1, 101, 1, 3, 3, 5);

-- ESCENARIO 2: MODERATE_RESTOCK (Producto 102 - 8 unidades)
INSERT INTO stock (id, product_id, store_id, size_id, quantity, min_stock) VALUES
(2, 102, 2, 4, 8, 5);

-- ESCENARIO 3: APPLY_DISCOUNT (Producto 103 - 75 unidades sin rotación)
INSERT INTO stock (id, product_id, store_id, size_id, quantity, min_stock) VALUES
(3, 103, 1, 3, 75, 5);

-- ESCENARIO 4: APPLY_PROMOTION (Producto 104 - 60 unidades con pocas ventas)
INSERT INTO stock (id, product_id, store_id, size_id, quantity, min_stock) VALUES
(4, 104, 3, 4, 60, 5);

-- ESCENARIO 5: OPTIMIZE_STOCK (Producto 105 - 25 unidades con rotación baja)
INSERT INTO stock (id, product_id, store_id, size_id, quantity, min_stock) VALUES
(5, 105, 2, 5, 25, 5);

-- ESCENARIO 6: Stock ÓPTIMO (Producto 106 - no debe generar recomendación)
INSERT INTO stock (id, product_id, store_id, size_id, quantity, min_stock) VALUES
(6, 106, 1, 3, 20, 5);

-- ESCENARIO 7: URGENT_RESTOCK en otra tienda (Producto 107)
INSERT INTO stock (id, product_id, store_id, size_id, quantity, min_stock) VALUES
(7, 107, 2, 4, 4, 5);

-- ESCENARIO 8: APPLY_DISCOUNT diferente categoría (Producto 108)
INSERT INTO stock (id, product_id, store_id, size_id, quantity, min_stock) VALUES
(8, 108, 3, 4, 55, 5);

-- ========================================
-- 7. VENTAS (Últimos 30 días simulados)
-- ========================================

-- Ventas para URGENT_RESTOCK (Producto 101: 15 ventas en 30 días)
INSERT INTO sale (id, sale_date, total_amount, store_id) VALUES
(1, CURRENT_TIMESTAMP - INTERVAL '5' DAY, 899.85, 1);

INSERT INTO sale_item (id, sale_id, product_id, size_id, quantity, unit_price) VALUES
(1, 1, 101, 3, 15, 59.99);

-- Ventas para MODERATE_RESTOCK (Producto 102: 7 ventas en 30 días)
INSERT INTO sale (id, sale_date, total_amount, store_id) VALUES
(2, CURRENT_TIMESTAMP - INTERVAL '10' DAY, 489.93, 2);

INSERT INTO sale_item (id, sale_id, product_id, size_id, quantity, unit_price) VALUES
(2, 2, 102, 4, 7, 69.99);

-- Producto 103: SIN VENTAS (genera APPLY_DISCOUNT)

-- Ventas para APPLY_PROMOTION (Producto 104: solo 2 ventas en 30 días)
INSERT INTO sale (id, sale_date, total_amount, store_id) VALUES
(3, CURRENT_TIMESTAMP - INTERVAL '20' DAY, 179.98, 3);

INSERT INTO sale_item (id, sale_id, product_id, size_id, quantity, unit_price) VALUES
(3, 3, 104, 4, 2, 89.99);

-- Ventas para OPTIMIZE_STOCK (Producto 105: 2 ventas, rotación 0.08)
INSERT INTO sale (id, sale_date, total_amount, store_id) VALUES
(4, CURRENT_TIMESTAMP - INTERVAL '25' DAY, 199.98, 2);

INSERT INTO sale_item (id, sale_id, product_id, size_id, quantity, unit_price) VALUES
(4, 4, 105, 5, 2, 99.99);

-- Ventas NORMALES para producto sin problemas (Producto 106: 10 ventas)
INSERT INTO sale (id, sale_date, total_amount, store_id) VALUES
(5, CURRENT_TIMESTAMP - INTERVAL '15' DAY, 399.90, 1);

INSERT INTO sale_item (id, sale_id, product_id, size_id, quantity, unit_price) VALUES
(5, 5, 106, 3, 10, 39.99);

-- Ventas para URGENT_RESTOCK en otra tienda (Producto 107: 12 ventas)
INSERT INTO sale (id, sale_date, total_amount, store_id) VALUES
(6, CURRENT_TIMESTAMP - INTERVAL '7' DAY, 1199.88, 2);

INSERT INTO sale_item (id, sale_id, product_id, size_id, quantity, unit_price) VALUES
(6, 6, 107, 4, 12, 99.99);

-- Producto 108: SIN VENTAS (genera APPLY_DISCOUNT)

COMMIT;
>>>>>>> feature/003-recommendation-service
