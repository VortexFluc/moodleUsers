CREATE TABLE IF NOT EXISTS users
(
    id INTEGER PRIMARY KEY,
    user_id varchar(200) NOT NULL,
    username varchar(200) NOT NULL,
    full_name varchar(200) NOT NULL,
    email varchar(200) NOT NULL,
    institution varchar(200) NOT NULL,
    region varchar(200) NOT NULL,
);

CREATE SEQUENCE users_id_seq START WITH 3 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS times
(
    id INTEGER PRIMARY KEY,
    course VARCHAR(200) NOT NULL,
    is_activated BOOLEAN NOT NULL,
    activation_time TIMESTAMP NOT NULL,
    graded_time TIMESTAMP,
);

CREATE TABLE IF NOT EXISTS courses
(
    id INTEGER PRIMARY KEY,
    course_id INTEGER NOT NULL,
    course_name VARCHAR(200) NOT NULL,
    course_shortname VARCHAR(200) NOT NULL,
);