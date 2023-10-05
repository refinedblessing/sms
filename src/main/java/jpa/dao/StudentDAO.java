package jpa.dao;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;

import java.util.List;
import java.util.Set;

public interface StudentDAO {
    public List<Student> getAllStudents();
    public Student getStudentByEmail(String email);
    public boolean validateStudent(String email, String password);
    public void registerStudentToCourse(String email, int courseId);
    public Set<Course> getStudentCourses(String email);
}
