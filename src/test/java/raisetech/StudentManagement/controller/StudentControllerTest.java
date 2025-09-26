package raisetech.StudentManagement.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.domain.StudentsDetailStatus;
import raisetech.StudentManagement.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StudentService service;

  // ObjectMapperをテストで使うために宣言
  private ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {
    when(service.searchStudentList()).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));

    verify(service, times(1)).searchStudentList();
  }

  @Test
  void 受講生詳細の登録が実行できて空で返ってくること() throws Exception {

    mockMvc.perform(post("/registerStudent").contentType(MediaType.APPLICATION_JSON).content(
            """
                {
                "student" : {
                "name" : "江南陽介",
                "kanaName" : "エナミ",
                "nickname" : "ヨウスケ",
                "mail" : "enami.yosuke@example.com",
                "city" : "大阪府",
                "age" : "25",
                "gender" : "男性",
                "remark" : ""
                },
                "studentCourseList" : [
                {
                "id" : "15",
                "studentId" : "12",
                "courseName" : "Javaコース",
                "courseStartAt" : "2024-04-27T10:50:39.833614",
                "courseEndAt" : "2025-04-27T10:50:39.833614"
                }
                ]
                }
                """
        ))
        .andExpect(status().isOk());
  }

  // --- 新しく追加したテスト ---

  @Test
  void 新規登録の内容が成功すると200が返ってくること() throws Exception {
    // 成功するStudentDetailオブジェクトを作成
    Student validStudent = new Student();
    validStudent.setName("山田太郎");
    validStudent.setKanaName("ヤマダタロウ");
    validStudent.setNickname("やまだ");
    validStudent.setMail("yamada@example.com");
    validStudent.setCity("東京");
    validStudent.setAge(25);
    validStudent.setGender("男性");
    validStudent.setRemark("特になし");

    StudentDetail validStudentDetail = new StudentDetail();
    validStudentDetail.setStudent(validStudent);
    validStudentDetail.setStudentsCourses(new ArrayList<>());

    // service.registerStudent()がany()の引数を受け取ったときに、元のオブジェクトを返すように設定
    when(service.registerStudent(any(StudentDetail.class))).thenReturn(validStudentDetail);

    // JSONに変換
    String validJson = objectMapper.writeValueAsString(validStudentDetail);

    // POSTリクエストを送信し、ステータスがOKになることを検証
    mockMvc.perform(post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(validJson))
        .andExpect(status().isOk())
        .andExpect((ResultMatcher) jsonPath("$.student.name").value("山田太郎")); // レスポンスのJSON内容を検証
  }

  @Test
  void 新規登録でバリデーションに失敗したときに400が返ってくること() throws Exception {
    // バリデーションに失敗するStudentDetailオブジェクトを作成
    StudentDetail invalidStudentDetail = new StudentDetail();
    invalidStudentDetail.setStudent(new Student()); // studentIdがnullになるためバリデーションエラーになる

    // JSONに変換
    String invalidJson = objectMapper.writeValueAsString(invalidStudentDetail);

    // POSTリクエストを送信し、ステータスがBAD_REQUESTになることを検証
    mockMvc.perform(post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidJson))
        .andExpect(status().isBadRequest());
  }

  @Test
  void 更新処理でバリデーションに失敗したときに400が返ってくること() throws Exception {
    // バリデーションに失敗するStudentDetailオブジェクトを作成
    StudentDetail invalidStudentDetail = new StudentDetail();
    invalidStudentDetail.setStudent(new Student()); // studentIdがnullになるためバリデーションエラーになる

    // JSONに変換
    String invalidJson = objectMapper.writeValueAsString(invalidStudentDetail);

    // PUTリクエストを送信し、ステータスがBAD_REQUESTになることを検証
    mockMvc.perform(put("/student/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidJson))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("コースとステータスの取得APIが正しいJSONを返すこと")
  void getCoursesWithStatus_returnsCorrectJson() throws Exception {
    // サービスが空のリストを返すようにモックの振る舞いを設定
    List<StudentsDetailStatus> expectedList = Collections.emptyList();
    when(service.getCoursesWithStatus()).thenReturn(expectedList);

    mockMvc.perform(get("/students/status/list"))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(expectedList)));
  }

  @Test
  @DisplayName("申込状況で受講生を検索すると、正しいJSONが返されること")
  void getStudentsByStatus_returnsCorrectJson() throws Exception {
    // モックの振る舞いを設定: サービスが空のリストを返すようにする
    when(service.findStudentDetailsByApplicationStatus("本申込")).thenReturn(
        Collections.emptyList());

    mockMvc.perform(get("/students/status")
            .param("applicationStatus", "本申込"))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));
  }
}

