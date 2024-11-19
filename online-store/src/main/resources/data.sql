INSERT INTO PRODUCT (product_code, name, product_type, description, image_url, price, is_taxable) VALUES
('KM45','RDS', 'Channel', 'Sport channel in french','https://rdsimages.cookieless.ca/polopoly_fs/1.6843299.1562016209!/img/httpImage/image.jpg',10.00, true),
('NM498','Avengers: Infinity War', 'Movie on demand', 'Super heroes movie from Marvel', 'https://terrigen-cdn-dev.marvel.com/content/prod/1x/avengersinfinitywar_lob_log_03.png',7.99, true),
('BPM5938','Breaking Bad', 'TV show', 'A  desperate chemistry teacher turns evil', null, 3.99, true),
('KM93','TSN','Channel', 'Sport channel in english','https://www.bellmedia.ca/wp-content/uploads/2014/02/tv1_tsn-490x490.png', 10.00, true),
('NM975','The Lord of the Rings: The Return of the King', 'Movie on demand', 'Classic movie base on J. R. R. Tolkien novel', null, 5.99, true),
('BPM243', 'Degrassi', 'TV show', null, null, 1.99, true),
('KM77','HBO', 'Channel', 'Movies and TV shows channel','https://asset.entpay.9c9media.ca/image-service/version/c:YTNjOWRjMTMtZDQzNi00:NDk3NDA2/image.png', 5.00, true),
('BPM45','Game of thrones', 'TV show', null, null, 4.99, true);

INSERT INTO CATALOGUE (region, start_date, end_date) VALUES
('GTA', '2019-07-01', '2019-09-03'),
('GTA', '2019-09-04', '2019-12-20'),
('Montreal area', '2019-07-01', '2019-08-29'),
('Montreal area', '2019-09-30', '2019-12-20'),
('Default', '2019-07-01', '2099-12-31'),
('Short', '2024-07-01', '2024-12-31');

INSERT INTO CATALOG_PRODUCTS(catalogue_id, product_id) VALUES
(1, 2),
(1, 3), 
(1, 5),
(1, 7),
(1, 8), 
(2, 2),
(2, 3),
(2, 4), 
(2, 5),
(2, 7),
(2, 8), 
(3, 2),
(3, 3), 
(3, 5),
(3, 7),
(3, 8), 
(4, 1),
(4, 2),
(4, 3), 
(4, 5),
(4, 7),
(4, 8),
(5, 1),
(5, 2), 
(5, 3),
(5, 4),
(5, 5), 
(5, 6),
(5, 7),
(5, 8),
(6, 7),
(6, 8);


