package com.telucko.Allocation.service;

import com.telucko.Allocation.dto.StudentDTO;
import com.telucko.Allocation.entity.Student;
import com.telucko.Allocation.repo.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student saveStudent(Student student) {
        // Calculate points
        int cgpaPoints = calculateCGPAPoints(student.getCgpa());
        int orphanPoints = "yes".equalsIgnoreCase(student.getOrphanChildOfWidow()) ? 10 : 0;
        int nccPoints = "yes".equalsIgnoreCase(student.getNationalCadetCorps()) ? 3 : 0;
        int sportsPoints = "national".equalsIgnoreCase(student.getSports()) ? 5 : 0;
        int distancePoints = calculateDistancePoints(student.getDistance());
        int coursePoints = calculateCoursePoints(student.getCourseId());

        int totalPoints = cgpaPoints + orphanPoints + nccPoints + sportsPoints + distancePoints + coursePoints;

        student.setCgpaPoint(cgpaPoints);
        student.setOrphanPoint(orphanPoints);
        student.setCoursePoint(coursePoints);
        student.setNccPoint(nccPoints);
        student.setSportsPoint(sportsPoints);
        student.setDistancePoint(distancePoints);
        student.setTotalPoint(totalPoints);

        return studentRepository.save(student);
    }
    public List<StudentDTO> getShortlistedStudents(int availableBeds) {
        return studentRepository.findAll()
                .stream()
                .sorted((s1, s2) -> Integer.compare(s2.getTotalPoint(), s1.getTotalPoint())) // Sort in descending order
                .limit(availableBeds) // Limit based on available beds
                .map(student -> new StudentDTO(student.getFirstName() + " " + student.getLastName(),
                        student.getCourseId(),
                        student.getTotalPoint()))
                .collect(Collectors.toList());
    }
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(int enrollmentNo) {
        return studentRepository.findById(enrollmentNo);
    }

    @Transactional
    public boolean deleteStudent(int enrollmentNo) {
        if (studentRepository.existsById(enrollmentNo)) {
            studentRepository.deleteById(enrollmentNo);
            return true;
        }
        return false;
    }

    private int calculateCGPAPoints(double cgpa) {
        if (cgpa >= 4 && cgpa < 5) return 20;
        else if (cgpa >= 5 && cgpa < 6) return 25;
        else if (cgpa >= 6 && cgpa < 7) return 30;
        else if (cgpa >= 7 && cgpa < 8) return 35;
        else if (cgpa >= 8) return 40;
        return 0;
    }

    private int calculateDistancePoints(int distance) {
        if (distance >= 500 && distance < 1000) return 2;
        else if (distance >= 1000 && distance < 1500) return 3;
        else if (distance >= 2000) return 5;
        return 0;
    }

    private int calculateCoursePoints(int courseId) {
        return switch (courseId) {
            case 1 -> 20;
            case 2 -> 25;
            case 3 -> 30;
            case 4 -> 35;
            default -> 0;
        };
    }
}
