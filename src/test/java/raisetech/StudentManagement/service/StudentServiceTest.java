package raisetech.StudentManagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.domain.StudentsDetailStatus;
import raisetech.StudentManagement.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  private StudentService sut;

  @Mock
  private StudentConverter converter;

  @BeforeEach
  void setUp() {
    sut = new StudentService(repository, converter);
  }

  @Test
  void searchStudent_リポジトリとコンバーターの処理が適切に呼び出せていること() {
    List<Student> studentList = new ArrayList<>();
    List<StudentsCourses> studentCourseList = new ArrayList<>();
    List<StudentDetail> studentDetailList = new ArrayList<>();

    when(repository.searchStudent()).thenReturn(studentList);
    when(repository.searchStudentsCourse()).thenReturn(studentCourseList);
    when(converter.convertStudentDetails(studentList, studentCourseList)).thenReturn(
        studentDetailList);

    sut.searchStudentList();

    verify(repository, times(1)).searchStudent();
    verify(repository, times(1)).searchStudentsCourse();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);

  }

  @Test
  void searchStudentsCourseList_リポジトリの処理が適切に呼び出せていること() {
    StudentService sut = new StudentService(repository, converter);
    List<StudentsCourses> studentsCoursesList = new ArrayList<>();
    when(repository.searchStudentsCourse()).thenReturn(studentsCoursesList);

    sut.searchStudentsCourseList();

    verify(repository, times(1)).searchStudentsCourse();
  }

  @Test
  void registerStudent_リポジトリの登録処理が適切に呼び出せていること() {
    StudentService sut = new StudentService(repository, converter);
    StudentDetail studentDetail = new StudentDetail();
    Student student = new Student();
    studentDetail.setStudent(student);
    List<StudentsCourses> courses = new ArrayList<>();
    courses.add(new StudentsCourses());
    studentDetail.setStudentsCourses(courses);

    sut.registerStudent(studentDetail);

    verify(repository, times(1)).registerStudent(student);
    verify(repository, times(1)).registerStudentsCourses(courses.get(0));
  }

  @Test
  void updateStudent_リポジトリの更新処理が適切に呼び出せていること() {
    StudentService sut = new StudentService(repository, converter);
    StudentDetail studentDetail = new StudentDetail();
    Student student = new Student();
    studentDetail.setStudent(student);
    List<StudentsCourses> courses = new ArrayList<>();
    courses.add(new StudentsCourses());
    studentDetail.setStudentsCourses(courses);

    sut.updateStudent(studentDetail);

    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(1)).updateStudentsCourses(courses.get(0));
  }

  @Test
  void findById_リポジトリの検索処理が適切に呼び出せていること() {
    StudentService sut = new StudentService(repository, converter);
    int studentId = 1;
    Student student = new Student();
    when(repository.findById(studentId)).thenReturn(student);

    sut.findById(studentId);

    verify(repository, times(1)).findById(studentId);
  }

  @Test
  void findCoursesById_リポジトリの検索処理が適切に呼び出せていること() {
    StudentService sut = new StudentService(repository, converter);
    int studentId = 1;
    List<StudentsCourses> courses = new ArrayList<>();
    when(repository.findCoursesById(studentId)).thenReturn(courses);

    sut.findCoursesById(studentId);

    verify(repository, times(1)).findCoursesById(studentId);
  }

  @Test
  @DisplayName("getCoursesWithStatusメソッドがリポジトリを正しく呼び出すこと")
  void getCoursesWithStatus_callsRepository() {
    List<StudentsDetailStatus> expectedList = new ArrayList<>();
    when(repository.findCoursesWithStatus()).thenReturn(expectedList);

    List<StudentsDetailStatus> actualList = sut.getCoursesWithStatus();

    verify(repository, times(1)).findCoursesWithStatus();
    assertEquals(expectedList, actualList);
  }

  @Test
  @DisplayName("申込状況で受講生詳細を検索するとリポジトリが正しく呼び出されること")
  void findStudentDetailsByApplicationStatus_callsRepository() {
    // モックの振る舞いを設定
    List<StudentDetail> expectedList = new ArrayList<>();
    when(repository.findStudentDetailsByApplicationStatus("受講中")).thenReturn(expectedList);

    List<StudentDetail> actualList = repository.findStudentDetailsByApplicationStatus("受講中");

    verify(repository, times(1)).findStudentDetailsByApplicationStatus("受講中");
    assertEquals(expectedList, actualList);
  }

}