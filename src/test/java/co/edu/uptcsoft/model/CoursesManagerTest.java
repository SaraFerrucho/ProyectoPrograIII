package co.edu.uptcsoft.model;

import co.edu.uptcsoft.test.CourseNotFoundException;
import co.edu.uptcsoft.test.DuplicateLessonException;
import co.edu.uptcsoft.test.DuplicateModuleException;
import co.edu.uptcsoft.test.LessonNotFoundException;
import co.edu.uptcsoft.test.ModuleNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoursesManagerTest {

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
    }
}
