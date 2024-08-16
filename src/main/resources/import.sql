insert into postcategory(category_id, category_name) values (1, '자유게시판');
insert into postcategory(category_id, category_name) values (2, '질의응답게시판');
insert into postcategory(category_id, category_name) values (3, '리뷰게시판');

insert into post(post_id, user_id, category_id, thumbnail_path, title, content, content_image_path, view_count, like_count, status, created_at, updated_at, attachment_paths) values (1, 1, 2, 'abc', 'abc', 'abc', 'abc', 0, 0, 0, now(), now(), 'abc');