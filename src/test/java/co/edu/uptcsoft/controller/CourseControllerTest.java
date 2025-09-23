package co.edu.uptcsoft.controller;

import co.edu.uptcsoft.test.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseControllerTest {

    private CourseController controller;

    @BeforeEach
    void setUp() {
        controller = new CourseController();
    }

    /////// CURSOS

    @Test
    void addCourseSuccessfully() throws DuplicateCourseException {
        controller.addCourse("C1", "Curso 1");
        assertEquals(1, controller.getCourses().size());
        assertEquals("C1", controller.getCourses().get(0).getId());
    }

    @Test
    void addDuplicateCourseThrows() throws DuplicateCourseException {
        controller.addCourse("C1", "Curso 1");
        DuplicateCourseException ex = assertThrows(DuplicateCourseException.class, () -> {
            controller.addCourse("C1", "Curso Duplicado");
        });
        assertTrue(ex.getMessage().contains("C1"));
    }

    @Test
    void updateCourseSuccessfully() throws Exception {
        controller.addCourse("C1", "Curso 1");
        controller.updateCourse("C1", "Curso 1 Actualizado");
        assertEquals("Curso 1 Actualizado", controller.getCourses().get(0).getTitle());
    }

    @Test
    void updateNonExistentCourseThrows() {
        assertThrows(CourseNotFoundException.class, () -> {
            controller.updateCourse("NoExiste", "Nuevo Título");
        });
    }

    @Test
    void deleteCourseSuccessfully() throws Exception {
        controller.addCourse("C1", "Curso 1");
        controller.deleteCourse("C1");
        assertEquals(0, controller.getCourses().size());
    }

    @Test
    void deleteNonExistentCourseThrows() {
        assertThrows(CourseNotFoundException.class, () -> {
            controller.deleteCourse("NoExiste");
        });
    }

    //////// MODULOS

    @Test
    void addModuleSuccessfully() throws Exception {
        controller.addCourse("C1", "Curso 1");
        controller.addModule("C1", "M1", "Módulo 1");
        assertEquals(1, controller.getCourses().get(0).getModules().size());
        assertEquals("M1", controller.getCourses().get(0).getModules().get(0).getId());
    }

    @Test
    void addDuplicateModuleThrows() throws Exception {
        controller.addCourse("C1", "Curso 1");
        controller.addModule("C1", "M1", "Módulo 1");
        DuplicateModuleException ex = assertThrows(DuplicateModuleException.class, () -> {
            controller.addModule("C1", "M1", "Módulo Duplicado");
        });
        assertTrue(ex.getMessage().contains("M1"));
    }

    @Test
    void addModuleToNonExistentCourseThrows() {
        assertThrows(CourseNotFoundException.class, () -> {
            controller.addModule("NoExiste", "M1", "Módulo 1");
        });
    }

    @Test
    void updateModuleSuccessfully() throws Exception {
        controller.addCourse("C1", "Curso 1");
        controller.addModule("C1", "M1", "Módulo 1");
        controller.updateModule("C1", "M1", "Módulo 1 Actualizado");
        assertEquals("Módulo 1 Actualizado", controller.getCourses().get(0).getModules().get(0).getTitle());
    }

    @Test
    void updateNonExistentModuleThrows() throws Exception {
        controller.addCourse("C1", "Curso 1");
        assertThrows(ModuleNotFoundException.class, () -> {
            controller.updateModule("C1", "NoExiste", "Nuevo Título");
        });
    }

    @Test
    void deleteModuleSuccessfully() throws Exception {
        controller.addCourse("C1", "Curso 1");
        controller.addModule("C1", "M1", "Módulo 1");
        controller.deleteModule("C1", "M1");
        assertEquals(0, controller.getCourses().get(0).getModules().size());
    }

    @Test
    void deleteNonExistentModuleThrows() throws Exception {
        controller.addCourse("C1", "Curso 1");
        assertThrows(ModuleNotFoundException.class, () -> {
            controller.deleteModule("C1", "NoExiste");
        });
    }

    ///// LECCIONES

    @Test
    void addLessonSuccessfully() throws Exception {
        controller.addCourse("C1", "Curso 1");
        controller.addModule("C1", "M1", "Módulo 1");
        controller.addLesson("C1", "M1", "L1", "Lección 1", "Video");
        assertEquals(1, controller.getCourses().get(0).getModules().get(0).getLessons().size());
        assertEquals("L1", controller.getCourses().get(0).getModules().get(0).getLessons().get(0).getId());
    }

    @Test
    void addDuplicateLessonThrows() throws Exception {
        controller.addCourse("C1", "Curso 1");
        controller.addModule("C1", "M1", "Módulo 1");
        controller.addLesson("C1", "M1", "L1", "Lección 1", "Video");
        DuplicateLessonException ex = assertThrows(DuplicateLessonException.class, () -> {
            controller.addLesson("C1", "M1", "L1", "Lección Duplicada", "Video");
        });
        assertTrue(ex.getMessage().contains("L1"));
    }

    @Test
    void addLessonToNonExistentModuleThrows() throws Exception {
        controller.addCourse("C1", "Curso 1");
        assertThrows(ModuleNotFoundException.class, () -> {
            controller.addLesson("C1", "NoExiste", "L1", "Lección 1", "Video");
        });
    }

    @Test
    void updateLessonSuccessfully() throws Exception {
        controller.addCourse("C1", "Curso 1");
        controller.addModule("C1", "M1", "Módulo 1");
        controller.addLesson("C1", "M1", "L1", "Lección 1", "Video");
        controller.updateLesson("C1", "M1", "L1", "Lección 1 Actualizada", "Texto");
        assertEquals("Lección 1 Actualizada", controller.getCourses().get(0).getModules().get(0).getLessons().get(0).getTitle());
        assertEquals("Texto", controller.getCourses().get(0).getModules().get(0).getLessons().get(0).getType());
    }

    @Test
    void updateNonExistentLessonThrows() throws Exception {
        controller.addCourse("C1", "Curso 1");
        controller.addModule("C1", "M1", "Módulo 1");
        assertThrows(LessonNotFoundException.class, () -> {
            controller.updateLesson("C1", "M1", "NoExiste", "Nuevo Título", "Texto");
        });
    }

    @Test
    void deleteLessonSuccessfully() throws Exception {
        controller.addCourse("C1", "Curso 1");
        controller.addModule("C1", "M1", "Módulo 1");
        controller.addLesson("C1", "M1", "L1", "Lección 1", "Video");
        controller.deleteLesson("C1", "M1", "L1");
        assertEquals(0, controller.getCourses().get(0).getModules().get(0).getLessons().size());
    }

    @Test
    void deleteNonExistentLessonThrows() throws Exception {
        controller.addCourse("C1", "Curso 1");
        controller.addModule("C1", "M1", "Módulo 1");
        assertThrows(LessonNotFoundException.class, () -> {
            controller.deleteLesson("C1", "M1", "NoExiste");
        });
    }
}
