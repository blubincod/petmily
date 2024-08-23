-- 회원 DB
insert into users (username, email, password, address, age, birth_date,
                   gender, name, nickname, phone, role, user_status, is_walking,
                   registered_at, modified_at)
values
    -- USER_ID 1
    ('kimjun91', 'kim91.jun@example.com',
     '$2a$10$V5HITwwdQLm673Q/gg.gk.I6.BLKuLRXis6/UyK.RA6nnhXHArsre', '서울시 강남구',
     33, '1994-01-10',
     'M', '김준', '사과', '010-1111-2222', 'USER', 'ACTIVE', FALSE,
     '2024-08-10T14:30:15', '2024-08-10T14:30:15'),
-- USER_ID 2
    ('leesora96', 'lee.sora@example.com',
     '$2a$10$iAEySf9Bhan3CE//5uz3DudIL7YAmtkUwl9niY9IP7UP6jACYTx..', '서울시 마포구',
     28, '1996-05-20',
     'F', '이소라', '바나나', '010-3333-4444', 'USER', 'ACTIVE', FALSE,
     '2024-08-11T09:15:30', '2024-08-11T09:15:30'),
-- USER_ID 3
    ('parkhyun89', 'park.hyun@example.com',
     '$2a$10$HvapuGDKf5S7LfHhkK.KUOeckZFIKgLzGyKc6O/RsOxZYruqnTYaa', '서울시 송파구',
     35, '1989-12-05',
     'M', '박현', '체리', '010-5555-6666', 'USER', 'ACTIVE', FALSE,
     '2024-08-12T11:45:00', '2024-08-12T11:45:00'),
-- USER_ID 4
    ('choiminji02', 'choi.minji@example.com',
     '$2a$10$Kwx87YVXeMISffvXq7Dg1.JMppxMTpj4ytuLt3hpcOmYcqGrO9yyy', '서울시 용산구',
     22, '2002-08-15',
     'F', '최민지', '오렌지', '010-7777-8888', 'USER', 'ACTIVE', FALSE,
     '2024-08-13T16:30:00', '2024-08-13T16:30:00'),
-- USER_ID 5
    ('jungeun97', 'jung.eun@example.com',
     '$2a$10$BFWt7Ohw3EaFMQ2j0QoBF.oPDyRC4MZQH85PcTXqiJPvCE5eTtj8u', '서울시 중구',
     27, '1997-03-30',
     'F', '정은', '포도', '010-9999-0000', 'USER', 'ACTIVE', FALSE,
     '2024-08-14T08:00:00', '2024-08-14T08:00:00');

-- 반려동물 DB
INSERT INTO pet (USER_ID, CATEGORY, BRAND, BIRTH_DATE, AGE, NAME, GENDER,
                 IS_PETS_NEUTER, WEIGHT, PET_STATUS, CHIP, CREATE_AT,
                 MODIFIED_AT)
VALUES
    -- USER_ID 1의 반려동물
    (1, 'DOG', 'Labrador Retriever', '2020-05-15', 3, 'Max', 'MALE', true, 30.5,
     'ACTIVE', 'CHIP123456', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 'CAT', 'Persian', '2021-03-10', 2, 'Luna', 'FEMALE', true, 4.2,
     'ACTIVE', 'CHIP234567', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 'DOG', 'Golden Retriever', '2019-11-20', 4, 'Charlie', 'MALE', false,
     28.7, 'ACTIVE', 'CHIP345678', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- USER_ID 2의 반려동물
    (2, 'CAT', 'Siamese', '2022-01-05', 1, 'Milo', 'MALE', false, 3.8, 'ACTIVE',
     'CHIP456789', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'DOG', 'Poodle', '2020-09-30', 3, 'Bella', 'FEMALE', true, 15.3,
     'ACTIVE', 'CHIP567890', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'CAT', 'Maine Coon', '2021-07-12', 2, 'Oliver', 'MALE', true, 5.5,
     'ACTIVE', 'CHIP678901', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- USER_ID 3의 반려동물
    (3, 'DOG', 'German Shepherd', '2019-04-22', 4, 'Rocky', 'MALE', true, 35.2,
     'ACTIVE', 'CHIP789012', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 'CAT', 'British Shorthair', '2020-12-03', 2, 'Lucy', 'FEMALE', false,
     4.0, 'ACTIVE', 'CHIP890123', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 'DOG', 'Beagle', '2021-08-17', 2, 'Daisy', 'FEMALE', true, 12.8,
     'ACTIVE', 'CHIP901234', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- USER_ID 4의 반려동물
    (4, 'DOG', 'Shiba Inu', '2021-02-14', 2, 'Hachi', 'MALE', false, 10.5,
     'ACTIVE', 'CHIP012345', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- USER_ID 5의 반려동물
    (5, 'DOG', 'Corgi', '2020-07-30', 3, 'Coco', 'FEMALE', true, 12.3, 'ACTIVE',
     'CHIP123450', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 산책 DB
INSERT INTO walk (user_id, walk_status, distance, duration, start_time,
                  end_time, walk_date)
VALUES
    -- User 1
    (1, 1, 3.0, 30.0, '2024-08-10 01:30:00', '2024-08-10 02:00:00',
     '2024-08-10'),
    (1, 1, 2.5, 23.0, '2024-08-11 01:22:00', '2024-08-11 01:45:00',
     '2024-08-11'),
    (1, 1, 4.0, 40.0, '2024-08-12 02:00:00', '2024-08-12 02:40:00',
     '2024-08-12'),

    -- User 2
    (2, 1, 3.5, 35.0, '2024-08-10 03:00:00', '2024-08-10 03:35:00',
     '2024-08-10'),
    (2, 1, 2.0, 20.0, '2024-08-11 03:30:00', '2024-08-11 03:50:00',
     '2024-08-11'),
    (2, 1, 5.0, 50.0, '2024-08-12 04:00:00', '2024-08-12 04:50:00',
     '2024-08-12'),
    (2, 1, 4.5, 45.0, '2024-08-13 04:30:00', '2024-08-13 05:15:00',
     '2024-08-13'),

    -- User 3
    (3, 1, 3.2, 32.0, '2024-08-10 05:00:00', '2024-08-10 05:32:00',
     '2024-08-10'),
    (3, 1, 2.8, 28.0, '2024-08-11 05:30:00', '2024-08-11 05:58:00',
     '2024-08-11'),
    (3, 1, 4.1, 41.0, '2024-08-12 06:00:00', '2024-08-12 06:41:00',
     '2024-08-12'),

    -- User 4
    (4, 1, 3.3, 33.0, '2024-08-10 06:30:00', '2024-08-10 07:03:00',
     '2024-08-10'),
    (4, 1, 2.7, 27.0, '2024-08-11 07:00:00', '2024-08-11 07:27:00',
     '2024-08-11'),
    (4, 1, 4.2, 42.0, '2024-08-12 07:30:00', '2024-08-12 08:12:00',
     '2024-08-12'),
    (4, 1, 5.1, 51.0, '2024-08-13 08:00:00', '2024-08-13 08:51:00',
     '2024-08-13'),
    (4, 1, 3.8, 38.0, '2024-08-14 08:30:00', '2024-08-14 09:08:00',
     '2024-08-14'),

    -- User 5
    (5, 1, 3.4, 34.0, '2024-08-10 09:00:00', '2024-08-10 09:34:00',
     '2024-08-10'),
    (5, 1, 2.6, 26.0, '2024-08-11 09:30:00', '2024-08-11 09:56:00',
     '2024-08-11'),
    (5, 1, 4.3, 43.0, '2024-08-12 10:00:00', '2024-08-12 10:43:00',
     '2024-08-12');

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
