package jpa.service;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jpa.dao.StudentDAO;
import jpa.entitymodels.Student;
import jpa.entitymodels.Course;
import jpa.util.ConnectionFactory;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class StudentService implements StudentDAO {
    ConnectionFactory connectionFactory = ConnectionFactory.GET_SESSION.getInstance();

    @Override
    public Student saveStudent(Student student) {
        try {
            connectionFactory.getSession().persist(student);
            return student;
        } catch (ConstraintViolationException e) {
            System.out.println("Seems like you already registered!");
        }
        catch (Exception e) {
            System.out.println("Unable to Register you :(");
        }
        return null;
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try {
            Session session = connectionFactory.getSession();
            students = session.createQuery("from Student", Student.class).list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return students;
    }

    @Override
    public Student getStudentByEmail(String email) {
        try {
            TypedQuery<Student> typedQuery = connectionFactory.getSession().createQuery("from Student where email=:email", Student.class);
            typedQuery.setParameter("email", email);
            return typedQuery.getSingleResult();
        } catch(NoResultException e) {
            System.out.println("Student not found");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean validateStudent(String email, String password) {
        Student student = getStudentByEmail(email);
        if (student == null) return false;
        return student.getPassword().equals(password);
    }

    @Override
    public void registerStudentToCourse(String email, int courseId) {
        Student student = getStudentByEmail(email);
        Course course = new CourseService().getCourseByID(courseId);
        student.getCourses().add(course);
    }

    @Override
    public void registerStudentToCourse(Student student, Course course) {
        student.getCourses().add(course);
    }

    @Override
    public List<Course> getStudentCourses(String email) {
        Student student = getStudentByEmail(email);
        return student.getCourses();
    }

    public void printStudentList() {
        System.out.println(formatStudentList(getAllStudents()));
    }

    public void printStudentList(List<Student> students) {
        System.out.println(formatStudentList(students));
    }

    private String formatStudentList(List<Student> students) {
        if (students.isEmpty()) return "No Students!";
        StringBuilder output = new StringBuilder();
        output.append(String.format("%-40s %s\n", "NAME", "EMAIL"));
        for (Student student : students) {
            output.append(String.format("%-40s %s\n", student.getName(), student.getEmail()));
        }
        return output.toString();
    }
}
