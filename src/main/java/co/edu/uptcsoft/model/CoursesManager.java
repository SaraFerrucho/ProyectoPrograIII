package co.edu.uptcsoft.model;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptcsoft.test.CourseNotFoundException;
import co.edu.uptcsoft.test.DuplicateLessonException;
import co.edu.uptcsoft.test.DuplicateModuleException;
import co.edu.uptcsoft.test.ModuleNotFoundException;

public class CoursesManager {
    private List<Course> courses;

    public CoursesManager() {
        courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addModule(String courseId, Module module)
            throws DuplicateModuleException, CourseNotFoundException {
        Course course = findCourse(courseId); // lanza excepción si no existe

        for (Module m : course.getModules()) {
            if (m.getId().equals(module.getId())) {
                throw new DuplicateModuleException(module.getId());
            }
        }

        course.addModule(module);
    }

    public void addLesson(String courseId, String moduleId, Lesson lesson)
            throws CourseNotFoundException, ModuleNotFoundException, DuplicateLessonException {
        Course course = findCourse(courseId); // lanza excepción si no existe

        for (Module mod : course.getModules()) {
            if (mod.getId().equals(moduleId)) {
                for (Lesson l : mod.getLessons()) {
                    if (l.getId().equals(lesson.getId())) {
                        throw new DuplicateLessonException(lesson.getId());
                    }
                }
                mod.addLesson(lesson);
                return;
            }
        }

        throw new ModuleNotFoundException(moduleId);
    }

    public Course findCourse(String id) throws CourseNotFoundException {
        for (Course c : courses) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        throw new CourseNotFoundException(id);
    }

    // Devuelve un String de los cursos como un árbol
    public String getCoursesAsString() {
        StringBuilder sb = new StringBuilder();
        for (Course course : courses) {
            sb.append(course).append("\n");
            for (Module module : course.getModules()) {
                sb.append("  ").append(module).append("\n");
                for (Lesson lesson : module.getLessons()) {
                    sb.append("    ").append(lesson).append("\n");
                }
            }
        }
        return sb.toString();
    }

    public List<Course> getCourses() {
        return courses;
    }
}
