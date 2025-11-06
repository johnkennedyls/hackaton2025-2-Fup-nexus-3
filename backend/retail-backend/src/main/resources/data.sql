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
