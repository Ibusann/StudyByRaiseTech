package raisetech.StudentManagement;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Student {
  private String studentId;
  private String name;
  private String furigana;
  private String nickname;
  private String mail;
  private String city;
  private int age;
  private String gender;
}


