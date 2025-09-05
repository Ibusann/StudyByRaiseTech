package raisetech.StudentManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして実行されるControllerである。
 */

@RestController
@Validated

public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生一覧検索API 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生一覧(全件)
   */

  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 受講生検索API この機能は、IDに基づいて個別の受講生情報を検索し、その詳細をJSON形式で返します。
   */

  @GetMapping("/update/{id}")
  public StudentDetail getStudentDetail(@PathVariable int id) { // ★ メソッド名を変更
    // IDに基づいてStudentを取得
    Student student = service.findById(id);
    // IDに基づいてStudentsCoursesを取得
    List<StudentsCourses> courses = service.findCoursesById(id);
    // StudentDetailオブジェクトを構築
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentsCourses(courses);
    // StudentDetailオブジェクトを返す
    return studentDetail;
  }

  /**
   * 新規登録API この機能は、新しい受講生情報をデータベースに登録し、登録が完了した詳細な情報をJSON形式で返します。
   */
  @PostMapping("/registerStudent")
  public StudentDetail registerStudent(
      @RequestBody StudentDetail studentDetail) { // ★ 戻り値をStudentDetailに変更
    StudentDetail registeredStudent = service.registerStudent(studentDetail);
    return registeredStudent; // ★ 登録した内容を返す
  }

  /**
   * 更新処理API この機能は、既存の受講生情報を更新します。 キャンセルフラグの更新もここで行う。（論理削除）
   *
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */

  @PutMapping("student/update")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }

}
/*　model
@GetMapping("/newStudent")
public String newStudent(Model model) {
  StudentDetail studentDetail = new StudentDetail();
  // ★ ここでstudentオブジェクトを初期化する
  studentDetail.setStudent(new Student());
  studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourses()));
  // studentsCoursesリストを初期化し、1つ以上の空のオブジェクトを追加
  // モデルにオブジェクトを追加
  model.addAttribute("studentDetail", studentDetail);
  return "registerStudent";
}

 */