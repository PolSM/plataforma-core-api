CREATE SCHEMA IF NOT EXISTS plataforma_core;
DROP TABLE IF EXISTS plataforma_core.tbl_prices;

CREATE TABLE plataforma_core.tbl_prices (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id INTEGER NOT NULL,
    brand_id INTEGER NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    price_list INTEGER NOT NULL,
    priority TINYINT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL
);

CREATE INDEX idx_price_lookup ON plataforma_core.tbl_prices (product_id, brand_id, start_date, end_date);