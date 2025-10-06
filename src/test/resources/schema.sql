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
    status_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    -- 修正: ENUM型をH2で互換性のあるVARCHAR型に変更
    application_status VARCHAR(20) NOT NULL, -- ENUMの最長文字列（例: '受講終了'）に合わせて適切な長さを設定
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (course_id) REFERENCES students_courses(course_id)
);