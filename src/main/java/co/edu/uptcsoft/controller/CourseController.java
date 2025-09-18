package co.edu.uptcsoft.controller;

import java.util.Map;

import co.edu.uptcsoft.model.Course;
import co.edu.uptcsoft.model.CoursesManager;
import co.edu.uptcsoft.model.Lesson;
import co.edu.uptcsoft.model.Module;
import co.edu.uptcsoft.persistence.CourseDAO;
import co.edu.uptcsoft.service.ProgressService;

public class CourseController {
    private CoursesManager courseManager;
    private CourseDAO courseDAO;

    private ProgressService progressService;

    public CourseController() {
        courseManager = new CoursesManager();
        courseDAO = new CourseDAO();
        progressService = new ProgressService(courseManager); 
    }

    // CRUD Cursos
    public void addCourse(Course course) {
        courseManager.addCourse(course);
    }

    public void addModule(String courseId, Module module) {
        courseManager.addModule(courseId, module);
    }

    public void addLesson(String moduleId, Lesson lesson) {
        courseManager.addLesson(moduleId, lesson);
    }

    public void printTree() {
        courseManager.printTree();
    }

    // Persistencia
    public void saveData() {
        courseDAO.save(courseManager.getRoot());
    }

    public void loadData() {
        courseManager.setRoot(courseDAO.load());
    }
    // ==== Progreso (expuesto al menú) ====

    /** Marcar una lección como completada */
    public void markCompleted(int studentId, String courseId, String lessonId) {
        progressService.markLessonCompleted(studentId, courseId, lessonId);
    }

    /** Desmarcar una lección */
    public void unmarkCompleted(int studentId, String courseId, String lessonId) {
        progressService.unmarkLesson(studentId, courseId, lessonId);
    }

    /** Mostrar porcentaje y estado por módulo */
    public void showProgress(int studentId, String courseId) {
    // DEBUG: lecciones totales bajo el curso y las completadas desde progress.json
    var all = progressService.allLessonIds(courseId);
    var done = progressService.completedSet(studentId, courseId);
    System.out.println("DEBUG total lessons under " + courseId + ": " + all);
    System.out.println("DEBUG done lessons for student " + studentId + ": " + done);

    double pct = progressService.coursePercentage(studentId, courseId);
    System.out.printf("Progreso estudiante %d en %s: %.1f%%%n", studentId, courseId, pct);

    Map<String,String> perModule = progressService.moduleStatuses(studentId, courseId);
    perModule.forEach((modId, status) ->
        System.out.println(" - Módulo " + modId + ": " + status)
    );
}
}
