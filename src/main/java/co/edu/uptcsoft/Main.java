package co.edu.uptcsoft;
import co.edu.uptcsoft.controller.CourseController;
import co.edu.uptcsoft.controller.StudentController;
public class Main {
    public static void main(String[] args) {
        CourseController controller = new CourseController();
        StudentController sc = new StudentController();
        sc.addStudent(103, "María P.", "maria@uni.edu");
        System.out.println("Listado:");
        sc.listStudents().forEach(System.out::println);
        //sc.deleteStudent(103);
        // 1. Cargar los cursos desde Courses.json
        controller.loadData();

        // 2. Mostrar el árbol de cursos
        System.out.println("=== Catálogo de Cursos ===");
        controller.printTree();
        // 1. Listar estudiantes del JSON
        System.out.println("=== Estudiantes registrados ===");
        sc.listStudents().forEach(System.out::println);

        // 2. Tomar el primero (o buscar por nombre/email)  
        int studentId = sc.listStudents().get(0).getId();  // <-- ahora es dinámico
        String courseId = "C2";

        // 3. Registrar progreso
        System.out.println("\n--- Registrando progreso ---");
        controller.markCompleted(studentId, courseId, "L2"); // estudiante 101 completa L1
        System.out.println("\n--- Registrando progreso ---");
        controller.markCompleted(studentId, courseId, "L3");

        // 4. Mostrar progreso actual
        System.out.println("\n--- Estado de progreso ---");
        controller.showProgress(studentId, courseId);

        // 5. Quitar una lección (para ver el cambio)
        controller.unmarkCompleted(studentId, courseId, "L1");
        System.out.println("\n--- Después de desmarcar ---");
        controller.showProgress(studentId, courseId);
    }
    
}
