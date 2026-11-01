-- Esquema inicial para la base de datos de usuarios
CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(36) PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100)
);

-- Datos iniciales
MERGE INTO users (id, username, name) KEY (id) VALUES
    ('d5c88ca9-5235-4006-83a6-344775c41281', 'karen', 'Karen'),
    ('e5b88627-79da-4ca0-bc53-175bcb825710', 'fede', 'Federico');
