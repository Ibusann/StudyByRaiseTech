CREATE TABLE IF NOT EXISTS students (
  student_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  kana_name VARCHAR(100),
  nickname VARCHAR(100),
  mail VARCHAR(100),
  city VARCHAR(100),
  age INT,
  gender VARCHAR(100),
  remark VARCHAR(300),
  is_deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS students_courses (
  course_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  student_id INT NOT NULL,
  course VARCHAR(100),
  start_course DATETIME,
  end_course DATETIME
);

CREATE TABLE IF NOT EXISTS students_courses_status (
    status_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    application_status VARCHAR(100) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);