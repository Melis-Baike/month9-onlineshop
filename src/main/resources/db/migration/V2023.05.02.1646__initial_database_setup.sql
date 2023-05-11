CREATE TABLE IF NOT EXISTS categories (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name TEXT NOT NULL
);


CREATE TABLE IF NOT EXISTS brands (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name TEXT NOT NULL,
    image TEXT NOT NULL,
    quantity BIGINT NOT NULL,
    description TEXT NOT NULL,
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
    name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name TEXT NOT NULL,
    email TEXT NOT NULL,
    password TEXT NOT NULL,
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

CREATE TABLE IF NOT EXISTS basket_products (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    product_name TEXT NOT NULL,
    product_image TEXT NOT NULL,
    product_quantity BIGINT NOT NULL,
    product_description TEXT NOT NULL,
    product_price FLOAT(8) NOT NULL,
    category_id BIGINT NOT NULL,
    brand_id BIGINT NOT NULL,
    basket_id BIGINT NOT NULL,
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

CREATE TABLE IF NOT EXISTS reviews (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    text TEXT NOT NULL,
    rating FLOAT(8) NOT NULL,
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
VALUES  ('Apple iPhone 12', 'Iphone12.jpg', 50,
        'The iPhone 12 features a 6.1-inch Super Retina XDR display and Ceramic Shield front cover. A14 Bionic chip ' ||
        'with next-generation Neural Engine. 5G capable. Advanced dual-camera system with 12MP Ultra Wide and ' ||
        'Wide cameras. Night mode, Deep Fusion, and Smart HDR 3. iOS with redesigned widgets on the Home screen, ' ||
        'all-new App Library, App Clips, and more.', 699, 2, 1),
        ('Samsung Galaxy S21', 'GalaxyS21.jpg', 30,
        'The Samsung Galaxy S21 features a 6.2-inch Dynamic AMOLED 2X display and Gorilla Glass Victus front cover. ' ||
        'Exynos 2100 or Snapdragon 888 chipset with next-generation Neural Engine. 5G capable. Advanced ' ||
        'triple-camera system with 12MP Ultra Wide, Wide, and Telephoto cameras. Night mode, 8K video recording, ' ||
        'and Smart AI. Android 11 with One UI 3.1.', 799, 2, 2),
        ('Sony PlayStation 5', 'PS5.jpg', 10,
        'The Sony PlayStation 5 is a next-generation gaming console with a custom AMD Zen 2 processor and RDNA ' ||
        '2 graphics engine. 16GB GDDR6 RAM and 825GB SSD storage. 4K UHD Blu-ray drive. DualSense wireless ' ||
        'controller with haptic feedback and adaptive triggers. Supports 4K gaming and up to 120fps.', 499, 6, 3),
        ('Apple Watch Series 6', 'AppleWatch6.jpg', 30, 'The Apple Watch Series 6 is a feature-packed ' ||
         'smartwatch designed for fitness enthusiasts and tech-savvy users. It boasts advanced health monitoring ' ||
         'capabilities, such as blood oxygen and ECG monitoring, as well as a range of fitness tracking features, ' ||
         'such as GPS and workout tracking. It is powered by an Apple S6 chip and runs on watchOS 7. It also offers ' ||
         'seamless integration with other Apple devices, allowing users to receive notifications, control music ' ||
         'playback, and make calls without ever taking their phone out of their pocket. The watch comes in a ' ||
         'variety of finishes and bands, making it a stylish accessory as well as a functional device.', 399, 8, 1),
        ('Bose QuietComfort', 'BoseQuietComfort.jpg', 20,
         'Bose QuietComfort is a noise-canceling headphone developed by the Bose company. They provide comfortable ' ||
         'listening experience by reducing the noise from the surrounding environment, allowing you to focus on the ' ||
         'sound. They are equipped with a built-in microphone for calls and music control, as well as a battery ' ||
         'that provides up to 20 hours of continuous playback. The headphones can be connected to a device via ' ||
         'Bluetooth or cable. They are available in several colors and have different sizes and shapes to fit ' ||
         'different head types.', 349, 9, 4),
        ('Dell XPS 13', 'DellXPS13.jpg', 10, 'Dell XPS 13 is a laptop developed by Dell that features a ' ||
         'compact design and high performance. It is equipped with a latest generation Intel Core i5 or i7 processor, ' ||
         'which ensures fast and smooth task execution. This laptop also has 8 or 16 GB of RAM and a fast solid-state ' ||
         'drive (SSD) with a capacity ranging from 256 GB to 2 TB, providing enough space for file storage and ' ||
         'quick access to them.', 1200, 7, 5),
        ('Apple iPad Pro', 'AppleIpadPro.jpg', 15, 'Apple iPad Pro is a tablet developed by Apple that ' ||
         'boasts high performance and a plethora of features. It features a Liquid Retina display with a screen ' ||
         'size of 11 or 12.9 inches and a resolution of up to 2732 x 2048 pixels, providing clear and vibrant image ' ||
         'and video display.', 1099, 3, 1),
        ('LG OLED CX', 'LGOLEDCX.jpg', 5, 'LG OLED CX is a television with OLED technology that provides ' ||
         'deep blacks and bright colors. It is equipped with the α9 Gen 3 processor, supports image quality ' ||
         'enhancement technologies and a screen dimming function. Additionally, it has Smart TV functionality.', 2499,
         4, 6),
        ('Canon EOS R5', 'CanonEOSR5.jpg', 8, 'The Canon EOS R5 is a high-end mirrorless camera with a 45MP ' ||
         'full-frame sensor, 8K video recording, and advanced autofocus system. With its weather-sealed body and ' ||
         'impressive performance, this camera is perfect for professional photographers and videographers.', 3899, 5, 7),
        ('HP Spectre x360', 'HPSpectrex360.jpg', 12, 'HP Spectre x360 is a laptop with a display that can ' ||
         'be rotated 360 degrees, allowing it to be used as a tablet or in tent mode. It is equipped with an Intel ' ||
         'Core processor, has a high screen resolution and supports touch input technology. Additionally, it has a ' ||
         'built-in fingerprint scanner, providing more secure access to the device.', 1599,7, 8),
        ('Sony WH-1000XM4', 'SonyWH-1000XM4.jpg', 25, 'Sony WH-1000XM4 are wireless headphones with active ' ||
         'noise-cancellation function. They have high sound quality, a comfortable form and lightweight design, ' ||
         'allowing for long-term use without discomfort. The headphones feature an automatic pause when taken off ' ||
         'the head and resume when put back on. They also support wireless Bluetooth connectivity and have a long ' ||
         'battery life from a single charge.', 349, 9, 3),
        ('Alienware Aurora R10', 'AlienwareAuroraR10.jpg', 8, 'Alienware Aurora R10 is a gaming desktop ' ||
         'computer equipped with an AMD Ryzen processor, NVIDIA GeForce graphics card, and high-speed RAM. It has ' ||
         'a compact chassis, but still provides powerful performance for running modern games. In addition, it ' ||
         'supports virtual reality technology, has numerous ports for connecting peripheral devices, and is ' ||
         'equipped with liquid cooling system for more stable operation.', 2899, 1, 9),
        ('Canon PIXMA TR150', 'CanonPIXMATR150.jpg', 20, 'Canon PIXMA TR150 is a portable printer for ' ||
         'printing documents and photos. It has a compact size and lightweight design, making it convenient for ' ||
         'use on the go. The printer is equipped with wireless connectivity and supports printing via mobile ' ||
         'devices and cloud services. It also has automatic duplex printing, which saves paper, and the ability ' ||
         'to print borderless photos for a more professional look.',199, 10, 7),
        ('Sonos One', 'SonosOne.jpg', 8, 'Sonos One is a wireless speaker with support for voice control ' ||
         'through Amazon Alexa or Google Assistant. It has a compact size, but provides quality sound thanks to ' ||
         'the unique design of the speaker. The speaker also supports wireless music playback from any device ' ||
         'compatible with Wi-Fi or Bluetooth, and can be used as part of a Sonos multi-room system to play music ' ||
         'in multiple rooms at the same time.', 199, 11, 10),
        ('MacBook Air M1', 'MacBookAirM1.jpg', 2, 'MacBook Air M1 is an Apple laptop with an M1 processor ' ||
         'that provides fast and efficient performance. It has a compact and lightweight body, making it convenient ' ||
         'for transportation. The laptop has a high-resolution Retina display that provides bright and clear images. ' ||
         'Additionally, it is equipped with a powerful battery that provides up to 15 hours of battery life on a ' ||
         'single charge. The laptop also supports touch input technology and has a built-in fingerprint scanner ' ||
         'for more secure access to the device.', 1299, 7, 1);
