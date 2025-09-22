
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
  course_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  student_id INT NOT NULL,
  course varchar(100) DEFAULT NULL,
  start_course datetime DEFAULT NULL,
  end_course datetime DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS students_courses_status (
    status_id INT NOT NULL AUTO_INCREMENT,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    application_status ENUM('仮申込', '本申込', '受講中', '受講終了') NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (status_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (course_id) REFERENCES students_courses(course_id)
);
