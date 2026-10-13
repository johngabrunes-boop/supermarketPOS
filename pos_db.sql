CREATE DATABASE IF NOT EXISTS pos_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;

USE pos_db;

CREATE TABLE IF NOT EXISTS products (
    id       VARCHAR(10) PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL,
    price    DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions (
    receipt_no      INT PRIMARY KEY AUTO_INCREMENT,
    cashier         VARCHAR(100) NOT NULL,
    lane            VARCHAR(50)  NOT NULL,
    datetime        DATETIME     NOT NULL,
    subtotal        DOUBLE       NOT NULL,
    discount        DOUBLE       NOT NULL,
    final_total     DOUBLE       NOT NULL,
    payment_method  VARCHAR(50)  NOT NULL,
    amount_received DOUBLE       NOT NULL,
    change_amount   DOUBLE       NOT NULL
);

CREATE TABLE IF NOT EXISTS transaction_items (
    id           INT PRIMARY KEY AUTO_INCREMENT,
    receipt_no   INT NOT NULL,
    product_id   VARCHAR(10)  NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    quantity     INT NOT NULL,
    unit_price   DOUBLE NOT NULL,
    subtotal     DOUBLE NOT NULL,
    FOREIGN KEY (receipt_no) REFERENCES transactions(receipt_no)
);