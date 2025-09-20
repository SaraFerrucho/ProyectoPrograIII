package co.edu.uptcsoft.controller;

import co.edu.uptcsoft.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainViewController {

    @FXML private VBox menuLateral;
    @FXML private Button menuButton;
    @FXML private Button btnCursos;
    @FXML private Button btnEstudiantes;
    @FXML private Button btnEstadisticas;
    @FXML private Button btnSalir;

    @FXML
    private void initialize() {
        // mostrar / ocultar menú lateral
        menuButton.setOnAction(e -> {
            boolean visible = menuLateral.isVisible();
            menuLateral.setVisible(!visible);
            menuLateral.setManaged(!visible);
        });

        // navegación entre vistas
        btnCursos.setOnAction(e -> SceneManager.switchScene("CourseListView.fxml"));
        btnEstudiantes.setOnAction(e -> SceneManager.switchScene("StudentView.fxml"));
        btnEstadisticas.setOnAction(e -> SceneManager.switchScene("StadisticsView.fxml"));

        // salir
        btnSalir.setOnAction(e -> System.exit(0));
    }
}
