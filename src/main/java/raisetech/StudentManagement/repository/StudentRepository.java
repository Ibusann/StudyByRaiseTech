package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
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
   * @return 全件検索した受講生一覧
   */

  List<Student> searchStudent();

  /**
   * 受講生のコース情報を全件検索
   *
   * @return　受講生のコース情報
   */

  List<StudentsCourses> searchStudentsCourse();

  /**
   * 受講生の新規登録を行う。 IDは自動採番される。
   *
   * @param student
   */

  void registerStudent(Student student);

  void registerStudentsCourses(StudentsCourses studentsCourses);

  /**
   * IDに紐づく受講生の情報を取得する。
   *
   * @param id
   * @return 受講生
   */

  // 受講生IDで受講生を検索するメソッド
  Student findById(int id);

  // 受講生IDでコースを検索するメソッド
  List<StudentsCourses> findCoursesById(int id);

  /**
   * IDに紐づく受講生情報に更新処理を行う。
   *
   * @param student ID return 更新された受講生の情報全件。
   */

  // ★ 受講生情報を更新するメソッドを追加
  void updateStudent(Student student);

  // コース情報を更新するメソッド
  void updateStudentsCourses(StudentsCourses studentsCourses);
}