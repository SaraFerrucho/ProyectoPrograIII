package co.edu.uptcsoft.model;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptcsoft.test.CourseNotFoundException;
import co.edu.uptcsoft.test.DuplicateLessonException;
import co.edu.uptcsoft.test.DuplicateModuleException;
import co.edu.uptcsoft.test.ModuleNotFoundException;
import co.edu.uptcsoft.test.LessonNotFoundException;

public class CoursesManager {
    private List<Course> courses;

    public CoursesManager() {
        courses = new ArrayList<>();
    }
    
    ///////////// CURSOS

    // Añadir un curso
    public void addCourse(Course course) {
        courses.add(course);
    }

    // Buscar un curso por ID
    public Course findCourse(String id) throws CourseNotFoundException {
        for (Course c : courses) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        throw new CourseNotFoundException(id);
    }

    // Actualizar un curso (versión simple)
    public void updateCourse(String courseId, Course updatedCourse) throws CourseNotFoundException {
        Course course = findCourse(courseId);
        course.setTitle(updatedCourse.getTitle());
        // Aquí puedes agregar más actualizaciones si el modelo crece
    }

    // Eliminar un curso
    public void deleteCourse(String courseId) throws CourseNotFoundException {
        Course course = findCourse(courseId);
        courses.remove(course);
    }

    //////////// MÓDULOS

    // Añadir un módulo a un curso
    public void addModule(String courseId, Module module)
            throws DuplicateModuleException, CourseNotFoundException {
        Course course = findCourse(courseId);

        for (Module m : course.getModules()) {
            if (m.getId().equals(module.getId())) {
                throw new DuplicateModuleException(module.getId());
            }
        }

        course.addModule(module);
    }

    // Buscar un módulo dentro de un curso
    private Module findModule(Course course, String moduleId) throws ModuleNotFoundException {
        for (Module m : course.getModules()) {
            if (m.getId().equals(moduleId)) {
                return m;
            }
        }
        throw new ModuleNotFoundException("Módulo no encontrado: " + moduleId);
    }

    // Actualizar un módulo
    public void updateModule(String courseId, String moduleId, Module updatedModule)
            throws CourseNotFoundException, ModuleNotFoundException {
        Course course = findCourse(courseId);
        Module module = findModule(course, moduleId);
        module.setTitle(updatedModule.getTitle());
    }

    // Eliminar un módulo
    public void deleteModule(String courseId, String moduleId)
            throws CourseNotFoundException, ModuleNotFoundException {
        Course course = findCourse(courseId);
        Module module = findModule(course, moduleId);
        course.getModules().remove(module);
    }

    /////////// LECCIONES

    // Añadir una lección a un módulo dentro de un curso
    public void addLesson(String courseId, String moduleId, Lesson lesson)
            throws CourseNotFoundException, ModuleNotFoundException, DuplicateLessonException {
        Course course = findCourse(courseId);
        Module module = findModule(course, moduleId);

        for (Lesson l : module.getLessons()) {
            if (l.getId().equals(lesson.getId())) {
                throw new DuplicateLessonException(lesson.getId());
            }
        }

        module.addLesson(lesson);
    }

    // Buscar una lección dentro de un módulo
    private Lesson findLesson(Module module, String lessonId) throws LessonNotFoundException {
        for (Lesson l : module.getLessons()) {
            if (l.getId().equals(lessonId)) {
                return l;
            }
        }
        throw new LessonNotFoundException("Lección no encontrada: " + lessonId);
    }

    // Actualizar una lección
    public void updateLesson(String courseId, String moduleId, String lessonId, Lesson updatedLesson)
            throws CourseNotFoundException, ModuleNotFoundException, LessonNotFoundException {
        Course course = findCourse(courseId);
        Module module = findModule(course, moduleId);
        Lesson lesson = findLesson(module, lessonId);
        lesson.setTitle(updatedLesson.getTitle());
        lesson.setType(updatedLesson.getType());
    }

    // Eliminar una lección
    public void deleteLesson(String courseId, String moduleId, String lessonId)
            throws CourseNotFoundException, ModuleNotFoundException, LessonNotFoundException {
        Course course = findCourse(courseId);
        Module module = findModule(course, moduleId);
        Lesson lesson = findLesson(module, lessonId);
        module.getLessons().remove(lesson);
    }

    // Devuelve un String con la estructura de cursos, módulos y lecciones en forma
    // de árbol
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

    // Obtener la lista completa de cursos (inmutable para proteger estado interno)
    public List<Course> getCourses() {
        return List.copyOf(courses);
    }

    public void setCourses(List<Course> newCourses) {
        courses.clear();
        courses.addAll(newCourses);
    }

}
