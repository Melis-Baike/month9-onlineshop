CREATE EXTENSION IF NOT EXISTS pgcrypto;
INSERT INTO users(name, email, password, enabled, authority_id)
VALUES ('Andrey', 'andreyka@gmail.com', crypt('andrey123', gen_salt('bf')), true, 2),
       ('Bob', 'bobik@gmail.com', crypt('bobikk2', gen_salt('bf')), true, 2),
       ('Stephen', 'curry@gmail.com', crypt('stephcur7', gen_salt('bf')), true, 2),
       ('Natlus', 'makintoshev@gmail.com', crypt('pojo222', gen_salt('bf')), true, 2),
       ('Tomas', 'jerriev@gmail.com', crypt('tomandjerry', gen_salt('bf')), true, 2);
INSERT INTO reviews (text, rating, user_id, product_id)
VALUES ('Good product!', 4.8, 1, 1),
       ('I bought it 1 year ago and it has already broken...', 2.7, 1, 14),
       ('There are some factory defects, occasionally you will have problems with this item', 3.1, 1, 23),
       ('Medium quality product, but price is not so expensive', 3.9, 2, 22),
       ('So expensive product! But it does not have competitors', 4.2, 2, 12),
       ('Just very good quality product, not a word more', 5, 2, 15),
       ('So amazing product, but price... so sad for the price', 4, 3, 8),
       ('5 out 5, Incredible!', 5, 3, 20),
       ('Takes up a lot of space, but in principle everything is fine', 4.4, 3, 22),
       ('No matter what anyone says, I really liked the product', 4.9, 3, 18),
       ('I do really like it!', 5, 4, 2),
       ('Product is expensive, but has high quality', 4.6, 4, 9),
       ('I did not like the design, but price and quality is ok', 4.5, 4, 3),
       ('Please change design of product, design is so ugly', 4.3, 5, 13),
       ('When i bought it, i thought this product was bad, but now... I have been using this since 2020 and all it is ok',
       4.7, 5, 4),
       ('Good quality product and price is not big', 4.8, 5, 25);