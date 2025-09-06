package raisetech.StudentManagement.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Student {

  private int studentId;

  @NotBlank(message = "名前は必須です。")
  @Size(max = 15, message = "名前は15文字以内で入力してください。")
  private String name;

  @Size(max = 20, message = "カナ名は20文字以内で入力してください。")
  private String kanaName;

  @Size(max = 20, message = "ニックネームは20文字以内で入力してください。")
  private String nickname;

  @NotBlank(message = "メールアドレスは必須です。")
  @Email(message = "有効なメールアドレスを入力してください。")
  private String mail;

  @Size(max = 10, message = "都市名は50文字以内で入力してください。")
  private String city;

  @Min(value = 1, message = "年齢は1以上である必要があります。")
  private int age;

  private String gender;

  @Size(max = 255, message = "備考は255文字以内で入力してください。")
  private String remark;

  private boolean isDeleted;
}


