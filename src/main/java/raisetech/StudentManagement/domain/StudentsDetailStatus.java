// src/main/java/raisetech/StudentManagement/domain/StudentDetailStatus.java

package raisetech.StudentManagement.domain;

import lombok.Getter;
import lombok.Setter;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.data.StudentsCoursesStatus;

@Getter
@Setter
public class StudentsDetailStatus {

  private StudentsCourses studentsCourses;
  private StudentsCoursesStatus studentsCoursesStatus;
}