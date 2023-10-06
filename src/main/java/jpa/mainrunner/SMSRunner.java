package jpa.mainrunner;

import static java.lang.System.out;

import java.util.List;
import java.util.Scanner;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.service.CourseService;
import jpa.service.StudentService;
import jpa.util.ConnectionFactory;

public class SMSRunner {

    static ConnectionFactory connectionFactory = ConnectionFactory.GET_SESSION.getInstance();

    private final Scanner input;

    private final CourseService courseService;
    private final StudentService studentService;
    private Student currentStudent;

    public SMSRunner() {
        input = new Scanner(System.in);
        courseService = new CourseService();
        studentService = new StudentService();
    }

    public static void main(String[] args) {
        SMSRunner sms = new SMSRunner();
        sms.run();

        connectionFactory.closeSession();
    }

    private void run() {
        switch (menu()) {
            case 1:
                studentLogin();
                break;
            case 2:
                studentRegistration();
                break;
            case 3:
                courseService.printCourseList();
                run();
                break;
            case 4:
                studentService.printStudentList();
                run();
                break;
            default:
                out.println("Goodbye!");
        }
    }

    private int menu() {
        out.println("Welcome to Student Management Platform");
        out.print("\n1. Student Login\n2. Student Registration\n3. View All Courses\n4. View All Students\n5. Quit Application\nPlease Enter A Selection: ");
        int selection = input.nextInt();
        input.nextLine();
        return selection;
    }

    private void studentRegistration() {
        out.print("Enter your full name: ");
        String name = input.nextLine();
        out.print("Enter your email address: ");
        String email = input.nextLine();
        out.print("Enter your password: ");
        String password = input.nextLine();

        Student student = new Student(email, name, password);
        if (studentService.saveStudent(student) != null) {
            currentStudent = student;
            registerMenu();
            return;
        }
        out.println("Registration Failed :(");
    }

    private void studentLogin() {
        out.print("Enter your email address: ");
        String email = input.next();
        out.print("Enter your password: ");
        String password = input.next();

        currentStudent = studentService.getStudentByEmail(email);
        if (currentStudent != null) {
            boolean authenticated = studentService.validateStudent(email, password);

            if (authenticated) {
                List<Course> courses = studentService.getStudentCourses(email);
                out.println("My Courses:");
                courseService.printCourseList(courses);

                registerMenu();
                return;
            }
        }

        out.println("Login Failed :(");
    }

    private void registerMenu() {
        out.println("\n1. Register for a Class\n2. Logout\nPlease Enter Selection: ");

        switch (input.nextInt()) {
            case 1:
                List<Course> allCourses = courseService.getAllCourses();
                List<Course> studentCourses = currentStudent.getCourses();
                if (!studentCourses.isEmpty()) allCourses.removeAll(studentCourses);
                courseService.printCourseList(allCourses);

                out.print("Enter Course ID: ");
                int courseID = input.nextInt();
                Course newCourse = courseService.getCourseByID(courseID);

                if (newCourse != null) {
                    studentService.registerStudentToCourse(currentStudent, newCourse);

                    out.println("My Courses:");
                    courseService.printCourseList(currentStudent.getCourses());
                } else {
                    out.println("Course not found!");
                }
                break;
            case 2:
            default:
                out.println("Goodbye!");
        }
    }
}
