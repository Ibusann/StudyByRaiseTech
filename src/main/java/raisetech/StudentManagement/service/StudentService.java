package raisetech.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private final StudentRepository repository;
  private final StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.searchStudent();
    List<StudentsCourses> studentsCoursesList = repository.searchStudentsCourse();
    return converter.convertStudentDetails(studentList, studentsCoursesList);
  }

  public List<StudentsCourses> searchStudentsCourseList() {
    return repository.searchStudentsCourse();
  }

  /**
   * 受講生の情報を新規登録する際にコース情報もセットして登録する。
   *
   * @param studentDetail
   * @return 登録情報を付与した受講生情報
   */

  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) { // ★ 戻り値をStudentDetailに変更

    Student student = studentDetail.getStudent();
    repository.registerStudent(student);
    studentDetail.getStudentsCourses().forEach(studentsCourse -> {
      initStudentsCourse(studentsCourse, student);
      repository.registerStudentsCourses(studentsCourse);
    });

    return studentDetail; // ★ 登録した内容を含むオブジェクトを返す
  }

  /**
   * 受講生コース情報を登録する際の初期情報を設定する。
   *
   * @param studentsCourse
   * @param student
   */

  private void initStudentsCourse(StudentsCourses studentsCourse, Student student) {
    LocalDateTime now = LocalDateTime.now();
    studentsCourse.setStudentId(student.getStudentId());
    studentsCourse.setStartCourse(now);
    studentsCourse.setEndCourse(now.plusYears(1));
  }

  /**
   * 受講生の情報を更新します。
   */

  // ★ 追加する更新メソッド
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {

    repository.updateStudent(studentDetail.getStudent());
    for (StudentsCourses course : studentDetail.getStudentsCourses()) {
      repository.updateStudentsCourses(course);
    }
  }

  /**
   * 受講生検索です。IDに紐づく受講生情報を取得した後、その受講生に紐づく受講生コーズ情報を取得して設定します。
   *
   * @param id 受講生ID
   * @return 受講生
   */

  // ★ 検索機能に使うメソッド
  public Student findById(int id) {
    return repository.findById(id);
  }

  // コースを検索するメソッドを追加
  public List<StudentsCourses> findCoursesById(int id) {
    return repository.findCoursesById(id);
  }
}