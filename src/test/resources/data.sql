INSERT INTO students (name, kana_name, nickname, mail, city, age, gender, remark)
VALUES
('山田 太郎', 'ヤマダ タロウ', 'やま', 'yamada.t@example.com', '東京都', 25, '男性', 'Javaコース受講'),
('鈴木 花子', 'スズキ ハナコ', 'はな', 'suzuki.h@example.com', '大阪府', 22, '女性', 'Web開発に興味あり'),
('田中 健太', 'タナカ ケンタ', 'けんけん', 'tanaka.k@example.com', '福岡県', 30, '男性', '未経験からエンジニアへ'),
('佐藤 ゆう', 'サトウ ユウ', 'ゆう', 'sato.y@example.com', '愛知県', 19, '非公開', '高校卒業後に入学'),
('吉田 葵', 'ヨシダ アオイ', 'あお', 'yoshida.a@example.com', '北海道', 28, '女性', 'UI/UXデザインも学ぶ');

INSERT INTO students_courses (student_id, course, start_course, end_course)
VALUES
(1,'Javaコース', '2025-04-01 09:00:00', '2026-03-31 17:00:00'),
(2,'データベース基礎', '2025-05-15 09:00:00', '2025-08-15 17:00:00'),
(3,'フロントエンド開発', '2025-04-10 09:00:00', '2026-03-31 17:00:00'),
(4,'Python AIコース', '2025-06-01 09:00:00', '2026-05-31 17:00:00'),
(5,'デザイン思考', '2025-07-20 09:00:00', '2025-10-20 17:00:00');

INSERT INTO students_courses_status (student_id, course_id, application_status)
VALUES
(1, 1, '本申込'),
(2, 2, '仮申込'),
(3, 3, '受講中'),
(4, 4, '受講終了'),
(5, 5, '本申込');