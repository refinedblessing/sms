package jpa.dao;

import jpa.entitymodels.Course;
import jpa.service.CourseService;
import jpa.util.ConnectionFactory;
import jpa.util.CreateTablesRoutineTest;
import org.junit.jupiter.api.*;

public class CourseDAOTest {
    static ConnectionFactory connectionFactory;
    Course course;
    CourseDAO courseDAO;

    @BeforeAll
    public static void setup() {
        CreateTablesRoutineTest.createTables();
        connectionFactory = ConnectionFactory.GET_SESSION.getInstance();
    }

    @BeforeEach
    public void init() {
        courseDAO = new CourseService();
        course = new Course();

        course.setCourseInstructor("Igor A");
        course.setCourseName("Java");
    }

    @Test
    public void testSaveCourse() {
        Course savedCourse = courseDAO.saveCourse(course);
        Assertions.assertNotNull(savedCourse);
        Assertions.assertEquals(savedCourse.getCourseName(), course.getCourseName());
    }

    @Test
    public void testGetCourseByID() {
        course.setCourseName("Ruby");
        Course savedCourse = courseDAO.saveCourse(course);
        Course courseFound = courseDAO.getCourseByID(savedCourse.getCourseID());
        Assertions.assertEquals(savedCourse, courseFound);
    }

    @Test
    public void testGetCourseByWrongID() {
        Course unknownCourse = courseDAO.getCourseByID(47567836);
        Assertions.assertNull(unknownCourse);
    }

    @AfterAll
    public static void cleanUp() {
        connectionFactory.closeSession();
    }
}
