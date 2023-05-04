CREATE TABLE IF NOT EXISTS categories (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(128) NOT NULL
);


CREATE TABLE IF NOT EXISTS brands (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(128) NOT NULL,
    image VARCHAR(256) NOT NULL,
    quantity BIGINT NOT NULL,
    description VARCHAR(512) NOT NULL,
    price FLOAT(8) NOT NULL,
    category_id BIGINT NOT NULL,
    brand_id BIGINT NOT NULL,
    CONSTRAINT fk_category
    FOREIGN KEY (category_id)
    REFERENCES categories (id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
    CONSTRAINT fk_brand
    FOREIGN KEY (brand_id)
    REFERENCES brands (id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
);


CREATE TABLE IF NOT EXISTS roles (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS users_roles (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT fk_user
    FOREIGN KEY (user_Id)
    REFERENCES users (id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
    CONSTRAINT fk_role
    FOREIGN KEY (role_id)
    REFERENCES roles (id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS baskets (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_user
    FOREIGN KEY (user_Id)
    REFERENCES users (id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS baskets_products (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    basket_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    CONSTRAINT fk_basket
    FOREIGN KEY (basket_id)
    REFERENCES baskets (id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
    CONSTRAINT fk_product
    FOREIGN KEY (product_id)
    REFERENCES products (id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS reviews (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    text VARCHAR(1024) NOT NULL,
    rating INT NOT NULL,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    CONSTRAINT fk_user
    FOREIGN KEY (user_Id)
    REFERENCES users (id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
    CONSTRAINT fk_product
    FOREIGN KEY (product_id)
    REFERENCES products (id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
);

INSERT INTO categories(name)
VALUES ('Computers'), ('Smartphones'), ('Tablets'), ('TVs'), ('Cameras'), ('Game Consoles'), ('Laptops'),
       ('Smartwatches'), ('Headphones'), ('Printers'), ('Speakers');

INSERT INTO brands(name)
VALUES ('Apple'),('Samsung'),('Sony'),('Bose'),('Dell'),('LG'),('Canon'),('HP'),('Alienware'),('Sonos');

INSERT INTO roles(name)
VALUES ('ROLE_GUEST'),('ROLE_USER'),('ROLE_ADMIN');

INSERT INTO products(name, image, quantity, description, price, category_id, brand_id)
VALUES  ('Apple iPhone 12', '../static/images/Iphone12.jpg', 50,
        'The iPhone 12 features a 6.1-inch Super Retina XDR display and Ceramic Shield front cover. A14 Bionic chip ' ||
        'with next-generation Neural Engine. 5G capable. Advanced dual-camera system with 12MP Ultra Wide and ' ||
        'Wide cameras. Night mode, Deep Fusion, and Smart HDR 3. iOS with redesigned widgets on the Home screen, ' ||
        'all-new App Library, App Clips, and more.', 699, 2, 1),
        ('Samsung Galaxy S21', '../static/images/GalaxyS21.jpg', 30,
        'The Samsung Galaxy S21 features a 6.2-inch Dynamic AMOLED 2X display and Gorilla Glass Victus front cover. ' ||
        'Exynos 2100 or Snapdragon 888 chipset with next-generation Neural Engine. 5G capable. Advanced ' ||
        'triple-camera system with 12MP Ultra Wide, Wide, and Telephoto cameras. Night mode, 8K video recording, ' ||
        'and Smart AI. Android 11 with One UI 3.1.', 799, 2, 2),
        ('Sony PlayStation 5', '../static/images/PS5.jpg', 10,
        'The Sony PlayStation 5 is a next-generation gaming console with a custom AMD Zen 2 processor and RDNA ' ||
        '2 graphics engine. 16GB GDDR6 RAM and 825GB SSD storage. 4K UHD Blu-ray drive. DualSense wireless ' ||
        'controller with haptic feedback and adaptive triggers. Supports 4K gaming and up to 120fps.', 499, 6, 3),
        ('Apple Watch Series 6', '../static/images/AppleWatch6.jpg', 30,
         'Smartwatch with always-on retina display, ECG and fitness tracking', 399, 8, 1),
        ('Bose QuietComfort', '../static/images/BoseQuietComfort.jpg', 20,
         'Over-ear noise cancelling headphones with Bluetooth and Alexa voice control', 349, 9, 4),
        ('Dell XPS 13', '../static/images/DellXPS13.jpg', 10, '13-inch laptop with Intel Core i7 processor and 16GB RAM'
        , 1200, 1, 5),
        ('Apple iPad Pro', '../static/images/AppleIpadPro.jpg', 15, '12.9-inch tablet with M1 chip, 256GB storage ' ||
         'and Liquid Retina display', 1099,
         3, 1),
        ('LG OLED CX', '../static/images/LGOLEDXC.jpg', 5, '65-inch OLED TV with 4K resolution and Dolby Atmos sound',
         2499, 4, 6),
        ('Canon EOS R5', '../static/images/CanonEOSR5.jpg', 8, 'Full-frame mirrorless camera with 45MP sensor and ' ||
         '8K video recording', 3899, 5, 7),
        ('HP Spectre x360', '../static/images/HPSpectrex360.jpg', 12, '2-in-1 laptop with 15.6-inch 4K touchscreen ' ||
         'and Intel Core i7 processor', 1599,7, 8),
        ('Sony WH-1000XM4', '../static/images/SonyWH-1000XM4.jpg', 25, 'Over-ear headphones with noise-cancellation' ||
         'and up to 30-hour battery', 349,9, 3),
        ('Alienware Aurora R10', '../static/images/AlienwareAuroraR10.jpg', 8, 'Gaming PC with AMD Ryzen 9 processor, ' ||
         '16GB RAM and NVIDIA GeForce RTX 3080 graphics card', 2899, 1, 9),
        ('Canon PIXMA TR150', '../static/images/CanonPIXMATR150.jpg', 20, 'Portable wireless printer with 1.44-inch ' ||
         'OLED display and AirPrint support',199, 10, 7),
        ('Sonos One', '../static/images/SonosOne.jpg', 8, 'Smart speaker with voice control and powerful sound', 199, 11, 10),
        ('MacBook Air M1', '../static/images/MacBookAirM1.jpg', 2, 'MacBook Air M1 - a laptop from Apple with an M1 ' ||
         'processor and a long battery life on a single charge', 1299, 7, 1);