package co.edu.uptcsoft.model;

<<<<<<< HEAD
import co.edu.uptcsoft.test.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CoursesManager {
    private List<TreeNode<Course>> courses; // raiz
=======
import java.util.ArrayList;
import java.util.List;

import co.edu.uptcsoft.test.CourseNotFoundException;
import co.edu.uptcsoft.test.DuplicateLessonException;
import co.edu.uptcsoft.test.DuplicateModuleException;
import co.edu.uptcsoft.test.ModuleNotFoundException;
import co.edu.uptcsoft.test.LessonNotFoundException;

public class CoursesManager {
    private List<Course> courses;
>>>>>>> ce4c03f78796e33d91b87ff4455f45f1748f3a7a

    public CoursesManager() {
        courses = new ArrayList<>();
    }
<<<<<<< HEAD

    public List<TreeNode<Course>> getCourses() {
        return courses;
    }

    public void setCourses(List<TreeNode<Course>> courses) {
        this.courses = courses;
    }

    // Agregar curso
    public void addCourse(Course course) throws DuplicateCourseException {
        if (findCourseNodeById(course.getId()).isPresent()) {
            throw new DuplicateCourseException(course.getId());
        }
        courses.add(new TreeNode<>(course));
    }

    // Actualizar curso
    public void updateCourse(String courseId, Course updated) throws CourseNotFoundException {
        for (TreeNode<Course> node : courses) {
            if (node.getData().getId().equals(courseId)) {
                node.getData().setTitle(updated.getTitle());
                return; // salimos porque ya lo encontramos
            }
        }
        throw new CourseNotFoundException(courseId); // si no se encontró
    }

    // Eliminar curso
    public void deleteCourse(String courseId) throws CourseNotFoundException {
        for (TreeNode<Course> node : courses) {
            if (node.getData().getId().equals(courseId)) {
                courses.remove(node);
                return; // salimos porque ya eliminamos
            }
        }
        throw new CourseNotFoundException(courseId); // si no se encontró
    }

    // Agregar módulo a curso
    public void addModule(String courseId, Module module) throws DuplicateModuleException, CourseNotFoundException {
        TreeNode<Course> courseNode = findCourseNodeById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        if (findModuleNodeById(courseNode, module.getId()).isPresent()) {
            throw new DuplicateModuleException(module.getId());
        }
        courseNode.addChild(new TreeNode<>(module));
    }

    // Actualizar módulo
    public void updateModule(String courseId, String moduleId, Module updated)
            throws CourseNotFoundException, ModuleNotFoundException {
        TreeNode<Course> courseNode = findCourseNodeById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        TreeNode<?> moduleNode = findModuleNodeById(courseNode, moduleId)
                .orElseThrow(() -> new ModuleNotFoundException(moduleId));

        ((Module) moduleNode.getData()).setTitle(updated.getTitle());
    }

    // Eliminar módulo
    public void deleteModule(String courseId, String moduleId) throws CourseNotFoundException, ModuleNotFoundException {
        TreeNode<Course> courseNode = findCourseNodeById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        TreeNode<?> moduleNode = findModuleNodeById(courseNode, moduleId)
                .orElseThrow(() -> new ModuleNotFoundException(moduleId));
        courseNode.getChildren().remove(moduleNode);
    }

    // Agregar lección a módulo
    public void addLesson(String courseId, String moduleId, Lesson lesson)
            throws DuplicateLessonException, CourseNotFoundException, ModuleNotFoundException {
        TreeNode<Course> courseNode = findCourseNodeById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        TreeNode<?> moduleNode = findModuleNodeById(courseNode, moduleId)
                .orElseThrow(() -> new ModuleNotFoundException(moduleId));

        if (findLessonNodeById(moduleNode, lesson.getId()).isPresent()) {
            throw new DuplicateLessonException(lesson.getId());
        }
        moduleNode.addChild(new TreeNode<>(lesson));
    }

    // Actualizar lección
    public void updateLesson(String courseId, String moduleId, String lessonId, Lesson updated)
            throws CourseNotFoundException, ModuleNotFoundException, LessonNotFoundException {
        TreeNode<Course> courseNode = findCourseNodeById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        TreeNode<?> moduleNode = findModuleNodeById(courseNode, moduleId)
                .orElseThrow(() -> new ModuleNotFoundException(moduleId));
        TreeNode<?> lessonNode = findLessonNodeById(moduleNode, lessonId)
                .orElseThrow(() -> new LessonNotFoundException(lessonId));

        Lesson lesson = (Lesson) lessonNode.getData();
        lesson.setTitle(updated.getTitle());
        lesson.setType(updated.getType());
    }

    // Eliminar lección
    public void deleteLesson(String courseId, String moduleId, String lessonId)
            throws CourseNotFoundException, ModuleNotFoundException, LessonNotFoundException {
        TreeNode<Course> courseNode = findCourseNodeById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        TreeNode<?> moduleNode = findModuleNodeById(courseNode, moduleId)
                .orElseThrow(() -> new ModuleNotFoundException(moduleId));
        TreeNode<?> lessonNode = findLessonNodeById(moduleNode, lessonId)
                .orElseThrow(() -> new LessonNotFoundException(lessonId));

        moduleNode.getChildren().remove(lessonNode);
    }

    // Métodos auxiliares para búsqueda

    private Optional<TreeNode<Course>> findCourseNodeById(String courseId) {
        for (TreeNode<Course> course : courses) {
            if (course.getData().getId().equals(courseId)) {
                return Optional.of(course);
            }
        }
        return Optional.empty();
    }

    private Optional<TreeNode<?>> findModuleNodeById(TreeNode<Course> courseNode, String moduleId) {
        for (TreeNode<?> module : courseNode.getChildren()) {
            Identifiable data = (Identifiable) module.getData();
            if (data.getId().equals(moduleId)) {
                return Optional.of(module);
            }
        }
        return Optional.empty();
    }

    private Optional<TreeNode<?>> findLessonNodeById(TreeNode<?> moduleNode, String lessonId) {
        for (TreeNode<?> lesson : moduleNode.getChildren()) {
            Identifiable data = (Identifiable) lesson.getData();
            if (data.getId().equals(lessonId)) {
                return Optional.of(lesson);
            }
        }
        return Optional.empty();
    }

    //Método para imprimir árbol como String (recursivo)
    public String getCoursesAsString() {
        StringBuilder sb = new StringBuilder();
        for (TreeNode<Course> courseNode : courses) {
            printNode(courseNode, sb, 0);
=======
    
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

    // Actualizar un curso 
    public void updateCourse(String courseId, Course updatedCourse) throws CourseNotFoundException {
        Course course = findCourse(courseId);
        course.setTitle(updatedCourse.getTitle());
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

    // Devuelve un String con la estructura de cursos, módulos y lecciones en forma de árbol
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
>>>>>>> ce4c03f78796e33d91b87ff4455f45f1748f3a7a
        }
        return sb.toString();
    }

<<<<<<< HEAD
    private void printNode(TreeNode<?> node, StringBuilder sb, int level) {
        String indent = "  ".repeat(level);
        Object data = node.getData();
        if (data instanceof Course) {
            Course c = (Course) data;
            sb.append(indent).append("Curso: ").append(c.getId()).append(" - ").append(c.getTitle()).append("\n");
        } else if (data instanceof Module) {
            Module m = (Module) data;
            sb.append(indent).append("Módulo: ").append(m.getId()).append(" - ").append(m.getTitle()).append("\n");
        } else if (data instanceof Lesson) {
            Lesson l = (Lesson) data;
            sb.append(indent).append("Lección: ").append(l.getId()).append(" - ").append(l.getTitle())
                    .append(" (").append(l.getType()).append(")").append("\n");
        }
        for (TreeNode<?> child : node.getChildren()) {
            printNode(child, sb, level + 1);
        }
    }

    public Course findCourse(String courseId) throws CourseNotFoundException {
        for (TreeNode<Course> courseNode : courses) {
            if (courseNode.getData().getId().equals(courseId)) {
                return courseNode.getData();
            }
        }
        throw new CourseNotFoundException(courseId);
=======
    // Obtener la lista completa de cursos (inmutable para proteger estado interno)
    public List<Course> getCourses() {
        return List.copyOf(courses);
    }

    public void setCourses(List<Course> newCourses) {
        courses.clear();
        courses.addAll(newCourses);
>>>>>>> ce4c03f78796e33d91b87ff4455f45f1748f3a7a
    }

}
