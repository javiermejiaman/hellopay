--liquibase formatted sql

--changeset javier:001 create table users
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    role VARCHAR(50) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT NOW()
);

--rollback DROP TABLE users;

--changeset javier:002 create table products
CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    price DECIMAL(13, 4) NOT NULL,
    image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT NOW()
);

--rollback DROP TABLE products;

--changeset javier:003 Add product
INSERT INTO products (name, description, price, image_url) VALUES ('Wooden chain', 'Tall wooden chair with a light color.', 50.00, 'https://images.unsplash.com/photo-1503602642458-232111445657');

/* liquibase rollback
rollback DELETE FROM products WHERE name='Wooden chain' AND description = 'Tall wooden chair with a light color.';
rollback SELECT setval('products_id_seq', COALESCE((SELECT MAX(id) FROM products), 0));
*/
