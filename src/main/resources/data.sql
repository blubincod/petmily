-- 회원 DB
insert into users (username, email, password, address, age, birth_date,
                   gender, name, nickname, phone, role, status, is_walking,
                   registered_at, modified_at)
values ('kimjun91', 'kim91.jun@example.com', '$2a$10$V5HITwwdQLm673Q/gg.gk.I6.BLKuLRXis6/UyK.RA6nnhXHArsre', '서울시 강남구', 33, '1994-01-10',
        'M', '김준', '사과', '010-1111-2222', 'USER', 'ACTIVE', FALSE, '2024-08-10T14:30:15', '2024-08-10T14:30:15'),

       ('leesora96', 'lee.sora@example.com', '$2a$10$iAEySf9Bhan3CE//5uz3DudIL7YAmtkUwl9niY9IP7UP6jACYTx..', '서울시 마포구', 28, '1996-05-20',
        'F', '이소라', '바나나', '010-3333-4444', 'USER', 'ACTIVE', FALSE, '2024-08-11T09:15:30', '2024-08-11T09:15:30'),

       ('parkhyun89', 'park.hyun@example.com', '$2a$10$HvapuGDKf5S7LfHhkK.KUOeckZFIKgLzGyKc6O/RsOxZYruqnTYaa', '서울시 송파구', 35, '1989-12-05',
        'M', '박현', '체리', '010-5555-6666', 'USER', 'ACTIVE', FALSE, '2024-08-12T11:45:00', '2024-08-12T11:45:00'),

       ('choiminji02', 'choi.minji@example.com', '$2a$10$Kwx87YVXeMISffvXq7Dg1.JMppxMTpj4ytuLt3hpcOmYcqGrO9yyy', '서울시 용산구', 22, '2002-08-15',
        'F', '최민지', '오렌지', '010-7777-8888', 'USER', 'ACTIVE', FALSE, '2024-08-13T16:30:00', '2024-08-13T16:30:00'),

       ('jungeun97', 'jung.eun@example.com', '$2a$10$BFWt7Ohw3EaFMQ2j0QoBF.oPDyRC4MZQH85PcTXqiJPvCE5eTtj8u', '서울시 중구', 27, '1997-03-30',
        'F', '정은', '포도', '010-9999-0000', 'USER', 'ACTIVE', FALSE, '2024-08-14T08:00:00', '2024-08-14T08:00:00'),

       ('kimseojin00', 'kim.seojin@example.com', '$2a$10$kOSxaq2ZJH.picGMFvtdZOy9AHnTfDWFrqD5oBfs4vamLUaosvfKK', '서울시 강서구', 24, '2000-11-25',
        'F', '김서진', '키위', '010-1234-5678', 'USER', 'ACTIVE', FALSE, '2024-08-15T12:00:00', '2024-08-15T12:00:00');

-- 카테고리 DB
insert into post_category(category_id, category_name)
values (1, '공지사항');
insert into post_category(category_id, category_name)
values (2, '가입 인사');
insert into post_category(category_id, category_name)
values (3, '정보 공유');
insert into post_category(category_id, category_name)
values (4, '산책 인증');
insert into post_category(category_id, category_name)
values (5, '자유 게시판');
insert into post_category(category_id, category_name)
values (6, '리뷰 게시판');
insert into post_category(category_id, category_name)
values (7, 'QnA');
