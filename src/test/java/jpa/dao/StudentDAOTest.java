package jpa.dao;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.service.StudentService;
import jpa.util.ConnectionFactory;
import jpa.util.CreateTablesRoutineTest;
import org.junit.jupiter.api.*;

public class StudentDAOTest {
    static ConnectionFactory connectionFactory;
    Student student;
    StudentDAO studentDAO;

    @BeforeAll
    public static void setup() {
        CreateTablesRoutineTest.createTables();
        connectionFactory = ConnectionFactory.GET_SESSION.getInstance();
    }

    @BeforeEach
    public void init() {
        studentDAO = new StudentService();
        student = new Student();

        student.setEmail("bb@gmail.com");
        student.setName("Bless High");
        student.setPassword("password");
    }

    @Test
    public void testSaveStudent() {
        Student savedStudent = studentDAO.saveStudent(student);
        Assertions.assertNotNull(savedStudent);
        Assertions.assertEquals(savedStudent.getName(), student.getName());
        Assertions.assertEquals(savedStudent.getEmail(), student.getEmail());
    }

    @Test
    public void testSaveRegisteredStudent() {
//      check that we cant save same student twice
        Student oldStudent = studentDAO.saveStudent(student);
        Assertions.assertNull(oldStudent);
    }

    @Test
    public void testGetStudentByEmail() {
        student.setEmail("qq@gmail.com");
        System.out.println(student);
        Student savedStudent = studentDAO.saveStudent(student);
        Student studentFound = studentDAO.getStudentByEmail(savedStudent.getEmail());
        Assertions.assertEquals(savedStudent, studentFound);
    }
    @Test
    public void testGetStudentByWrongEmail() {
        Student unknownStudent = studentDAO.getStudentByEmail("abracadabra@gmail.com");
        Assertions.assertNull(unknownStudent);
    }
    @AfterAll
    public static void cleanUp() {
        connectionFactory.closeSession();
    }
}
