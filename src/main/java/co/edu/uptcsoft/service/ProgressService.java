package co.edu.uptcsoft.service;
import java.util.Map;

import co.edu.uptcsoft.model.CoursesManager;
import co.edu.uptcsoft.persistence.ProgressRepository;


public class ProgressService {
     private final CoursesManager cm;
    private final ProgressRepository repo = new ProgressRepository();

    public ProgressService(CoursesManager cm) {
        this.cm = cm;
    }

    // Acciones
    public void markLessonCompleted(int studentId, String courseId, String lessonId) {
        repo.markLesson(studentId, courseId, lessonId);
    }

    public void unmarkLesson(int studentId, String courseId, String lessonId) {
        repo.unmarkLesson(studentId, courseId, lessonId);
    }

    // Consultas
    public double coursePercentage(int studentId, String courseId) {
        var all = cm.lessonIdsUnder(courseId);
        if (all.isEmpty()) return 0.0;
        var done = repo.getCompletedLessons(studentId, courseId);
        long completed = all.stream().filter(done::contains).count();
        return (completed * 100.0) / all.size();
    }

    public Map<String,String> moduleStatuses(int studentId, String courseId) {
        var done = repo.getCompletedLessons(studentId, courseId);
        Map<String,String> st = new java.util.LinkedHashMap<>();

        // ahora usamos IDs de módulo
        for (String moduleId : cm.moduleIdsOfCourse(courseId)) {
            var lessons = cm.lessonIdsUnder(moduleId);
            int total = lessons.size();
            int completed = (int) lessons.stream().filter(done::contains).count();
            st.put(moduleId,
                (completed==0) ? "Pendiente" :
                (completed==total) ? "Completado" : "En curso");
        }
        return st;
    }
    

    public java.util.List<String> allLessonIds(String courseId) {
        return cm.lessonIdsUnder(courseId);
    }
    public java.util.Set<String> completedSet(int studentId, String courseId) {
        return repo.getCompletedLessons(studentId, courseId);
    }
    
}
