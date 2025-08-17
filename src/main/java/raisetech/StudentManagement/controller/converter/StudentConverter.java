package raisetech.StudentManagement.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.StudentManagement.data.Course;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.domain.StudentDetail;

@Component
public class StudentConverter {

  public List<StudentDetail> convertStudentDetails(List<Student> students,
      List<Course> courses) {
    List<StudentDetail> studentDtails = new ArrayList<>();
    students.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<Course> convertCourses = courses.stream()
          .filter(course -> student.getStudentId() == course.getStudentId())
          .collect(Collectors.toList());

      studentDetail.setStudentCourse(convertCourses);
      studentDtails.add(studentDetail);
    });
    return studentDtails;
  }
}
