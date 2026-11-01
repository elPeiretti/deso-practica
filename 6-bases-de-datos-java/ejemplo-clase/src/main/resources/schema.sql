-- Esquema inicial para la base de datos
CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(36) PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS flights (
    id VARCHAR(36) PRIMARY KEY,
    number VARCHAR(20) NOT NULL UNIQUE,
    company VARCHAR(50) NOT NULL,
    aircraft_name VARCHAR(50),
    liters_per_km DOUBLE,
    length DOUBLE
);

CREATE TABLE IF NOT EXISTS reservations (
    id BIGINT PRIMARY KEY,
    service_id VARCHAR(36) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    reservation_date TIMESTAMP NOT NULL
);

-- Datos iniciales
MERGE INTO users (id, username, name) KEY (id) VALUES
    ('d5c88ca9-5235-4006-83a6-344775c41281', 'karen', 'Karen'),
    ('e5b88627-79da-4ca0-bc53-175bcb825710', 'fede', 'Federico');

MERGE INTO flights (id, number, company, aircraft_name, liters_per_km, length) KEY (id) VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'ABC123', 'FlyDeso', 'Boeing 747', 11.0, 1200.0);
