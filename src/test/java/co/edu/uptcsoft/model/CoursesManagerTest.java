package co.edu.uptcsoft.model;

import co.edu.uptcsoft.test.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoursesManagerTest {

    private CoursesManager manager;

    @BeforeEach
    void setUp() {
        manager = new CoursesManager();
    }

    //  CURSOS 
    @Test
    void testAddCourse() throws DuplicateCourseException, CourseNotFoundException {
        Course c = new Course("C1", "Curso 1");
        manager.addCourse(c);

        Course retrieved = manager.findCourse("C1");
        assertEquals("Curso 1", retrieved.getTitle());
    }

    @Test
    void testAddDuplicateCourseThrows() throws DuplicateCourseException {
        manager.addCourse(new Course("C2", "Curso 2"));

        DuplicateCourseException ex = assertThrows(DuplicateCourseException.class, () -> {
            manager.addCourse(new Course("C2", "Curso 2 Duplicate"));
        });
        assertTrue(ex.getMessage().contains("C2"));
    }

    @Test
    void testUpdateCourse() throws DuplicateCourseException, CourseNotFoundException {
        manager.addCourse(new Course("C3", "Curso 3"));
        manager.updateCourse("C3", new Course("C3", "Curso 3 Updated"));

        assertEquals("Curso 3 Updated", manager.findCourse("C3").getTitle());
    }

    @Test
    void testDeleteCourse() throws DuplicateCourseException, CourseNotFoundException {
        manager.addCourse(new Course("C4", "Curso 4"));
        manager.deleteCourse("C4");

        assertThrows(CourseNotFoundException.class, () -> manager.findCourse("C4"));
    }

    //  MÓDULOS 
    @Test
    void testAddModule() throws Exception {
        manager.addCourse(new Course("C5", "Curso 5"));
        Module m = new Module("M1", "Módulo 1");
        manager.addModule("C5", m);

        String str = manager.getCoursesAsString();
        assertTrue(str.contains("Módulo: M1 - Módulo 1"));
    }

    @Test
    void testUpdateModule() throws Exception {
        manager.addCourse(new Course("C6", "Curso 6"));
        Module m = new Module("M2", "Módulo 2");
        manager.addModule("C6", m);

        manager.updateModule("C6", "M2", new Module("M2", "Módulo 2 Updated"));

        String str = manager.getCoursesAsString();
        assertTrue(str.contains("Módulo: M2 - Módulo 2 Updated"));
    }

    @Test
    void testDeleteModule() throws Exception {
        manager.addCourse(new Course("C7", "Curso 7"));
        Module m = new Module("M3", "Módulo 3");
        manager.addModule("C7", m);
        manager.deleteModule("C7", "M3");

        String str = manager.getCoursesAsString();
        assertFalse(str.contains("Módulo: M3"));
    }

    // LECCIONES 
    @Test
    void testAddLesson() throws Exception {
        manager.addCourse(new Course("C8", "Curso 8"));
        Module m = new Module("M4", "Módulo 4");
        manager.addModule("C8", m);

        Lesson l = new Lesson("L1", "Lección 1", "Video");
        manager.addLesson("C8", "M4", l);

        String str = manager.getCoursesAsString();
        assertTrue(str.contains("Lección: L1 - Lección 1 (Video)"));
    }

    @Test
    void testUpdateLesson() throws Exception {
        manager.addCourse(new Course("C9", "Curso 9"));
        Module m = new Module("M5", "Módulo 5");
        manager.addModule("C9", m);

        Lesson l = new Lesson("L2", "Lección 2", "Video");
        manager.addLesson("C9", "M5", l);

        manager.updateLesson("C9", "M5", "L2", new Lesson("L2", "Lección 2 Updated", "Quiz"));

        String str = manager.getCoursesAsString();
        assertTrue(str.contains("Lección: L2 - Lección 2 Updated (Quiz)"));
    }

    @Test
    void testDeleteLesson() throws Exception {
        manager.addCourse(new Course("C10", "Curso 10"));
        Module m = new Module("M6", "Módulo 6");
        manager.addModule("C10", m);

        Lesson l = new Lesson("L3", "Lección 3", "Video");
        manager.addLesson("C10", "M6", l);
        manager.deleteLesson("C10", "M6", "L3");

        String str = manager.getCoursesAsString();
        assertFalse(str.contains("Lección: L3"));
    }

    @Test
    void testCourseNotFoundThrows() {
        assertThrows(CourseNotFoundException.class, () -> manager.findCourse("NO_EXISTE"));
    }
}
