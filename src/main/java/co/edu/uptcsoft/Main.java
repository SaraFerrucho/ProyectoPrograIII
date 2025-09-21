package co.edu.uptcsoft;

import co.edu.uptcsoft.controller.CourseController;
import co.edu.uptcsoft.controller.StudentController;

public class Main {
    public static void main(String[] args) {
        CourseController controller = new CourseController();
        StudentController sc = new StudentController();

        // Registrar un estudiante
        sc.addStudent(101, "Juan Pérez", "juan@uni.edu");

        // Cargar cursos desde el JSON
        controller.loadData();

        System.out.println("=== Catálogo de Cursos ===");
        System.out.println(controller.getCoursesAsString());

        int studentId = 101;
        String courseId = "C2"; // Asegúrate que exista en tu Courses.json

        // Marcar lecciones completadas
        controller.markCompleted(studentId, courseId, "L1");
        controller.markCompleted(studentId, courseId, "L3");

        // Mostrar progreso actual
        System.out.println("\n--- Estado de progreso ---");
        controller.showProgress(studentId, courseId);

        // Desmarcar una lección
        controller.unmarkCompleted(studentId, courseId, "L2");
        System.out.println("\n--- Después de desmarcar ---");
        controller.showProgress(studentId, courseId);
    }
}
