-- =============================================
-- CORE TABLES FOR SPRING BOOT MVC (H2 COMPATIBLE)
-- =============================================

-- Master tables
CREATE TABLE gender (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE size (
    id SERIAL PRIMARY KEY,
    name VARCHAR(10) NOT NULL UNIQUE,
    size_order INTEGER NOT NULL
);

CREATE TABLE store (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    address TEXT
);

-- Main entity tables
CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    barcode VARCHAR(50) UNIQUE,
    description TEXT,
    category_id INTEGER REFERENCES category(id),
    gender_id INTEGER REFERENCES gender(id),
    sale_price DECIMAL(10,2) NOT NULL CHECK (sale_price > 0),
    cost_price DECIMAL(10,2) NOT NULL CHECK (cost_price >= 0),
    is_active BOOLEAN DEFAULT TRUE,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sale (
    id SERIAL PRIMARY KEY,
    store_id INTEGER NOT NULL REFERENCES store(id),
    sale_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(12,2) NOT NULL CHECK (total_amount >= 0),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sale_item (
    id SERIAL PRIMARY KEY,
    sale_id INTEGER NOT NULL REFERENCES sale(id) ON DELETE CASCADE,
    product_id INTEGER NOT NULL REFERENCES product(id),
    size_id INTEGER NOT NULL REFERENCES size(id),
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    unit_price DECIMAL(10,2) NOT NULL CHECK (unit_price > 0),
    subtotal DECIMAL(12,2) GENERATED ALWAYS AS (quantity * unit_price)
);

CREATE TABLE stock (
    id SERIAL PRIMARY KEY,
    product_id INTEGER NOT NULL REFERENCES product(id),
    size_id INTEGER NOT NULL REFERENCES size(id),
    store_id INTEGER NOT NULL REFERENCES store(id),
    quantity INTEGER NOT NULL DEFAULT 0 CHECK (quantity >= 0),
    min_stock INTEGER NOT NULL DEFAULT 5,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(product_id, size_id, store_id)
);

-- =============================================
-- BASIC INDEXES FOR PERFORMANCE
-- =============================================

CREATE INDEX idx_product_name ON product(name);
CREATE INDEX idx_product_barcode ON product(barcode);
CREATE INDEX idx_product_category ON product(category_id);
CREATE INDEX idx_product_gender ON product(gender_id);
CREATE INDEX idx_product_active ON product(is_active);

CREATE INDEX idx_sale_store ON sale(store_id);
CREATE INDEX idx_sale_date ON sale(sale_date);
CREATE INDEX idx_sale_store_date ON sale(store_id, sale_date);

CREATE INDEX idx_sale_item_sale ON sale_item(sale_id);
CREATE INDEX idx_sale_item_product ON sale_item(product_id);

CREATE INDEX idx_stock_product ON stock(product_id);
CREATE INDEX idx_stock_store ON stock(store_id);
CREATE INDEX idx_stock_product_store ON stock(product_id, store_id);

COMMIT;
