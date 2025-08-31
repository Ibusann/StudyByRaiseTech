package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

/**
 * 受講生情報を扱うリポジトリ。
 * <p>
 * 全件検索や単一条件での検索、コース情報の検索が行えるクラスです。
 */
@Mapper
public interface StudentRepository {

  /**
   * 全件検索します。
   *
   * @return 全件検索した受講生情報の一覧
   */
  @Select("SELECT * FROM students")
  List<Student> searchStudent();

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCourse();

  @Insert(
      "INSERT INTO students(name, kana_name, nickname, mail, city, age, gender, remark, is_deleted) "
          + "VALUES(#{name}, #{kanaName}, #{nickname}, #{mail}, #{city}, #{age}, #{gender}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "studentId")
  void registerStudent(Student student);

  @Insert(
      "INSERT INTO students_courses(student_id, course, start_course, end_course) "
          + "VALUES(#{studentId}, #{course}, #{startCourse}, #{endCourse})")
  @Options(useGeneratedKeys = true, keyProperty = "courseId")
  void registerStudentsCourses(StudentsCourses studentsCourses);

  // StudentRepository.java

  @Select("SELECT * FROM students WHERE student_id = #{id}")
  Student findById(int id);

  // ★ 受講生情報を更新するメソッドを追加
  @Update("UPDATE students SET " +
      "name = #{name}, " +
      "kana_name = #{kanaName}, " +
      "nickname = #{nickname}, " +
      "mail = #{mail}, " +
      "city = #{city}, " +
      "age = #{age}, " +
      "gender = #{gender}, " +
      "remark = #{remark}, " +
      "is_deleted = #{isDeleted} " +
      "WHERE student_id = #{studentId}")
  void updateStudent(Student student);

  // コース情報を更新するメソッド
  @Update("UPDATE students_courses SET " +
      "course = #{course}, " +
      "start_course = #{startCourse}, " +
      "end_course = #{endCourse} " +
      "WHERE course_id = #{courseId}")
  void updateStudentsCourses(StudentsCourses studentsCourses);

  // 受講生IDでコースを検索するメソッド
  @Select("SELECT * FROM students_courses WHERE student_id = #{id}")
  List<StudentsCourses> findCoursesById(int id);

}