package raisetech.StudentManagement.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.domain.StudentsDetailStatus;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること() {
    List<Student> actual = sut.searchStudent();
    assertThat(actual.size()).isEqualTo(5);
  }

  @Test
  void 受講生の登録が行えること() {
    Student student = new Student();
    student.setName("江南康二");
    student.setKanaName("エナミコウジ");
    student.setNickname("エナミ");
    student.setMail("test.email@example.com");
    student.setCity("奈良県");
    student.setAge(36);
    student.setGender("男性");
    student.setRemark("");
    student.setDeleted(false);

    sut.registerStudent(student);
    List<Student> actual = sut.searchStudent();

    assertThat(actual.size()).isEqualTo(6);
  }

  @Test
  @DisplayName("既存の受講生情報が正しく更新できること")
  void updateExistingStudentSuccessfully() {
    Student updatedStudent = new Student();
    updatedStudent.setStudentId(1);
    updatedStudent.setName("更新された ユーザー");
    updatedStudent.setAge(25);
    sut.updateStudent(updatedStudent);
    Student foundStudent = sut.findById(1);
    assertEquals("更新された ユーザー", foundStudent.getName());
    assertEquals(25, foundStudent.getAge());
  }

  @Test
  @DisplayName("IDで受講生情報が取得できること")
  void findStudentByIdSuccessfully() {
    Student foundStudent = sut.findById(1);
    assertNotNull(foundStudent);
    assertEquals(1, foundStudent.getStudentId());
    assertEquals("山田 太郎", foundStudent.getName());
  }

  @Test
  @DisplayName("IDで受講生のコース情報が取得できること")
  void findCoursesByIdSuccessfully() {
    // students_coursesテーブルのstudent_idがvarchar(100)なので、ここではIDを文字列として扱います
    List<StudentsCourses> courses = sut.findCoursesById(1);
    assertNotNull(courses);
    assertEquals(1, courses.size());
    assertEquals("Javaコース", courses.get(0).getCourse());
  }

  @Test
  @DisplayName("コースと申込状況が正しく取得できること")
  void findCoursesWithStatus_returnsCorrectly() {
    // DBに存在するデータに合わせて、5件のデータが取得されることを期待
    List<StudentsDetailStatus> result = sut.findCoursesWithStatus();

    assertNotNull(result);
    assertEquals(5, result.size());

    // 取得したデータの最初の要素を検証
    StudentsDetailStatus firstResult = result.get(0);
    assertEquals(1, firstResult.getStudentsCourses().getCourseId());
    assertEquals("Javaコース", firstResult.getStudentsCourses().getCourse());
    assertEquals("本申込", firstResult.getStudentsCoursesStatus().getApplicationStatus());

    // 取得したデータの最後の要素を検証
    StudentsDetailStatus lastResult = result.get(4);
    assertEquals(5, lastResult.getStudentsCourses().getCourseId());
    assertEquals("デザイン思考", lastResult.getStudentsCourses().getCourse());
    assertEquals("本申込", lastResult.getStudentsCoursesStatus().getApplicationStatus());
  }

  @Test
  @DisplayName("申込状況が「受講中」の受講生を検索できること")
  void findStudentDetailsByApplicationStatus_returnsCorrectly() {

    List<StudentDetail> result = sut.findStudentDetailsByApplicationStatus("受講中");

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("田中 健太", result.get(0).getStudent().getName());
    assertEquals("フロントエンド開発", result.get(0).getStudentsCourses().get(0).getCourse());
  }
}


