package co.edu.uptcsoft.model;

import co.edu.uptcsoft.test.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentsManagerTest {

    private StudentsManager manager;

    @BeforeEach
    void setUp() {
        manager = new StudentsManager();
    }

    @Test
    void testAddStudent() throws DuplicateStudentException, StudentNotFoundException {
        Student s = new Student(1, "Juan", "juan@example.com");
        manager.addStudent(s);

        Student retrieved = manager.getStudentById(1);
        assertEquals("Juan", retrieved.getName());
        assertEquals("juan@example.com", retrieved.getEmail());
    }

    @Test
    void testAddDuplicateStudentThrows() throws DuplicateStudentException {
        manager.addStudent(new Student(2, "Ana", "ana@example.com"));

        DuplicateStudentException ex = assertThrows(DuplicateStudentException.class, () -> {
            manager.addStudent(new Student(2, "Ana2", "ana2@example.com"));
        });

        assertTrue(ex.getMessage().contains("ya existe"));
    }

    @Test
    void testUpdateStudent() throws DuplicateStudentException, StudentNotFoundException {
        manager.addStudent(new Student(3, "Pedro", "pedro@example.com"));

        manager.updateStudent(new Student(3, "Pedro Updated", "pedro2@example.com"));
        Student updated = manager.getStudentById(3);

        assertEquals("Pedro Updated", updated.getName());
        assertEquals("pedro2@example.com", updated.getEmail());
    }

    @Test
    void testUpdateNonExistentStudentThrows() {
        StudentNotFoundException ex = assertThrows(StudentNotFoundException.class, () -> {
            manager.updateStudent(new Student(999, "NoName", "noemail@example.com"));
        });
        assertTrue(ex.getMessage().contains("no encontrado"));
    }

    @Test
    void testDeleteStudent() throws DuplicateStudentException, StudentNotFoundException {
        manager.addStudent(new Student(4, "Laura", "laura@example.com"));

        manager.deleteStudent(4);

        StudentNotFoundException ex = assertThrows(StudentNotFoundException.class, () -> {
            manager.getStudentById(4);
        });
        assertTrue(ex.getMessage().contains("no encontrado"));
    }

    @Test
    void testDeleteNonExistentStudentThrows() {
        StudentNotFoundException ex = assertThrows(StudentNotFoundException.class, () -> {
            manager.deleteStudent(999);
        });
        assertTrue(ex.getMessage().contains("no encontrado"));
    }

    @Test
    void testGetStudent() throws DuplicateStudentException, StudentNotFoundException {
        manager.addStudent(new Student(5, "Sofia", "sofia@example.com"));
        Student s = manager.getStudentById(5);

        assertEquals("Sofia", s.getName());
        assertEquals("sofia@example.com", s.getEmail());
    }

    @Test
    void testGetNonExistentStudentThrows() {
        StudentNotFoundException ex = assertThrows(StudentNotFoundException.class, () -> {
            manager.getStudentById(999);
        });
        assertTrue(ex.getMessage().contains("no encontrado"));
    }
}
