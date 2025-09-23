package co.edu.uptcsoft.model;

<<<<<<< HEAD
import co.edu.uptcsoft.test.*;

=======
import co.edu.uptcsoft.test.CourseNotFoundException;
import co.edu.uptcsoft.test.DuplicateLessonException;
import co.edu.uptcsoft.test.DuplicateModuleException;
import co.edu.uptcsoft.test.LessonNotFoundException;
import co.edu.uptcsoft.test.ModuleNotFoundException;
>>>>>>> ce4c03f78796e33d91b87ff4455f45f1748f3a7a
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoursesManagerTest {

<<<<<<< HEAD
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
=======
    private CoursesManager coursesManager;

    @BeforeEach
    void setUp() {
        coursesManager = new CoursesManager();
    }

    ////////////////////      CURSOS         


    @Test /////////añadir cursos correctamente
    void AddCourse() {
        Course course = new Course("C1", "Curso 1");
        coursesManager.addCourse(course);
        assertEquals(1, coursesManager.getCourses().size());
        assertEquals("C1", coursesManager.getCourses().get(0).getId());
    }

    @Test ////////////  encontrar curso existente
    void FindCourse() throws CourseNotFoundException {
        Course course = new Course("C1", "Curso 1");
        coursesManager.addCourse(course);
        Course found = coursesManager.findCourse("C1");
        assertNotNull(found);
        assertEquals("Curso 1", found.getTitle());
    }

    @Test //  al buscar curso lanza excepcion
    void throwWhenCourseNotFound() {
        assertThrows(CourseNotFoundException.class, () -> {
            coursesManager.findCourse("NoExiste");
        });
    }

    @Test //////////  actualizar curso correctamente
    void updateCourse() throws CourseNotFoundException {
        Course course = new Course("C1", "Curso 1");
        coursesManager.addCourse(course);

        Course updatedCourse = new Course("C1", "Curso Actualizado");
        coursesManager.updateCourse("C1", updatedCourse);

        Course found = coursesManager.findCourse("C1");
        assertEquals("Curso Actualizado", found.getTitle());
    }

    @Test //// eliminar curso correctamente
    void deleteCourse() throws CourseNotFoundException {
        Course course = new Course("C1", "Curso 1");
        coursesManager.addCourse(course);

        coursesManager.deleteCourse("C1");
        assertEquals(0, coursesManager.getCourses().size());

        assertThrows(CourseNotFoundException.class, () -> {
            coursesManager.findCourse("C1");
        });
    }

    ////////////////////// MÓDULOS
         
    @Test /// añadir modulo correctamente
    void addModule() throws DuplicateModuleException, CourseNotFoundException {
        Course course = new Course("C1", "Curso 1");
        coursesManager.addCourse(course);

        Module module = new Module("M1", "Módulo 1");
        coursesManager.addModule("C1", module);

        assertEquals(1, coursesManager.findCourse("C1").getModules().size());
        assertEquals("M1", coursesManager.findCourse("C1").getModules().get(0).getId());
    }

    @Test ////  al añadir modulo duplicado lanza una excepcion
    void throwWhenDuplicateModule() throws DuplicateModuleException, CourseNotFoundException {
        Course course = new Course("C1", "Curso 1");
        coursesManager.addCourse(course);

        Module module = new Module("M1", "Módulo 1");
        coursesManager.addModule("C1", module);

        assertThrows(DuplicateModuleException.class, () -> {
            coursesManager.addModule("C1", new Module("M1", "Módulo duplicado"));
        });
    }

    @Test /// actualiza modulo correctamente
    void updateModule() throws CourseNotFoundException, ModuleNotFoundException, DuplicateModuleException {
        Course course = new Course("C1", "Curso 1");
        coursesManager.addCourse(course);

        Module module = new Module("M1", "Módulo 1");
        coursesManager.addModule("C1", module);

        Module updatedModule = new Module("M1", "Módulo actualizado");
        coursesManager.updateModule("C1", "M1", updatedModule);

        Module found = coursesManager.findCourse("C1").getModules().get(0);
        assertEquals("Módulo actualizado", found.getTitle());
    }

    @Test /// elimina modulo correctamente
    void deleteModule() throws CourseNotFoundException, ModuleNotFoundException, DuplicateModuleException {
        Course course = new Course("C1", "Curso 1");
        coursesManager.addCourse(course);

        Module module = new Module("M1", "Módulo 1");
        coursesManager.addModule("C1", module);

        coursesManager.deleteModule("C1", "M1");
        assertEquals(0, coursesManager.findCourse("C1").getModules().size());
    }

    //////////// LECCIONES  

    @Test //// añadir leccion correctamente
    void addLesson() throws CourseNotFoundException, ModuleNotFoundException, DuplicateLessonException, DuplicateModuleException {
        Course course = new Course("C1", "Curso 1");
        coursesManager.addCourse(course);

        Module module = new Module("M1", "Módulo 1");
        coursesManager.addModule("C1", module);

        Lesson lesson = new Lesson("L1", "Lección 1", "video");
        coursesManager.addLesson("C1", "M1", lesson);

        assertEquals(1, coursesManager.findCourse("C1").getModules().get(0).getLessons().size());
        assertEquals("L1", coursesManager.findCourse("C1").getModules().get(0).getLessons().get(0).getId());
    }

    @Test /// al añadir leccion duplicada lanza excepcion
    void throwWhenDuplicateLesson() throws CourseNotFoundException, ModuleNotFoundException, DuplicateLessonException, DuplicateModuleException {
        Course course = new Course("C1", "Curso 1");
        coursesManager.addCourse(course);

        Module module = new Module("M1", "Módulo 1");
        coursesManager.addModule("C1", module);

        Lesson lesson = new Lesson("L1", "Lección 1", "video");
        coursesManager.addLesson("C1", "M1", lesson);

        assertThrows(DuplicateLessonException.class, () -> {
            coursesManager.addLesson("C1", "M1", new Lesson("L1", "Lección duplicada", "texto"));
        });
    }

    @Test  /// actualizar leccion correctamente
    void updateLesson() throws CourseNotFoundException, ModuleNotFoundException, DuplicateLessonException, DuplicateModuleException, LessonNotFoundException {
        Course course = new Course("C1", "Curso 1");
        coursesManager.addCourse(course);

        Module module = new Module("M1", "Módulo 1");
        coursesManager.addModule("C1", module);

        Lesson lesson = new Lesson("L1", "Lección 1", "video");
        coursesManager.addLesson("C1", "M1", lesson);

        Lesson updatedLesson = new Lesson("L1", "Lección actualizada", "texto");
        coursesManager.updateLesson("C1", "M1", "L1", updatedLesson);

        Lesson found = coursesManager.findCourse("C1").getModules().get(0).getLessons().get(0);
        assertEquals("Lección actualizada", found.getTitle());
        assertEquals("texto", found.getType());
    }

    @Test /// eliminar leccion correctamente
    void deleteLesson() throws CourseNotFoundException, ModuleNotFoundException, DuplicateLessonException, DuplicateModuleException, LessonNotFoundException {
        Course course = new Course("C1", "Curso 1");
        coursesManager.addCourse(course);

        Module module = new Module("M1", "Módulo 1");
        coursesManager.addModule("C1", module);

        Lesson lesson = new Lesson("L1", "Lección 1", "video");
        coursesManager.addLesson("C1", "M1", lesson);

        coursesManager.deleteLesson("C1", "M1", "L1");
        assertEquals(0, coursesManager.findCourse("C1").getModules().get(0).getLessons().size());
>>>>>>> ce4c03f78796e33d91b87ff4455f45f1748f3a7a
    }
}
