INSERT INTO USERS (email, password, username, age, gender, married, job_group, job_position, s,g,d) VALUES('admin@gb-soft.com', '1234', 'ADMIN', 23, 'M', 'Y', 'EMPLOYEE', 'MANAGEMENT', '서울시', '강남구', '대치동');
INSERT INTO USERS (email, password, username, age, gender, married, job_group, job_position, s,g,d) VALUES('ssw@gb-soft.com', '1234', '손성우', 34, 'M', 'N', 'G', 'IT', '서울시', '마포구', '아현동');
INSERT INTO USERS (email, password, username, age, gender, married, job_group, job_position, s,g,d) VALUES('hss@gb-soft.com', '1234', '한새싹', 29, 'F', 'N', 'I', 'IT', '서울시', '노원구', '상계동');


INSERT INTO NOTICE (title, content, user_id, registration_date) values ('제목1', '내용1', 1, now());
INSERT INTO NOTICE (title, content, user_id, registration_date) values ('제목2', '내용2', 2, now());
INSERT INTO NOTICE (title, content, user_id, registration_date) values ('title', 'content', 3, now());
