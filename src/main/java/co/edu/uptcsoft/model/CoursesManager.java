package co.edu.uptcsoft.model;

import co.edu.uptcsoft.test.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CoursesManager {
    private List<TreeNode<Course>> courses; // raiz

    public CoursesManager() {
        courses = new ArrayList<>();
    }

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
        }
        return sb.toString();
    }

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
    }

}
