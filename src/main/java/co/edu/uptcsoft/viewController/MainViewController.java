package co.edu.uptcsoft.viewController;

import co.edu.uptcsoft.util.AppContext;
import co.edu.uptcsoft.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainViewController {

    @FXML
    private Button btnCourses;
    @FXML
    private Button btnStudents;
    @FXML
    private Button btnStatistics;
    @FXML
    private Button btnExit;

    // Abrir ventana de Cursos en la misma Stage
    @FXML
    private void handleCourses() {
        // ←← IMPORTANTE: limpiar el estudiante en contexto
        AppContext.currentStudentId = 0;
        SceneManager.switchScene("co/edu/uptcsoft/view/CourseListView.fxml");
    }

    // Abrir ventana de Estudiantes
    @FXML
    private void handleStudents() {
        
        SceneManager.switchScene("co/edu/uptcsoft/view/StudentView.fxml");
    }

    // Abrir ventana de Estadísticas
    @FXML
    private void handleStatistics() {
        System.out.println("clic estadísticas");
        SceneManager.switchScene("co/edu/uptcsoft/view/StadisticsView.fxml");
    }

    // Salir del sistema
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
