-- 회원 데이터
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

-- 반려동물 데이터
INSERT INTO pet (USER_ID, TYPE, BREED, BIRTH_DATE, AGE, NAME, GENDER,
                 IS_NEUTERED, WEIGHT, PET_STATUS, CHIP_NUMBER, CREATED_AT,
                 UPDATED_AT)
VALUES
-- USER_ID 1의 반려동물
(1, 'DOG', '래브라도 리트리버', '2020-05-15', 3, '맥스', 'MALE', true, 30.5,
 'ACTIVE', 'CHIP123456', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'CAT', '페르시안', '2021-03-10', 2, '루나', 'FEMALE', true, 4.2,
 'ACTIVE', 'CHIP234567', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'DOG', '골든 리트리버', '2019-11-20', 4, '찰리', 'MALE', false,
 28.7, 'ACTIVE', 'CHIP345678', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- USER_ID 2의 반려동물
(2, 'CAT', '샴', '2022-01-05', 1, '밀로', 'MALE', false, 3.8, 'ACTIVE',
 'CHIP456789', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'DOG', '푸들', '2020-09-30', 3, '벨라', 'FEMALE', true, 15.3,
 'ACTIVE', 'CHIP567890', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'CAT', '메인쿤', '2021-07-12', 2, '올리버', 'MALE', true, 5.5,
 'ACTIVE', 'CHIP678901', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- USER_ID 3의 반려동물
(3, 'DOG', '저먼 셰퍼드', '2019-04-22', 4, '로키', 'MALE', true, 35.2,
 'ACTIVE', 'CHIP789012', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'CAT', '브리티시 숏헤어', '2020-12-03', 2, '루시', 'FEMALE', false,
 4.0, 'ACTIVE', 'CHIP890123', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'DOG', '비글', '2021-08-17', 2, '데이지', 'FEMALE', true, 12.8,
 'ACTIVE', 'CHIP901234', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- USER_ID 4의 반려동물
(4, 'DOG', '시바 이누', '2021-02-14', 2, '하치', 'MALE', false, 10.5,
 'ACTIVE', 'CHIP012345', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- USER_ID 5의 반려동물
(5, 'DOG', '웰시 코기', '2020-07-30', 3, '코코', 'FEMALE', true, 12.3, 'ACTIVE',
 'CHIP123450', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 산책 데이터
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

-- 산책 참여 반려동물 데이터
INSERT INTO WALK_PARTICIPANT (PET_ID, WALK_ID)
VALUES (1, 1),
       (2, 1),
       (1, 2),
       (3, 2),
       (2, 3),
       (3, 3),
       (4, 4),
       (5, 4),
       (5, 5),
       (6, 5),
       (4, 6),
       (6, 6),
       (5, 7),
       (6, 7),
       (7, 8),
       (8, 8),
       (8, 9),
       (9, 9),
       (7, 10),
       (9, 10),
       (10, 11),
       (10, 12),
       (10, 13),
       (10, 14),
       (10, 15),
       (11, 16),
       (11, 17),
       (11, 18);

-- 산책에 대한 산책 활동 데이터
INSERT INTO WALK_ACTIVITY (LATITUDE, LONGITUDE, PET_ID, WALK_ID, TIMESTAMP,
                           ACTIVITY_TYPE)
VALUES (37.5665, 126.9780, 1, 1, '2024-08-10 01:45:00', 'PEE'),
       (37.5666, 126.9781, 2, 1, '2024-08-10 01:55:00', 'POOP'),
       (37.5667, 126.9782, 1, 2, '2024-08-11 01:30:00', 'WATER'),
       (37.5668, 126.9783, 3, 2, '2024-08-11 01:40:00', 'PEE'),
       (37.5669, 126.9784, 2, 3, '2024-08-12 02:20:00', 'POOP'),
       (37.5670, 126.9785, 3, 3, '2024-08-12 02:30:00', 'WATER'),
       (37.5671, 126.9786, 4, 4, '2024-08-10 03:15:00', 'PEE'),
       (37.5672, 126.9787, 5, 4, '2024-08-10 03:25:00', 'POOP'),
       (37.5673, 126.9788, 5, 5, '2024-08-11 03:40:00', 'WATER'),
       (37.5674, 126.9789, 6, 5, '2024-08-11 03:50:00', 'PEE'),
       (37.5675, 126.9790, 4, 6, '2024-08-12 04:25:00', 'POOP'),
       (37.5676, 126.9791, 6, 6, '2024-08-12 04:35:00', 'WATER'),
       (37.5677, 126.9792, 5, 7, '2024-08-13 04:45:00', 'PEE'),
       (37.5678, 126.9793, 6, 7, '2024-08-13 04:55:00', 'POOP'),
       (37.5679, 126.9794, 7, 8, '2024-08-10 05:15:00', 'WATER'),
       (37.5680, 126.9795, 8, 8, '2024-08-10 05:25:00', 'PEE'),
       (37.5681, 126.9796, 8, 9, '2024-08-11 05:45:00', 'POOP'),
       (37.5682, 126.9797, 9, 9, '2024-08-11 05:55:00', 'WATER'),
       (37.5683, 126.9798, 7, 10, '2024-08-12 06:20:00', 'PEE'),
       (37.5684, 126.9799, 9, 10, '2024-08-12 06:30:00', 'POOP'),
       (37.5685, 126.9800, 10, 11, '2024-08-10 06:45:00', 'WATER'),
       (37.5686, 126.9801, 10, 12, '2024-08-11 07:15:00', 'PEE'),
       (37.5687, 126.9802, 10, 13, '2024-08-12 07:45:00', 'POOP'),
       (37.5688, 126.9803, 10, 14, '2024-08-13 08:25:00', 'WATER'),
       (37.5689, 126.9804, 10, 15, '2024-08-14 08:55:00', 'PEE'),
       (37.5690, 126.9805, 11, 16, '2024-08-10 09:15:00', 'POOP'),
       (37.5691, 126.9806, 11, 17, '2024-08-11 09:45:00', 'WATER'),
       (37.5692, 126.9807, 11, 18, '2024-08-12 10:20:00', 'PEE');

-- 게시판 카테고리 데이터
insert into post_category(category_id, category_name)
values (1, '공지사항'),
       (2, '가입 인사'),
       (3, '정보 공유'),
       (4, '산책 인증'),
       (5, '자유 게시판'),
       (6, '리뷰 게시판'),
       (7, 'QnA');

-- 오픈 채팅 카테고리 데이터
INSERT INTO open_chat_category (name, description)
VALUES ('일상대화', '일상적인 주제에 대해 자유롭게 대화하는 공간입니다.'),
       ('반려동물', '반려동물에 관한 정보를 공유하고 소통하는 공간입니다.'),
       ('산책메이트', '함께 산책할 친구를 찾고 만나는 공간입니다.'),
       ('펫용품리뷰', '다양한 펫용품에 대한 리뷰와 정보를 공유하는 공간입니다.'),
       ('동물병원정보', '동물병원 정보와 경험을 공유하는 공간입니다.');

-- 오픈 채팅방 리스트 데이터
INSERT INTO open_chat (CATEGORY_ID, TITLE, DESCRIPTION, PASSWORD,
                       MAX_PARTICIPANTS, CURRENT_PARTICIPANTS, IS_PUBLIC,
                       CREATOR_ID, CREATED_AT, UPDATED_AT, LAST_MESSAGE_AT,
                       STATUS, IMAGE_URL)
VALUES (3, 'OO동 저녁 8시 산책 모임', '퇴근 후 함께 산책하며 스트레스 풀어요!', NULL, 5, 2, true, 1,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE',
        NULL),
       (2, '고양이 집사 모여라!', '고양이 키우는 분들의 일상 공유 모임', NULL, 10, 3, true, 2,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE',
        NULL),
       (1, '20대 직장인 수다방', '일상 고민부터 취미 공유까지 자유로운 대화', NULL, 15, 4, true, 3,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE',
        NULL),
       (4, '강아지 장난감 추천', '우리 강아지가 좋아하는 장난감 공유해요', NULL, 8, 1, true, 4,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE',
        NULL),
       (5, 'OO동 동물병원 정보 공유', '믿을 만한 동물병원 정보 나눠요', NULL, 20, 5, true, 5,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE',
        NULL),
       (3, 'OO동 주말 아침 공원 산책', '주말 아침 상쾌한 공기 마시며 산책해요', NULL, 6, 2, true, 1,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE',
        NULL),
       (2, '반려동물 훈련 팁 공유', '반려동물 훈련 노하우 나누는 공간', NULL, 12, 3, true, 2,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE',
        NULL),
       (1, '펫밀리 독서모임', '반려동물 관련 책 읽고 토론해요', NULL, 8, 2, true, 3,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE',
        NULL),
       (4, '수제 간식 레시피 공유', '건강한 수제 간식 만드는 법 공유해요', NULL, 10, 1, true, 4,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE',
        NULL),
       (5, '응급처치 정보 공유', '반려동물 응급상황 대처법 공유해요', NULL, 15, 3, true, 5,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE',
        NULL);

-- 오픈 채팅방 참여자 데이터
INSERT INTO open_chat_participant (user_id, open_chat_id, status, joined_at,
                                   left_at)
VALUES
-- 채팅방 1
(1, 1, 'ACTIVE', '2024-08-15 18:30:00', NULL),
(2, 1, 'ACTIVE', '2024-08-15 19:00:00', NULL),

-- 채팅방 2
(2, 2, 'ACTIVE', '2024-08-16 10:00:00', NULL),
(3, 2, 'ACTIVE', '2024-08-16 10:30:00', NULL),
(4, 2, 'ACTIVE', '2024-08-16 11:00:00', NULL),

-- 채팅방 3
(1, 3, 'ACTIVE', '2024-08-17 20:00:00', NULL),
(2, 3, 'ACTIVE', '2024-08-17 20:15:00', NULL),
(4, 3, 'ACTIVE', '2024-08-17 20:30:00', NULL),
(5, 3, 'ACTIVE', '2024-08-17 21:00:00', NULL),

-- 채팅방 4
(3, 4, 'ACTIVE', '2024-08-18 14:00:00', NULL),

-- 채팅방 5
(1, 5, 'ACTIVE', '2024-08-19 09:00:00', NULL),
(2, 5, 'ACTIVE', '2024-08-19 09:30:00', NULL),
(3, 5, 'ACTIVE', '2024-08-19 10:00:00', NULL),
(4, 5, 'ACTIVE', '2024-08-19 10:30:00', NULL),
(5, 5, 'ACTIVE', '2024-08-19 11:00:00', NULL),

-- 채팅방 6
(4, 6, 'ACTIVE', '2024-08-20 07:00:00', NULL),
(5, 6, 'ACTIVE', '2024-08-20 07:15:00', NULL),

-- 채팅방 7
(1, 7, 'ACTIVE', '2024-08-21 15:00:00', NULL),
(3, 7, 'ACTIVE', '2024-08-21 15:30:00', NULL),
(5, 7, 'ACTIVE', '2024-08-21 16:00:00', NULL),

-- 채팅방 8
(2, 8, 'ACTIVE', '2024-08-22 19:00:00', NULL),
(4, 8, 'ACTIVE', '2024-08-22 19:30:00', NULL),

-- 채팅방 9
(3, 9, 'ACTIVE', '2024-08-23 11:00:00', NULL),

-- 채팅방 10
(1, 10, 'ACTIVE', '2024-08-24 13:00:00', NULL),
(2, 10, 'ACTIVE', '2024-08-24 13:30:00', NULL),
(5, 10, 'ACTIVE', '2024-08-24 14:00:00', NULL);

-- 질병 데이터
INSERT INTO disease (name, description, vaccination_deadline, pet_type)
VALUES
-- 개 질병
('광견병', '치명적인 바이러스성 질병으로, 모든 포유류에 감염될 수 있습니다.', 365, 'DOG'),
('파보바이러스', '심각한 위장 질환을 일으키는 바이러스성 질병입니다.', 365, 'DOG'),
('디스템퍼', '호흡기, 소화기, 신경계에 영향을 미치는 바이러스성 질병입니다.', 365, 'DOG'),
('켄넬코프', '전염성이 강한 호흡기 질환입니다.', 180, 'DOG'),
('레프토스피라증', '박테리아로 인한 질병으로 간과 신장에 영향을 줍니다.', 365, 'DOG'),

-- 고양이 질병
('고양이 범백혈구감소증', '매우 전염성이 강하고 치명적인 바이러스성 질병입니다.', 365, 'CAT'),
('고양이 백혈병', '면역 체계를 약화시키는 바이러스성 질병입니다.', 365, 'CAT'),
('고양이 칼리시바이러스', '상부 호흡기 감염을 일으키는 바이러스성 질병입니다.', 365, 'CAT'),
('고양이 허피스바이러스', '상부 호흡기 감염과 안구 질환을 일으키는 바이러스성 질병입니다.', 365, 'CAT'),
('고양이 클라미디아', '결막염과 상부 호흡기 감염을 일으키는 세균성 질병입니다.', 365, 'CAT'),

-- 기타 동물 질병
-- 토끼 질병
('마이크소마토시스', '토끼에게 치명적인 바이러스성 질병입니다.', 180, 'OTHER'),
('토끼 출혈병', '급성 바이러스성 질병으로 간에 영향을 줍니다.', 365, 'OTHER'),
-- 햄스터 질병
('웻테일', '햄스터의 꼬리와 항문 주변이 젖어 있는 상태를 말합니다.', NULL, 'OTHER'),
('치아 과다 성장', '햄스터의 치아가 지속적으로 자라 문제를 일으키는 상태입니다.', NULL, 'OTHER');