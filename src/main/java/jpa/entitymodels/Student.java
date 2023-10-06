package jpa.entitymodels;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Student")
public class Student {

    @Id
    @Column(name="email", unique = true, nullable = false, length = 50)
    private String email;
    @Column(name="name", nullable = false, length = 50)

    private String name;
    @Column(name="password", nullable = false, length = 50)

    private String password;

    @ManyToMany(targetEntity = Course.class, fetch = FetchType.LAZY)
    private List<Course> courses = new ArrayList<>();

    public Student() {
    }

    public Student(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
