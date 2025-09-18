CREATE TABLE IF NOT EXISTS students (
  student_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100),
  kana_name VARCHAR(100),
  nickname VARCHAR(100),
  mail VARCHAR(100),
  city VARCHAR(100),
  age INT,
  gender VARCHAR(100),
  remark VARCHAR(300),
  is_deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS students_courses
(
  course_id int NOT NULL AUTO_INCREMENT,
  student_id varchar(100) DEFAULT NULL,
  course varchar(100) DEFAULT NULL,
  start_course datetime DEFAULT NULL,
  end_course datetime DEFAULT NULL
);
