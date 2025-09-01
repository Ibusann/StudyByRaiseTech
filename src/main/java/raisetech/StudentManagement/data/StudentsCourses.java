package raisetech.StudentManagement.data;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StudentsCourses {

  private int courseId;
  private int studentId;
  private String course;
  private LocalDateTime startCourse;
  private LocalDateTime endCourse;
}
