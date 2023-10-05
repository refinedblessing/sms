package jpa.dao;

import jpa.entitymodels.Course;

import java.util.Set;

public interface CourseDAO {
    public Set<Course> getAllCourses();
}
