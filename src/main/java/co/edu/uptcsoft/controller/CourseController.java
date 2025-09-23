package co.edu.uptcsoft.controller;

<<<<<<< HEAD
import co.edu.uptcsoft.model.*;
import co.edu.uptcsoft.model.Module;
import co.edu.uptcsoft.persistence.CourseDao;
import co.edu.uptcsoft.test.*;
=======
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
>>>>>>> ce4c03f78796e33d91b87ff4455f45f1748f3a7a

import java.util.List;

public class CourseController {
    private CoursesManager courseManager;
<<<<<<< HEAD
    private CourseDao courseDAO;

    public CourseController() {
        courseManager = new CoursesManager();
        courseDAO = new CourseDao();
    }

=======
    private CourseDAO courseDAO;

    public CourseController() {
        courseManager = new CoursesManager();
        courseDAO = new CourseDAO();
    }

    // Métodos privados para validación

>>>>>>> ce4c03f78796e33d91b87ff4455f45f1748f3a7a
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

<<<<<<< HEAD
    public co.edu.uptcsoft.model.CoursesManager getCoursesManager() {
        return courseManager;
    }
    // CURSOS
=======
    ///////////////// CURSOS
>>>>>>> ce4c03f78796e33d91b87ff4455f45f1748f3a7a

    public void addCourse(String newCourseId, String newCourseTitle) throws DuplicateCourseException {
        validateId(newCourseId, "ID del curso");
        validateTitle(newCourseTitle, "Título del curso");

<<<<<<< HEAD
=======
        for (Course c : courseManager.getCourses()) {
            if (c.getId().equals(newCourseId)) {
                throw new DuplicateCourseException(newCourseId);
            }
        }
>>>>>>> ce4c03f78796e33d91b87ff4455f45f1748f3a7a
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

<<<<<<< HEAD
    // MÓDULOS

    public void addModule(String courseId, String newModuleId, String newModuleTitle)
            throws DuplicateModuleException, CourseNotFoundException {
        validateId(courseId, "ID del curso");
=======
    ///////// MÓDULOS

    public void addModule(String courseId, String newModuleId, String newModuleTitle)
            throws DuplicateModuleException, CourseNotFoundException {
>>>>>>> ce4c03f78796e33d91b87ff4455f45f1748f3a7a
        validateId(newModuleId, "ID del módulo");
        validateTitle(newModuleTitle, "Título del módulo");

        Module newModule = new Module(newModuleId, newModuleTitle);
        courseManager.addModule(courseId, newModule);
        saveData();
    }

    public void updateModule(String courseId, String moduleId, String newTitle)
            throws CourseNotFoundException, ModuleNotFoundException {
<<<<<<< HEAD
        validateId(courseId, "ID del curso");
=======
>>>>>>> ce4c03f78796e33d91b87ff4455f45f1748f3a7a
        validateId(moduleId, "ID del módulo");
        validateTitle(newTitle, "Título del módulo");

        Module updated = new Module(moduleId, newTitle);
        courseManager.updateModule(courseId, moduleId, updated);
        saveData();
    }

    public void deleteModule(String courseId, String moduleId)
            throws CourseNotFoundException, ModuleNotFoundException {
<<<<<<< HEAD
        validateId(courseId, "ID del curso");
=======
>>>>>>> ce4c03f78796e33d91b87ff4455f45f1748f3a7a
        validateId(moduleId, "ID del módulo");

        courseManager.deleteModule(courseId, moduleId);
        saveData();
    }

<<<<<<< HEAD
    // LECCIONES
=======
    /////////// LECCIONES
>>>>>>> ce4c03f78796e33d91b87ff4455f45f1748f3a7a

    public void addLesson(String courseId, String moduleId, String newLessonId,
            String newLessonTitle, String newLessonType)
            throws DuplicateLessonException, CourseNotFoundException, ModuleNotFoundException {
<<<<<<< HEAD
        validateId(courseId, "ID del curso");
        validateId(moduleId, "ID del módulo");
        validateId(newLessonId, "ID de la lección");
=======
>>>>>>> ce4c03f78796e33d91b87ff4455f45f1748f3a7a
        validateTitle(newLessonTitle, "Título de la lección");
        validateTitle(newLessonType, "Tipo de la lección");

        Lesson newLesson = new Lesson(newLessonId, newLessonTitle, newLessonType);
        courseManager.addLesson(courseId, moduleId, newLesson);
        saveData();
    }

    public void updateLesson(String courseId, String moduleId, String lessonId,
            String newTitle, String newType)
            throws CourseNotFoundException, ModuleNotFoundException, LessonNotFoundException {
<<<<<<< HEAD
        validateId(courseId, "ID del curso");
        validateId(moduleId, "ID del módulo");
        validateId(lessonId, "ID de la lección");
=======
>>>>>>> ce4c03f78796e33d91b87ff4455f45f1748f3a7a
        validateTitle(newTitle, "Título de la lección");
        validateTitle(newType, "Tipo de la lección");

        Lesson updated = new Lesson(lessonId, newTitle, newType);
        courseManager.updateLesson(courseId, moduleId, lessonId, updated);
        saveData();
    }

    public void deleteLesson(String courseId, String moduleId, String lessonId)
            throws CourseNotFoundException, ModuleNotFoundException, LessonNotFoundException {
<<<<<<< HEAD
        validateId(courseId, "ID del curso");
        validateId(moduleId, "ID del módulo");
=======
>>>>>>> ce4c03f78796e33d91b87ff4455f45f1748f3a7a
        validateId(lessonId, "ID de la lección");

        courseManager.deleteLesson(courseId, moduleId, lessonId);
        saveData();
    }

    public String getCoursesAsString() {
        return courseManager.getCoursesAsString();
    }

<<<<<<< HEAD
    public List<TreeNode<Course>> getCourses() {
=======
    public List<Course> getCourses() {
>>>>>>> ce4c03f78796e33d91b87ff4455f45f1748f3a7a
        return courseManager.getCourses();
    }

    public void saveData() {
        courseDAO.save(courseManager.getCourses());
    }

    public void loadData() {
<<<<<<< HEAD
        List<TreeNode<Course>> loadedCourses = courseDAO.load();
=======
        List<Course> loadedCourses = courseDAO.load();
>>>>>>> ce4c03f78796e33d91b87ff4455f45f1748f3a7a
        if (loadedCourses != null) {
            courseManager.setCourses(loadedCourses);
        }
    }
}
