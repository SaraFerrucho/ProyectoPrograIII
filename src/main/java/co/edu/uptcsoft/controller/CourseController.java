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
import co.edu.uptcsoft.test.ModuleNotFoundException;

import java.util.List;

public class CourseController {
    private CoursesManager courseManager;
    private CourseDAO courseDAO;

    public CourseController() {
        courseManager = new CoursesManager();
        courseDAO = new CourseDAO();
    }

    public void addCourse(String newCourseId, String newCourseTitle) throws DuplicateCourseException {
        Course newCourse = new Course(newCourseId, newCourseTitle);
        for (Course c : courseManager.getCourses()) {
            if (c.getId().equals(newCourseId)) {
                throw new DuplicateCourseException(newCourseId);
            }
        }
        courseManager.addCourse(newCourse);
    }

    public void addModule(String courseId, String newModuleId, String newCurseTitle)
            throws DuplicateModuleException, CourseNotFoundException {
        Module newModule = new Module(newModuleId, newCurseTitle);
        courseManager.addModule(courseId, newModule);
    }

    public void addLesson(String courseId, String moduleId, String newLessonId,
            String newLessonTitle, String newLessonType)
            throws DuplicateLessonException, CourseNotFoundException, ModuleNotFoundException {
        Lesson newLesson = new Lesson(newLessonId, newLessonTitle, newLessonType);
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
