package raisetech.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private final StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {

    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    //絞り込みをする。年齢が30代以上の人
    //抽出したリストをコントローラーに返す
    /*
      return repository.searchStudent().stream()
        .filter(s -> s.getAge() >= 30)
        .collect(Collectors.toList());
     */
    return repository.searchStudent();
  }

  public List<StudentsCourses> searchStudentsCourseList() {
    return repository.searchStudentsCourse();
    //絞り込み検索で、Javaコースのみのコース情報を抽出する
    //抽出したリストをコントローラーに返す
      /*
      return repository.searchCourse().stream()
          .filter(c -> "Java".equals(c.getCourse()))
          .collect(Collectors.toList());
       */
  }

  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    repository.registerStudent(studentDetail.getStudent());
    for (StudentsCourses studentsCourse : studentDetail.getStudentsCourses()) {
      studentsCourse.setStudentId(studentDetail.getStudent().getStudentId());
      studentsCourse.setStartCourse(LocalDateTime.now());
      studentsCourse.setEndCourse(LocalDateTime.now().plusYears(1));
      repository.registerStudentsCourses(studentsCourse);
    }
  }

  // ★ 追加する更新メソッド
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    for (StudentsCourses course : studentDetail.getStudentsCourses()) {
      repository.updateStudentsCourses(course);
    }
  }

  // ★ 検索機能に使うメソッド
  public Student findById(int id) {
    return repository.findById(id);
  }

  // コースを検索するメソッドを追加
  public List<StudentsCourses> findCoursesById(int id) {
    return repository.findCoursesById(id);
  }
}