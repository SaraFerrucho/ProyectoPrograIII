package co.edu.uptcsoft.model;

import java.util.ArrayList;
import java.util.List;

public class CoursesManager {
    private List<Course> courses;

    public CoursesManager() {
        courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public boolean addModule(String courseId, Module module) {
        Course course = findCourse(courseId);
        if (course != null) {
            course.addModule(module);
            return true;
        } else {
            return false;
        }
    }

    public boolean addLesson(String courseId, String moduleId, Lesson lesson) {
        Course course = findCourse(courseId);
        if (course != null) {
            for (Module mod : course.getModules()) {
                if (mod.getId().equals(moduleId)) {
                    mod.addLesson(lesson);
                    return true;
                }
            }
        }
        return false;
    }

    public Course findCourse(String id) {
        for (Course c : courses) {
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }

    // Devuelve un String de los cursos como un arbol
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

    // Devuelve la lista completa (para poblar tablas en UI)
    public List<Course> getCourses() {
        return courses;
    }
}
