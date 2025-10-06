package raisetech.StudentManagement.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentCourseStatusDetail;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして実行されるControllerである。
 */

@Validated
@RestController

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
      @Valid @RequestBody StudentDetail studentDetail) { // ★ 戻り値をStudentDetailに変更
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
  public ResponseEntity<String> updateStudent(@Valid @RequestBody StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }

  /**
   * 受講生の申込状況の一覧を取得するAPIです。
   *
   * @return 申込状況を確認する受講生詳細情報のリスト
   */
  // 修正: 戻り値を ResponseEntity<List<StudentsDetailStatus>> に変更
  @GetMapping("/students/status/list")
  public ResponseEntity<List<StudentCourseStatusDetail>> getCoursesWithStatus() {
    // Listを直接返す代わりにResponseEntity.ok()で包む
    return ResponseEntity.ok(service.getCoursesWithStatus());
  }

  /**
   * 申込状況に基づいて受講生を検索するAPIです。
   *
   * @param applicationStatus 検索したい申込状況（例: "受講中"）
   * @return 申込状況に一致する受講生詳細情報のリスト
   */
  @GetMapping("/students/status")
  public ResponseEntity<List<StudentDetail>> getStudentsByStatus(
      // 修正: @NotBlankを追加し、空文字または空白のみの入力を禁止する
      @RequestParam @NotBlank(message = "申込状況は空にできません。") String applicationStatus) {

    // この時点で applicationStatus が null, 空文字, 空白のみの場合は、
    // Controllerの@Validatedと@NotBlankにより400エラーが自動的に発生します。

    return ResponseEntity.ok(service.findStudentDetailsByApplicationStatus(applicationStatus));
  }

  /**
   * RequestBody（@Valid）の入力チェックを行うエラーハンドリング
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    StringBuilder errors = new StringBuilder();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String errorMessage = error.getDefaultMessage();
      errors.append(errorMessage).append("\n");
    });
    return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
  }

  /**
   * RequestParam/PathVariable（@Validated）の入力チェックを行うエラーハンドリング
   *
   * @param ex ConstraintViolationException
   * @return エラーメッセージと400 BAD REQUEST
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<String> handleConstraintViolationException(
      ConstraintViolationException ex) {
    StringBuilder errors = new StringBuilder();
    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
      // エラーメッセージだけを取得し、整形する
      errors.append(violation.getMessage()).append("\n");
    }
    return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
  }
}



