package raisetech.StudentManagement.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.domain.StudentDetail;
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

  // --- 新しく追加したテスト ---

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
}