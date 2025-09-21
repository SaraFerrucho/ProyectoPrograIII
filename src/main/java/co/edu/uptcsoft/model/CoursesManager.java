package co.edu.uptcsoft.model;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptcsoft.test.CourseNotFoundException;
import co.edu.uptcsoft.test.DuplicateLessonException;
import co.edu.uptcsoft.test.DuplicateModuleException;
import co.edu.uptcsoft.test.LessonNotFoundException;
import co.edu.uptcsoft.test.ModuleNotFoundException;

public class CoursesManager {
    private final List<Course> courses;

    public CoursesManager() {
        this.courses = new ArrayList<>();
    }

    // ===== Cursos =====
    public void addCourse(Course course) { courses.add(course); }

    public Course findCourse(String id) throws CourseNotFoundException {
        for (Course c : courses) if (c.getId().equals(id)) return c;
        throw new CourseNotFoundException(id);
    }

    public void updateCourse(String courseId, Course updatedCourse) throws CourseNotFoundException {
        Course c = findCourse(courseId);
        c.setTitle(updatedCourse.getTitle());
    }

    public void deleteCourse(String courseId) throws CourseNotFoundException {
        Course c = findCourse(courseId);
        courses.remove(c);
    }

    // ===== Módulos =====
    public void addModule(String courseId, Module module)
            throws DuplicateModuleException, CourseNotFoundException {
        Course course = findCourse(courseId);
        for (Module m : course.getModules()) {
            if (m.getId().equals(module.getId()))
                throw new DuplicateModuleException(module.getId());
        }
        course.addModule(module);
    }

    private Module findModule(Course course, String moduleId) throws ModuleNotFoundException {
        for (Module m : course.getModules()) if (m.getId().equals(moduleId)) return m;
        throw new ModuleNotFoundException("Módulo no encontrado: " + moduleId);
    }

    public void updateModule(String courseId, String moduleId, Module updatedModule)
            throws CourseNotFoundException, ModuleNotFoundException {
        Course course = findCourse(courseId);
        Module module = findModule(course, moduleId);
        module.setTitle(updatedModule.getTitle());
    }

    public void deleteModule(String courseId, String moduleId)
            throws CourseNotFoundException, ModuleNotFoundException {
        Course course = findCourse(courseId);
        Module module = findModule(course, moduleId);
        course.getModules().remove(module);
    }

    // ===== Lecciones =====
    public void addLesson(String courseId, String moduleId, Lesson lesson)
            throws CourseNotFoundException, ModuleNotFoundException, DuplicateLessonException {
        Course course = findCourse(courseId);
        Module module = findModule(course, moduleId);
        for (Lesson l : module.getLessons()) {
            if (l.getId().equals(lesson.getId()))
                throw new DuplicateLessonException(lesson.getId());
        }
        module.addLesson(lesson);
    }

    private Lesson findLesson(Module module, String lessonId) throws LessonNotFoundException {
        for (Lesson l : module.getLessons()) if (l.getId().equals(lessonId)) return l;
        throw new LessonNotFoundException("Lección no encontrada: " + lessonId);
    }

    public void updateLesson(String courseId, String moduleId, String lessonId, Lesson updatedLesson)
            throws CourseNotFoundException, ModuleNotFoundException, LessonNotFoundException {
        Course course = findCourse(courseId);
        Module module = findModule(course, moduleId);
        Lesson lesson = findLesson(module, lessonId);
        lesson.setTitle(updatedLesson.getTitle());
        lesson.setType(updatedLesson.getType());
    }

    public void deleteLesson(String courseId, String moduleId, String lessonId)
            throws CourseNotFoundException, ModuleNotFoundException, LessonNotFoundException {
        Course course = findCourse(courseId);
        Module module = findModule(course, moduleId);
        Lesson lesson = findLesson(module, lessonId);
        module.getLessons().remove(lesson);
    }

    // ===== Vistas =====
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

    public List<Course> getCourses() { return List.copyOf(courses); }
    public void setCourses(List<Course> newCourses) { courses.clear(); courses.addAll(newCourses); }

    // ===== Helpers para PROGRESO =====
    public List<String> courseIds() {
        List<String> ids = new ArrayList<>();
        for (Course c : courses) ids.add(c.getId());
        return ids;
    }

    public List<String> moduleIdsOfCourse(String courseId) throws CourseNotFoundException {
        Course c = findCourse(courseId);
        List<String> ids = new ArrayList<>();
        for (Module m : c.getModules()) ids.add(m.getId());
        return ids;
    }

    /** nodeId = curso → todas sus lecciones; nodeId = módulo → sus lecciones. */
    public List<String> lessonIdsUnder(String nodeId) {
        List<String> ids = new ArrayList<>();
        // curso
        for (Course c : courses) {
            if (c.getId().equals(nodeId)) {
                for (Module m : c.getModules())
                    for (Lesson l : m.getLessons())
                        ids.add(l.getId());
                return ids;
            }
        }
        // módulo
        for (Course c : courses) {
            for (Module m : c.getModules()) {
                if (m.getId().equals(nodeId)) {
                    for (Lesson l : m.getLessons()) ids.add(l.getId());
                    return ids;
                }
            }
        }
        return ids;
    }
}
