package com.telucko.Allocation.controller;

import com.telucko.Allocation.dto.StudentDTO;
import com.telucko.Allocation.entity.Student;
import com.telucko.Allocation.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> createStudent(@RequestBody Student student) {
        studentService.saveStudent(student);
        return ResponseEntity.ok("Student added successfully!");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{enrollmentNo}")
    public ResponseEntity<Object> getStudentById(@PathVariable int enrollmentNo) {
        try {
            Optional<Student> student = studentService.getStudentById(enrollmentNo);

            if (student.isPresent()) {
                return ResponseEntity.ok(student.get()); // Return Student if found
            } else {
                return ResponseEntity.status(404).body("Student not found"); // Return 404 with message
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while fetching the student.");
        }
    }
    @DeleteMapping("/{enrollmentNo}")
    public ResponseEntity<String> deleteStudent(@PathVariable int enrollmentNo) {
        boolean isDeleted = studentService.deleteStudent(enrollmentNo);
        if (isDeleted) {
            return ResponseEntity.ok("Student deleted successfully!");
        } else {
            return ResponseEntity.status(404).body("Student not found!");
        }
    }
    @GetMapping("/shortlist")
    public ResponseEntity<List<StudentDTO>> getShortlistedStudents(@RequestParam int availableBeds) {
        return ResponseEntity.ok(studentService.getShortlistedStudents(availableBeds));
    }
}
