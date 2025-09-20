package co.edu.uptcsoft.controller;

import co.edu.uptcsoft.model.Module;
import co.edu.uptcsoft.model.Lesson;
import co.edu.uptcsoft.model.Course;
import co.edu.uptcsoft.model.CoursesManager;
import co.edu.uptcsoft.persistence.CourseDAO;
import co.edu.uptcsoft.test.CourseNotFoundException;
import co.edu.uptcsoft.test.DuplicateCourseException;
import co.edu.uptcsoft.test.DuplicateLessonException;
import co.edu.uptcsoft.test.DuplicateModuleException;
import co.edu.uptcsoft.test.LessonNotFoundException;
import co.edu.uptcsoft.test.ModuleNotFoundException;

import java.util.List;

public class CourseController {
    private CoursesManager courseManager;
    private CourseDAO courseDAO;

    public CourseController() {
        courseManager = new CoursesManager();
        courseDAO = new CourseDAO();
    }

    // Métodos privados para validación

    private void validateId(String id, String fieldName) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " no puede ser nulo o vacío");
        }
    }

    private void validateTitle(String title, String fieldName) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " no puede ser nulo o vacío");
        }
    }

    ///////////////// CURSOS

    public void addCourse(String newCourseId, String newCourseTitle) throws DuplicateCourseException {
        validateId(newCourseId, "ID del curso");
        validateTitle(newCourseTitle, "Título del curso");

        for (Course c : courseManager.getCourses()) {
            if (c.getId().equals(newCourseId)) {
                throw new DuplicateCourseException(newCourseId);
            }
        }
        Course newCourse = new Course(newCourseId, newCourseTitle);
        courseManager.addCourse(newCourse);
        saveData();
    }

    public void updateCourse(String courseId, String newTitle) throws CourseNotFoundException {
        validateId(courseId, "ID del curso");
        validateTitle(newTitle, "Título del curso");

        Course updated = new Course(courseId, newTitle);
        courseManager.updateCourse(courseId, updated);
        saveData();
    }

    public void deleteCourse(String courseId) throws CourseNotFoundException {
        validateId(courseId, "ID del curso");

        courseManager.deleteCourse(courseId);
        saveData();
    }

    ///////// MÓDULOS

    public void addModule(String courseId, String newModuleId, String newModuleTitle)
            throws DuplicateModuleException, CourseNotFoundException {
        validateId(newModuleId, "ID del módulo");
        validateTitle(newModuleTitle, "Título del módulo");

        Module newModule = new Module(newModuleId, newModuleTitle);
        courseManager.addModule(courseId, newModule);
        saveData();
    }

    public void updateModule(String courseId, String moduleId, String newTitle)
            throws CourseNotFoundException, ModuleNotFoundException {
        validateId(moduleId, "ID del módulo");
        validateTitle(newTitle, "Título del módulo");

        Module updated = new Module(moduleId, newTitle);
        courseManager.updateModule(courseId, moduleId, updated);
        saveData();
    }

    public void deleteModule(String courseId, String moduleId)
            throws CourseNotFoundException, ModuleNotFoundException {
        validateId(moduleId, "ID del módulo");

        courseManager.deleteModule(courseId, moduleId);
        saveData();
    }

    /////////// LECCIONES

    public void addLesson(String courseId, String moduleId, String newLessonId,
            String newLessonTitle, String newLessonType)
            throws DuplicateLessonException, CourseNotFoundException, ModuleNotFoundException {
        validateTitle(newLessonTitle, "Título de la lección");
        validateTitle(newLessonType, "Tipo de la lección");

        Lesson newLesson = new Lesson(newLessonId, newLessonTitle, newLessonType);
        courseManager.addLesson(courseId, moduleId, newLesson);
        saveData();
    }

    public void updateLesson(String courseId, String moduleId, String lessonId,
            String newTitle, String newType)
            throws CourseNotFoundException, ModuleNotFoundException, LessonNotFoundException {
        validateTitle(newTitle, "Título de la lección");
        validateTitle(newType, "Tipo de la lección");

        Lesson updated = new Lesson(lessonId, newTitle, newType);
        courseManager.updateLesson(courseId, moduleId, lessonId, updated);
        saveData();
    }

    public void deleteLesson(String courseId, String moduleId, String lessonId)
            throws CourseNotFoundException, ModuleNotFoundException, LessonNotFoundException {
        validateId(lessonId, "ID de la lección");

        courseManager.deleteLesson(courseId, moduleId, lessonId);
        saveData();
    }

    public String getCoursesAsString() {
        return courseManager.getCoursesAsString();
    }

    public List<Course> getCourses() {
        return courseManager.getCourses();
    }

    public void saveData() {
        courseDAO.save(courseManager.getCourses());
    }

    public void loadData() {
        List<Course> loadedCourses = courseDAO.load();
        if (loadedCourses != null) {
            courseManager.setCourses(loadedCourses);
        }
    }
}
