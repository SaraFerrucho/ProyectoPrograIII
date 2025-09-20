package co.edu.uptcsoft.controller;

import co.edu.uptcsoft.model.Module;
import co.edu.uptcsoft.model.Lesson;
import co.edu.uptcsoft.model.Course;
import co.edu.uptcsoft.model.CoursesManager;
import co.edu.uptcsoft.persistence.CourseDAO;

import java.util.List;

public class CourseController {
    private CoursesManager courseManager;
    private CourseDAO courseDAO;

    public CourseController() {
        courseManager = new CoursesManager();
        courseDAO = new CourseDAO();
    }

    // CRUD Cursos
    public void addCourse(Course course) {
        courseManager.addCourse(course);
    }

    public void addModule(String courseId, Module module) {
        courseManager.addModule(courseId, module);
    }

    public void addLesson(String courseId, String moduleId, Lesson lesson) {
        courseManager.addLesson(courseId, moduleId, lesson);
    }

    public void printCourses() {
        courseManager.printCourses();
    }

    public void saveData() {
        courseDAO.save(courseManager.getCourses());
    }

    public void loadData() {
        List<Course> loadedCourses = courseDAO.load();
        if (loadedCourses != null) {
            courseManager.getCourses().clear();
            courseManager.getCourses().addAll(loadedCourses);
        }
    }
}
