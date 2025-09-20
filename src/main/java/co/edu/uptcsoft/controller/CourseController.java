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
    public void addCourse(String newCourseId, String newCourseTitle) {
        Course newCourse = new Course(newCourseId, newCourseTitle);
        courseManager.addCourse(newCourse);
    }

    public void addModule(String courseId, String newModuleId, String newCurseTitle) {
        Module newModule = new Module(newModuleId, newCurseTitle);
        courseManager.addModule(courseId, newModule);
    }

    public void addLesson(String courseId, String moduleId, String newLessoId, String newLessonTitle,
            String newLessonType) {
        Lesson newLesson = new Lesson(newLessoId, newLessonTitle, newLessonType);
        courseManager.addLesson(courseId, moduleId, newLesson);
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
            courseManager.getCourses().clear();
            courseManager.getCourses().addAll(loadedCourses);
        }
    }
}
