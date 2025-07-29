package raisetech.StudentManagement.data;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Course {

  private int courseId;
  private int studentId;
  private String course;
  private LocalDate startCourse;
  private LocalDate endCourse;
}
