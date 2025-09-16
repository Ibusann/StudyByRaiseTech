package raisetech.StudentManagement.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  @Test
  void searchStudent_リポジトリとコンバーターの処理が適切に呼び出せていること() {
    StudentService sut = new StudentService(repository, converter); // 👈 この行を追加
    List<Student> studentList = new ArrayList<>();
    List<StudentsCourses> studentCourseList = new ArrayList<>();
    when(repository.searchStudent()).thenReturn(studentList);
    when(repository.searchStudentsCourse()).thenReturn(studentCourseList);

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

}