-- Datos iniciales (las tablas las crea Hibernate a partir de las entidades)
MERGE INTO users (id, username, name) KEY (id) VALUES
    ('d5c88ca9-5235-4006-83a6-344775c41281', 'karen', 'Karen'),
    ('e5b88627-79da-4ca0-bc53-175bcb825710', 'fede', 'Federico');

MERGE INTO tourist_services (id, company) KEY (id) VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'FlyDeso'),
    ('0b3f8a52-6c1e-4d57-9a42-5c7e1d8b9f10', 'FlyDeso'),
    ('7d2c9e41-3a85-4f16-b0c7-8e4a6f213d55', 'LatAir');

MERGE INTO flights (id, number, aircraft_name, liters_per_km, length) KEY (id) VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'ABC123', 'Boeing 747', 11.0, 1200.0),
    ('0b3f8a52-6c1e-4d57-9a42-5c7e1d8b9f10', 'XYZ789', 'Airbus A320', 2.5, 600.0),
    ('7d2c9e41-3a85-4f16-b0c7-8e4a6f213d55', 'LAT456', 'Embraer 190', 3.1, 900.0);
