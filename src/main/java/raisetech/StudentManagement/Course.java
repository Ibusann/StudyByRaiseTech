package raisetech.StudentManagement;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Course {
  private String courseId;
  private String studentId;
  private String course;
  private LocalDate startCourse;
  private LocalDate endCourse;
}
