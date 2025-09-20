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

    public void addModule(String courseId, Module module) {
        Course course = findCourse(courseId);
        if (course != null) {
            course.addModule(module);
        } else {
            System.out.println("Curso no encontrado con ID: " + courseId);
        }
    }

    public void addLesson(String courseId, String moduleId, Lesson lesson) {
        Course course = findCourse(courseId);
        if (course != null) {
            for (Module mod : course.getModules()) {
                if (mod.getId().equals(moduleId)) {
                    mod.addLesson(lesson);
                    return;
                }
            }
        }
        System.out.println("Módulo no encontrado con ID: " + moduleId);
    }

    public Course findCourse(String id) {
        for (Course c : courses) {
            if (c.getId().equals(id)) return c;
        }
        return null;
    }

    public void printCourses() {
        for (Course course : courses) {
            System.out.println(course);
            for (Module module : course.getModules()) {
                System.out.println("  " + module);
                for (Lesson lesson : module.getLessons()) {
                    System.out.println("    " + lesson);
                }
            }
        }
    }

    public List<Course> getCourses() {
        return courses;
    }
}
