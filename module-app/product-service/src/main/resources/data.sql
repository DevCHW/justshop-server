insert into product(id, name, price, sales_quantity, like_count, review_count, status, gender)
values (1, '에센셜 레이어드 롱 슬리브 티셔츠', 39800, 0, 0, 0, 'SELLING', 'FREE'),
       (2, '크루 넥 긴팔 티셔츠 2팩', 30590, 0, 0, 0, 'SELLING', 'MAN'),
       (3, '베이식 긴팔 티셔츠 [블랙]', 16990, 0, 10, 0, 'STOP', 'WOMEN');

insert into product_option(id, product_id, product_size, color, etc, additional_price, stock_quantity)
values (1, 1, 'S', 'BLACK', null, 2000, 100),
       (2, 1, 'S', 'WHITE', null, 2000, 143),
       (3, 1, 'M', 'BLACK', null, 0, 200),
       (4, 1, 'M', 'WHITE', null, 0, 100),
       (5, 1, 'L', 'BLACK', null, 0, 10),
       (6, 1, 'L', 'WHITE', null, 0, 2),

       (7, 2, 'S', 'RED', null, 0, 200),
       (8, 2, 'S', 'RED', null, 0, 200),
       (9, 2, 'M', 'RED', null, 0, 200),
       (10, 2, 'M', 'RED', null, 0, 200),
       (11, 2, 'L', 'RED', null, 0, 200),
       (12, 2, 'L', 'RED', null, 0, 200),

       (13, 3, 'S', 'BLACK', null, 0, 20),
       (14, 3, 'M', 'BLACK', null, 2000, 0),
       (15, 3, 'L', 'BLACK', null, 0, 0);

