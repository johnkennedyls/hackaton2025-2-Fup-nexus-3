-- =============================================
-- SAMPLE DATA
-- =============================================

INSERT INTO gender (id, name) VALUES (1, 'WOMEN');
INSERT INTO gender (id, name) VALUES (2, 'MEN');
INSERT INTO gender (id, name) VALUES (3, 'BOY');
INSERT INTO gender (id, name) VALUES (4, 'GIRL');
-- Si manejas productos neutros, podrías agregar:
-- INSERT INTO gender (id, name) VALUES (5, 'UNISEX');

ALTER SEQUENCE gender_id_seq RESTART WITH 5;

INSERT INTO category (name) VALUES
('ABRIGO'),
('BERMUDA'),
('BUZO'),
('CAMISAS'),
('FALDA'),
('HOGAR'),
('JEANS TERMINADOS'),
('PANTALONES'),
('PIJAMAS'),
('ROPA INTERIOR'),
('TERCERAS PIEZAS'),
('TSHIRT TERMINADA'),
('VESTIDOS'),
('POLOS'),
('ROPA DE BAÑO');

-- Tallas en Letras (Adultos)
INSERT INTO size (name, size_order) VALUES
('XXS', 1), ('XS', 2), ('S', 3), ('M', 4),
('L', 5), ('XL', 6);

-- Tallas Numéricas (Niños/Niñas)
INSERT INTO size (name, size_order) VALUES
('4', 7), ('6', 8), ('8', 9), ('10', 10),
('12', 11), ('14', 12), ('16', 13);

INSERT INTO store (name, city, address) VALUES
('FUP Principal', 'Popayán', 'Carrera 9 # 12N-13, B/ Campamento'),
('Centro Comercial', 'Cali', 'Avenida Sexta # 30-01'),
('Outlet Sur', 'Bogotá', 'Calle 13 # 77-01');


