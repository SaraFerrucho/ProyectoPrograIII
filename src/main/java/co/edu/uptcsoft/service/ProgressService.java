package co.edu.uptcsoft.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import co.edu.uptcsoft.model.Course;
import co.edu.uptcsoft.model.Lesson;
import co.edu.uptcsoft.model.Module;
import co.edu.uptcsoft.model.TreeNode;
import co.edu.uptcsoft.persistence.ProgressRepository;
import co.edu.uptcsoft.test.CourseNotFoundException;

public class ProgressService {
    private final co.edu.uptcsoft.model.CoursesManager cm;
    private final ProgressRepository repo = new ProgressRepository();

    public ProgressService(co.edu.uptcsoft.model.CoursesManager cm) {
        this.cm = cm;
    }

    // Obtiene todas las lecciones de un curso recorriendo el árbol
    private List<String> allLessonIdsOf(Course course) throws CourseNotFoundException {
        List<String> ids = new ArrayList<>();
        TreeNode<Course> courseNode = findCourseNode(course.getId());
        if (courseNode == null)
            throw new CourseNotFoundException(course.getId());

        for (TreeNode<?> moduleNode : courseNode.getChildren()) {
            Object data = moduleNode.getData();
            if (data instanceof Module) {
                for (TreeNode<?> lessonNode : moduleNode.getChildren()) {
                    Object lessonData = lessonNode.getData();
                    if (lessonData instanceof Lesson) {
                        String id = ((Lesson) lessonData).getId();
                        if (id != null)
                            ids.add(id);
                    }
                }
            }
        }
        return ids;
    }

    // Obtiene las lecciones de un módulo
    private List<String> lessonIdsOfModule(Module m) throws CourseNotFoundException {
        List<String> ids = new ArrayList<>();
        TreeNode<Course> courseNode = findCourseNodeContainingModule(m.getId());
        if (courseNode == null)
            throw new CourseNotFoundException("Curso que contiene módulo " + m.getId() + " no encontrado");

        TreeNode<?> moduleNode = findModuleNode(courseNode, m.getId());
        if (moduleNode == null)
            return ids;

        for (TreeNode<?> lessonNode : moduleNode.getChildren()) {
            Object lessonData = lessonNode.getData();
            if (lessonData instanceof Lesson) {
                String id = ((Lesson) lessonData).getId();
                if (id != null)
                    ids.add(id);
            }
        }
        return ids;
    }

    //// Acciones

    public void markLessonCompleted(int studentId, String courseId, String lessonId) {
        repo.markLesson(studentId, courseId, lessonId);
    }

    public void unmarkLesson(int studentId, String courseId, String lessonId) {
        repo.unmarkLesson(studentId, courseId, lessonId);
    }

    ///// Consultas

    public double coursePercentage(int studentId, String courseId) {
        try {
            Course c = cm.findCourse(courseId);
            List<String> all = allLessonIdsOf(c);
            if (all.isEmpty())
                return 0.0;
            Set<String> done = repo.getCompletedLessons(studentId, courseId);
            long completed = all.stream().filter(done::contains).count();
            return (completed * 100.0) / all.size();
        } catch (CourseNotFoundException e) {
            return 0.0;
        }
    }

    public Map<String, String> moduleStatuses(int studentId, String courseId) {
        Map<String, String> st = new LinkedHashMap<>();
        try {
            Course c = cm.findCourse(courseId);
            Set<String> done = repo.getCompletedLessons(studentId, courseId);
            TreeNode<Course> courseNode = findCourseNode(courseId);
            if (courseNode == null)
                return st;

            for (TreeNode<?> moduleNode : courseNode.getChildren()) {
                Object data = moduleNode.getData();
                if (data instanceof Module) {
                    Module m = (Module) data;
                    List<String> lessons = lessonIdsOfModule(m);
                    int total = lessons.size();
                    int completed = (int) lessons.stream().filter(done::contains).count();
                    String status = (total == 0) ? "Sin lecciones"
                            : (completed == 0) ? "Pendiente"
                                    : (completed == total) ? "Completado"
                                            : "En curso";
                    st.put(m.getId(), status);
                }
            }
        } catch (CourseNotFoundException e) {
        }
        return st;
    }

    public String courseProgressReport(int studentId, String courseId) {
        StringBuilder sb = new StringBuilder();
        try {
            Course c = cm.findCourse(courseId);
            double pct = coursePercentage(studentId, courseId);
            sb.append(String.format("Progreso estudiante %d en %s: %.1f%%%n",
                    studentId, courseId, pct));
            Map<String, String> perModule = moduleStatuses(studentId, courseId);
            for (Map.Entry<String, String> e : perModule.entrySet()) {
                sb.append(" - Módulo ").append(e.getKey()).append(": ")
                        .append(e.getValue()).append("\n");
            }
        } catch (CourseNotFoundException e) {
            sb.append("Curso ").append(courseId).append(" no encontrado.\n");
        }
        return sb.toString();
    }

    ///// Métodos auxiliares para buscar nodos en el árbol

    private TreeNode<Course> findCourseNode(String courseId) {
        for (TreeNode<Course> courseNode : cm.getCourses()) {
            if (courseNode.getData().getId().equals(courseId)) {
                return courseNode;
            }
        }
        return null;
    }

    private TreeNode<Course> findCourseNodeContainingModule(String moduleId) {
        for (TreeNode<Course> courseNode : cm.getCourses()) {
            for (TreeNode<?> moduleNode : courseNode.getChildren()) {
                Object data = moduleNode.getData();
                if (data instanceof Module && ((Module) data).getId().equals(moduleId)) {
                    return courseNode;
                }
            }
        }
        return null;
    }

    private TreeNode<?> findModuleNode(TreeNode<Course> courseNode, String moduleId) {
        for (TreeNode<?> moduleNode : courseNode.getChildren()) {
            Object data = moduleNode.getData();
            if (data instanceof Module && ((Module) data).getId().equals(moduleId)) {
                return moduleNode;
            }
        }
        return null;
    }
    
    public java.util.Set<String> repoGetCompleted(int studentId, String courseId) {
    return repo.getCompletedLessons(studentId, courseId);
    }
}
