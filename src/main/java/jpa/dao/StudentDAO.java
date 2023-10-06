package jpa.dao;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;

import java.util.List;

public interface StudentDAO {
    Student saveStudent(Student student);
    List<Student> getAllStudents();
    Student getStudentByEmail(String email);
    boolean validateStudent(String email, String password);
    void registerStudentToCourse(String email, int courseId);
    void registerStudentToCourse(Student student, Course course);

    List<Course> getStudentCourses(String email);
}
