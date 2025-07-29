package raisetech.StudentManagement.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.data.Course;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

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

  public List<Course> searchCourseList() {
    //絞り込み検索で、Javaコースのみのコース情報を抽出する
    //抽出したリストをコントローラーに返す
    /*
    return repository.searchCourse().stream()
        .filter(c -> "Java".equals(c.getCourse()))
        .collect(Collectors.toList());

     */
    return repository.searchCourse();
  }

  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    repository.registerStudent(studentDetail.getStudent());
  }

}

