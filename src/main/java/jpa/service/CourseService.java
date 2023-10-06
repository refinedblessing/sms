package jpa.service;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jpa.dao.CourseDAO;
import jpa.entitymodels.Course;
import jpa.util.ConnectionFactory;
import org.hibernate.Session;

import java.util.List;

public class CourseService implements CourseDAO {

    ConnectionFactory connectionFactory = ConnectionFactory.GET_SESSION.getInstance();

    @Override
    public Course saveCourse(Course course) {
        try {
            connectionFactory.getSession().persist(course);
            return course;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteCourse(int courseID) {
        TypedQuery<Course> typedQuery = connectionFactory.getSession().createQuery("delete from Course where courseID=:courseID", Course.class);
        typedQuery.setParameter("courseID", courseID);
        typedQuery.executeUpdate();
    }

    @Override
    public Course getCourseByID(int courseID) {
        try {
            TypedQuery<Course> typedQuery = connectionFactory.getSession().createQuery("from Course where courseID=:courseID", Course.class);
            typedQuery.setParameter("courseID", courseID);
            return typedQuery.getSingleResult();
        } catch(NoResultException e) {
            System.out.println("Course not found!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Course> getAllCourses() {
        try {
            Session session = connectionFactory.getSession();
            return session.createQuery("from Course", Course.class).list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void printCourseList() {
        System.out.println(formatCourseList(getAllCourses()));
    }

    public void printCourseList(List<Course> courses) {
        System.out.println(formatCourseList(courses));
    }

    private String formatCourseList(List<Course> courses) {
        if (courses.isEmpty()) return "No Courses!";
        StringBuilder output = new StringBuilder();
        output.append(String.format("%-3s %-35s %s\n", "ID", "NAME", "INSTRUCTOR"));
        for (Course course : courses) {
            output.append(String.format("%-3d %-35s %s\n", course.getCourseID(), course.getCourseName(), course.getCourseInstructor()));
        }
        return output.toString();
    }
}
