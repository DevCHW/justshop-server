 -- Product
insert into product(product_number, name, selling_status, price, stock_quantity)
values ('001', '멋쟁이안경', 'SELLING', 5000, 100),
       ('002', '노스페이스패딩', 'SELLING', 3000, 155),
       ('003', '라코스테카라티', 'SELLING', 2500, 200);

-- Member
insert into member(login_id, password, name, email, gender, birthday, status, allow_to_marketing_notification)
values ('test123', 'qwer1234$', '최현우', 'ggoma003@naver.com', 'MAN', '1997-01-03', 'ACTIVE', 'TRUE'),
       ('test1231', 'qwer1234$', '최민우', 'ggoma001@naver.com', 'MAN', '1997-01-02', 'ACTIVE', 'TRUE'),
       ('test1232', 'qwer1234$', '김자바', 'ggoma002@naver.com', 'MAN', '1997-01-03', 'ACTIVE', 'TRUE');

