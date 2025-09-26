package raisetech.StudentManagement.controller.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;

class StudentConverterTest {

  private StudentConverter converter;

  @BeforeEach
  void setUp() {
    converter = new StudentConverter();
  }

  @Test
  void 受講生とコース情報が正しくマッピングされること() {
    // テスト用の受講生データを作成
    List<Student> students = new ArrayList<>();
    Student student1 = new Student();
    student1.setStudentId(1);
    student1.setName("山田太郎");
    students.add(student1);

    Student student2 = new Student();
    student2.setStudentId(2);
    student2.setName("鈴木花子");
    students.add(student2);

    // テスト用の受講生コースデータを作成
    List<StudentsCourses> studentsCourses = new ArrayList<>();
    StudentsCourses course1_1 = new StudentsCourses();
    course1_1.setStudentId(1);
    course1_1.setCourse("Javaコース");
    studentsCourses.add(course1_1);

    StudentsCourses course1_2 = new StudentsCourses();
    course1_2.setStudentId(1);
    course1_2.setCourse("SQLコース");
    studentsCourses.add(course1_2);

    StudentsCourses course2_1 = new StudentsCourses();
    course2_1.setStudentId(2);
    course2_1.setCourse("Rubyコース");
    studentsCourses.add(course2_1);

    // コンバータのメソッドを実行
    List<StudentDetail> result = converter.convertStudentDetails(students, studentsCourses);

    // 検証
    assertNotNull(result);
    assertEquals(2, result.size()); // 2人の受講生がいるので、結果のリストサイズは2になる

    StudentDetail detail1 = result.get(0);
    assertEquals(1, detail1.getStudent().getStudentId()); // IDが正しいか
    assertEquals("山田太郎", detail1.getStudent().getName()); // 名前が正しいか
    assertEquals(2, detail1.getStudentsCourses().size()); // student1に紐づくコースの数が2つか

    StudentDetail detail2 = result.get(1);
    assertEquals(2, detail2.getStudent().getStudentId()); // IDが正しいか
    assertEquals("鈴木花子", detail2.getStudent().getName()); // 名前が正しいか
    assertEquals(1, detail2.getStudentsCourses().size()); // student2に紐づくコースの数が1つか
  }
}