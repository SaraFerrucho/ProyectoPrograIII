package co.edu.uptcsoft.controller;

import co.edu.uptcsoft.model.*;
import co.edu.uptcsoft.model.Module;
import co.edu.uptcsoft.persistence.CourseDAO;

public class CourseController {
    private CourseManager courseManager;
    private CourseDAO courseDAO;

    public CourseController() {
        courseManager = new CourseManager();
        courseDAO = new CourseDAO();
    }

    // CRUD Cursos
    public void addCourse(Course course) {
        courseManager.addCourse(course);
    }

    public void addModule(String courseId, Module module) {
        courseManager.addModule(courseId, module);
    }

    public void addLesson(String moduleId, Lesson lesson) {
        courseManager.addLesson(moduleId, lesson);
    }

    public void printTree() {
        courseManager.printTree();
    }

    // Persistencia
    public void saveData() {
        courseDAO.save(courseManager.getRoot());
    }

    public void loadData() {
        courseManager.setRoot(courseDAO.load());
    }
}
