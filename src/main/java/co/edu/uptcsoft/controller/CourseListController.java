package co.edu.uptcsoft.controller;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class CourseListController {

    @FXML private TableView<?> tableCourses;
    @FXML private TableColumn<?, ?> colCourseName;
    @FXML private TableColumn<?, ?> colDescription;

    @FXML private Button btnAddCourse;
    @FXML private Button btnDeleteCourse;
    @FXML private Button btnAssignStudent;
    @FXML private Button btnBack;

    @FXML
    private void initialize() {
        // evento doble click en tabla para abrir módulos
        tableCourses.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !tableCourses.getSelectionModel().isEmpty()) {
                openModuleListView();
            }
        });

        btnAssignStudent.setOnAction(e -> openAssignStudentWindow());
        btnBack.setOnAction(e -> goBackToMain());
    }

    // abre vista de módulos
    private void openModuleListView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModuleListView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) tableCourses.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Módulos del curso");
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // abre ventana emergente de asignación de estudiantes
    private void openAssignStudentWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StudentView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Asignar estudiantes");
            stage.initOwner(tableCourses.getScene().getWindow());
            stage.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    

    // volver al menú principal
    private void goBackToMain() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Menú principal");
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
