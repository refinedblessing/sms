package jpa.dao;

import jpa.entitymodels.Course;

import java.util.List;

public interface CourseDAO {
    public Course saveCourse(Course course);
    public void deleteCourse(int courseID);
    public Course getCourseByID(int courseID);

    public List<Course> getAllCourses();
}
