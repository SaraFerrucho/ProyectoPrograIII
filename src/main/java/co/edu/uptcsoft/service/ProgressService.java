package co.edu.uptcsoft.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import co.edu.uptcsoft.model.Course;
import co.edu.uptcsoft.model.Lesson;
import co.edu.uptcsoft.persistence.ProgressRepository;
import co.edu.uptcsoft.test.CourseNotFoundException;

public class ProgressService {
    private final co.edu.uptcsoft.model.CoursesManager cm;
    private final ProgressRepository repo = new ProgressRepository();

    public ProgressService(co.edu.uptcsoft.model.CoursesManager cm) {
        this.cm = cm;
    }

    /* ===== Helpers internos ===== */

    private List<String> allLessonIdsOf(Course course) {
        List<String> ids = new ArrayList<>();
        for (co.edu.uptcsoft.model.Module m : course.getModules()) {   // FQN
            for (Lesson l : m.getLessons()) {
                if (l.getId() != null) ids.add(l.getId());
            }
        }
        return ids;
    }

    private List<String> lessonIdsOfModule(co.edu.uptcsoft.model.Module m) {  // FQN
        List<String> ids = new ArrayList<>();
        for (Lesson l : m.getLessons()) {
            if (l.getId() != null) ids.add(l.getId());
        }
        return ids;
    }

    /* ===== Acciones ===== */

    public void markLessonCompleted(int studentId, String courseId, String lessonId) {
        repo.markLesson(studentId, courseId, lessonId);
    }

    public void unmarkLesson(int studentId, String courseId, String lessonId) {
        repo.unmarkLesson(studentId, courseId, lessonId);
    }

    /* ===== Consultas ===== */

    public double coursePercentage(int studentId, String courseId) {
        try {
            Course c = cm.findCourse(courseId);
            List<String> all = allLessonIdsOf(c);
            if (all.isEmpty()) return 0.0;
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
            for (co.edu.uptcsoft.model.Module m : c.getModules()) {    // FQN
                List<String> lessons = lessonIdsOfModule(m);
                int total = lessons.size();
                int completed = (int) lessons.stream().filter(done::contains).count();
                String status = (total == 0) ? "Sin lecciones"
                        : (completed == 0) ? "Pendiente"
                        : (completed == total) ? "Completado"
                        : "En curso";
                st.put(m.getId(), status);
            }
        } catch (CourseNotFoundException e) {
            // curso no existe → mapa vacío
        }
        return st;
    }

    /** Reporte textual para imprimir desde el controlador */
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
}
