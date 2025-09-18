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
    updatedStudent.setStudentId(2);
    updatedStudent.setName("更新された ユーザー");
    updatedStudent.setAge(25);
    sut.updateStudent(updatedStudent);
    Student foundStudent = sut.findById(999);
    assertEquals("更新された ユーザー", foundStudent.getName());
    assertEquals(25, foundStudent.getAge());
  }

  @Test
  @DisplayName("IDで受講生情報が取得できること")
  void findStudentByIdSuccessfully() {
    Student foundStudent = sut.findById(2);
    assertNotNull(foundStudent);
    assertEquals(2, foundStudent.getStudentId());
    assertEquals("山田 太郎", foundStudent.getName());
  }

  @Test
  @DisplayName("IDで受講生のコース情報が取得できること")
  void findCoursesByIdSuccessfully() {
    // students_coursesテーブルのstudent_idがvarchar(100)なので、ここではIDを文字列として扱います
    List<StudentsCourses> courses = sut.findCoursesById(2);
    assertNotNull(courses);
    assertEquals(1, courses.size());
    assertEquals("Javaコース", courses.get(0).getCourse());
  }
}

