insert into file_info(id, file_name, file_path) values(1, 'sample_img_01.jpg', 'C:/Users/ohsang seo/Desktop/GitHub/miniprojects-2019/src/main/resources/static/images/default');
insert into file_info(id, file_name, file_path) values(2, 'eastjun_profile.jpg', 'C:/Users/ohsang seo/Desktop/GitHub/miniprojects-2019/src/main/resources/static/images/default');
insert into file_info(id, file_name, file_path) values(3, 'sample_img_02.jpg', 'C:/Users/ohsang seo/Desktop/GitHub/miniprojects-2019/src/main/resources/static/images/default');


insert into user(id, email, nick_name, password, user_name, fileinfo_id, created_date, modified_date) values(1, 'qwe@naver.com', 'qwe', 'qweqwe', 'qwe',2, CURRENT_TIME(), CURRENT_TIME());
insert into user(id, email, nick_name, password, user_name, fileinfo_id, created_date, modified_date) values(2, 'qwe2@naver.com', 'qwe2', 'qweqwe', 'qwe2',3, CURRENT_TIME(), CURRENT_TIME());

insert into follow(id, followed, follower,created_date, modified_date) values(1, 1, 2,CURRENT_TIME(), CURRENT_TIME());


insert into article(id, contents, author, fileinfo_id, created_date, modified_date) values(1, '1#qwe#qqq#qww#qee', 1, 1,CURRENT_TIME(), CURRENT_TIME());
insert into article(id, contents, author, fileinfo_id, created_date, modified_date) values(2, '2#qwe#qqq', 1, 1,CURRENT_TIME(), CURRENT_TIME());
insert into article(id, contents, author, fileinfo_id, created_date, modified_date) values(3, '3qwe', 1, 1,CURRENT_TIME(), CURRENT_TIME());
insert into article(id, contents, author, fileinfo_id, created_date, modified_date) values(4, '4#qwe', 1, 1,CURRENT_TIME(), CURRENT_TIME());
insert into article(id, contents, author, fileinfo_id, created_date, modified_date) values(5, '5#qwe', 1, 1,CURRENT_TIME(), CURRENT_TIME());
insert into article(id, contents, author, fileinfo_id, created_date, modified_date) values(6, '6#qwe', 1, 1,CURRENT_TIME(), CURRENT_TIME());
insert into article(id, contents, author, fileinfo_id, created_date, modified_date) values(7, '7#qwe', 1, 1,CURRENT_TIME(), CURRENT_TIME());
insert into article(id, contents, author, fileinfo_id, created_date, modified_date) values(8, '8#qwe', 1, 1,CURRENT_TIME(), CURRENT_TIME());
insert into article(id, contents, author, fileinfo_id, created_date, modified_date) values(9, '9#qwe', 1, 1,CURRENT_TIME(), CURRENT_TIME());
insert into article(id, contents, author, fileinfo_id, created_date, modified_date) values(10, '10#qwe', 1, 1,CURRENT_TIME(), CURRENT_TIME());
insert into article(id, contents, author, fileinfo_id, created_date, modified_date) values(11, '11#qwe', 1, 1,CURRENT_TIME(), CURRENT_TIME());
insert into article(id, contents, author, fileinfo_id, created_date, modified_date) values(12, '12#qwe', 1, 1,CURRENT_TIME(), CURRENT_TIME());
insert into article(id, contents, author, fileinfo_id, created_date, modified_date) values(13, '13#qwe', 1, 1,CURRENT_TIME(), CURRENT_TIME());
insert into article(id, contents, author, fileinfo_id, created_date, modified_date) values(14, '14#qwe', 1, 1,CURRENT_TIME(), CURRENT_TIME());
insert into article(id, contents, author, fileinfo_id, created_date, modified_date) values(15, '15#qwe', 1, 1,CURRENT_TIME(), CURRENT_TIME());
insert into article(id, contents, author, fileinfo_id, created_date, modified_date) values(16, '16#qwe', 1, 1,CURRENT_TIME(), CURRENT_TIME());
insert into article(id, contents, author, fileinfo_id, created_date, modified_date) values(17, '17#qwe', 1, 1,CURRENT_TIME(), CURRENT_TIME());
insert into article(id, contents, author, fileinfo_id, created_date, modified_date) values(18, '18#qwe', 1, 1,CURRENT_TIME(), CURRENT_TIME());
insert into article(id, contents, author, fileinfo_id, created_date, modified_date) values(19, '19#qwe', 1, 1,CURRENT_TIME(), CURRENT_TIME());

insert into article(id, contents, author, fileinfo_id, created_date, modified_date) values(20, '1#hello', 2, 1,CURRENT_TIME(), CURRENT_TIME());
insert into article(id, contents, author, fileinfo_id, created_date, modified_date) values(21, '2#hello', 2, 1,CURRENT_TIME(), CURRENT_TIME());
insert into article(id, contents, author, fileinfo_id, created_date, modified_date) values(22, '3#hello', 2, 1,CURRENT_TIME(), CURRENT_TIME());
insert into article(id, contents, author, fileinfo_id, created_date, modified_date) values(23, '4#hello', 2, 1,CURRENT_TIME(), CURRENT_TIME());
insert into article(id, contents, author, fileinfo_id, created_date, modified_date) values(24, '5#hello', 2, 1,CURRENT_TIME(), CURRENT_TIME());


insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(1, '#qwe', 1,CURRENT_TIME(), CURRENT_TIME());
insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(2, '#qqq', 1,CURRENT_TIME(), CURRENT_TIME());
insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(3, '#qww', 1,CURRENT_TIME(), CURRENT_TIME());
insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(4, '#qee', 1,CURRENT_TIME(), CURRENT_TIME());
insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(5, '#qwe', 2,CURRENT_TIME(), CURRENT_TIME());
insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(6, '#qqq', 2,CURRENT_TIME(), CURRENT_TIME());

insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(7, '#qwe', 4,CURRENT_TIME(), CURRENT_TIME());
insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(8, '#qwe', 5,CURRENT_TIME(), CURRENT_TIME());
insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(9, '#qwe', 6,CURRENT_TIME(), CURRENT_TIME());
insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(10, '#qwe', 7,CURRENT_TIME(), CURRENT_TIME());
insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(11, '#qwe', 8,CURRENT_TIME(), CURRENT_TIME());
insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(12, '#qwe', 9,CURRENT_TIME(), CURRENT_TIME());
insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(13, '#qwe', 10,CURRENT_TIME(), CURRENT_TIME());
insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(14, '#qwe', 11,CURRENT_TIME(), CURRENT_TIME());
insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(15, '#qwe', 12,CURRENT_TIME(), CURRENT_TIME());
insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(16, '#qwe', 13,CURRENT_TIME(), CURRENT_TIME());
insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(17, '#qwe', 14,CURRENT_TIME(), CURRENT_TIME());
insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(18, '#qwe', 15,CURRENT_TIME(), CURRENT_TIME());
insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(19, '#qwe', 16,CURRENT_TIME(), CURRENT_TIME());
insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(20, '#qwe', 17,CURRENT_TIME(), CURRENT_TIME());
insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(21, '#qwe', 18,CURRENT_TIME(), CURRENT_TIME());
insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(22, '#qwe', 19,CURRENT_TIME(), CURRENT_TIME());

insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(23, '#hello', 20,CURRENT_TIME(), CURRENT_TIME());
insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(24, '#hello', 21,CURRENT_TIME(), CURRENT_TIME());
insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(25, '#hello', 22,CURRENT_TIME(), CURRENT_TIME());
insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(26, '#hello', 23,CURRENT_TIME(), CURRENT_TIME());
insert into hash_tag(id, keyword, article_id, created_date, modified_date) values(27, '#hello', 24,CURRENT_TIME(), CURRENT_TIME());