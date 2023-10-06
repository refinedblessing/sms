package jpa.dao;

import jpa.entitymodels.Course;

import java.util.List;

public interface CourseDAO {
    Course saveCourse(Course course);
    void deleteCourse(int courseID);
    Course getCourseByID(int courseID);

    List<Course> getAllCourses();
}
