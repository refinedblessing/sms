package jpa.entitymodels;

import jakarta.persistence.*;

@Entity
@Table(name = "Course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int courseId;
    @Column(name="name", nullable = false, length = 50)

    private String courseName;
    @Column(name="instructor", nullable = false, length = 50)

    private String courseInstructor;

    public Course() {
    }

    public Course(String name, String instructor) {
        this.courseName = name;
        this.courseInstructor = instructor;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseInstructor() {
        return courseInstructor;
    }

    public void setCourseInstructor(String courseInstructor) {
        this.courseInstructor = courseInstructor;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + courseId +
                ", name='" + courseName + '\'' +
                ", instructor='" + courseInstructor + '\'' +
                '}';
    }
}
