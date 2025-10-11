package raisetech.StudentManagement.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentsCoursesStatus {

  private int statusId;
  private int studentId;
  private int courseId;
  private String applicationStatus;

}
