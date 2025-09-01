package raisetech.StudentManagement.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

@Controller
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public String getStudentList(Model model) {
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.searchStudentsCourseList();

    model.addAttribute("studentList", converter.convertStudentDetails(students, studentsCourses));
    return "studentList";
  }

  @GetMapping("/studentsCourseList")
  public List<StudentsCourses> getStudentsCourseList() {
    return service.searchStudentsCourseList();
  }

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

  @GetMapping("/update/{id}")
  public String showUpdateForm(@PathVariable("id") int id, Model model) {
    Student student = service.findById(id);
    if (student == null) {
      return "redirect:/studentList";
    }
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    List<StudentsCourses> courses = service.findCoursesById(id);
    studentDetail.setStudentsCourses(courses);
    model.addAttribute("studentDetail", studentDetail);
    return "student-update";
  }

  @PostMapping("student/update")
  public String updateStudent(@ModelAttribute StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return "redirect:/studentList";
  }

  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "registerStudent";
    }
    service.registerStudent(studentDetail);
    return "redirect:/studentList";
  }
}

//  <td>
//      <input type="checkbox" th:field="${studentDetail.student.isDeleted}">
//    </td>