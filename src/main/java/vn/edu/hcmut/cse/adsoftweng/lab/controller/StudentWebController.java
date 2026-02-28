package vn.edu.hcmut.cse.adsoftweng.lab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // Luu y: su dung @Controller, KHONG dung @RestController
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.edu.hcmut.cse.adsoftweng.lab.service.StudentService;
import vn.edu.hcmut.cse.adsoftweng.lab.entity.Student;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentWebController {
    
    @Autowired
    private StudentService service;
/*
    // Route: GET http://localhost:8080/students
    @GetMapping
    public String getAllStudents(Model model) {
        // 1. Lay du lieu tu Service
        List<Student> students = service.getAll();

        // 2. Dong goi du lieu vao "Model" de chuyen sang View
        // Key "dsSinhVien" se duoc su dung ben file HTML
        model.addAttribute("dsSinhVien", students);

        // 3. Tra ve ten cua View (khong can duoi .html)
        // Spring Boot se tu dong tim file tai: src/main/resources/templates/students.html
        return "students";
    } 
*/  
    // Hien thi danh sach sinh vien
    // Route: GET http://localhost:8080/students
    // Them tham so "keyword" de tim kiem theo ten
    // Route: GET http://localhost:8080/students?keyword=abc
    @GetMapping
    public String getAllStudents(@RequestParam(required = false) String keyword, Model model) {
        List<Student> students;
        if (keyword != null && !keyword.isEmpty()) {
            // Can viet them ham searchByName trong Service/Repository
            students = service.searchByName(keyword);
        } else {
            students = service.getAll();
        }
        model.addAttribute("dsSinhVien", students);
        return "students";
    }

    // Hien thi chi tiet sinh vien
    // Route: GET http://localhost:8080/students/{id}
    @GetMapping("/{id}")
    public String viewStudent(@PathVariable String id, Model model) {
        Student student = service.getById(id);
        if (student == null) {
            return "redirect:/students";
        }
        model.addAttribute("student", student);
        return "student-detail";
    }

    // Tao moi sinh vien
    // Route: GET http://localhost:8080/students/new
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        return "student-form";
    }

    // Luu sinh vien moi tao
    // Route: POST http://localhost:8080/students/save
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute Student student) {
        service.save(student);
        return "redirect:/students";
    }

    // Hien thi form chinh sua sinh vien
    // Route: GET http://localhost:8080/students/edit/{id}
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Student student = service.getById(id);
        if (student == null) {
            return "redirect:/students";
        }
        model.addAttribute("student", student);
        return "student-form";
    }

    // Xoa sinh vien
    // Route: POST http://localhost:8080/students/delete/{id}
    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable String id) {
        service.deleteById(id);
        return "redirect:/students";
    }
}

