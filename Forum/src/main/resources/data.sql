INSERT INTO USER(name, email, password) VALUES('Student', 'student@email.com', '$2a$10$5SgZ2yE03pUKXt0q7Nu2q.CujRUpbKVbwR.H5NkzT3IloGs514xAO');

INSERT INTO COURSE(name, category) VALUES('Spring Boot', 'Programation');
INSERT INTO COURSE(name, category) VALUES('HTML 5', 'Front-end');

INSERT INTO TOPIC(title, post, creation_date, status, user_id, course_id) VALUES('Doubt', 'Error when create the project', '2019-05-05 18:00:00', 'NOT_ANSWERED', 1, 1);
INSERT INTO TOPIC(title, post, creation_date, status, user_id, course_id) VALUES('Doubt 2', 'Project do not compile', '2019-05-05 19:00:00', 'NOT_ANSWERED', 1, 1);
INSERT INTO TOPIC(title, post, creation_date, status, user_id, course_id) VALUES('Doubt 3', 'HTML tag', '2019-05-05 20:00:00', 'NOT_ANSWERED', 1, 2);