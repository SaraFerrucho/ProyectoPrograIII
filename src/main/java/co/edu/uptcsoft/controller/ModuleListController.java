package co.edu.uptcsoft.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class ModuleListController {

    @FXML private TableView<?> tableModules;
    @FXML private TableColumn<?, ?> colModuleName;
    @FXML private TableColumn<?, ?> colDescription;

    @FXML private Button btnViewLessons;
    @FXML private Button btnAddModule;
    @FXML private Button btnBack;

    @FXML
    private void initialize() {
        btnViewLessons.setOnAction(e -> openLessonListView());
        btnBack.setOnAction(e -> goBackToCourses());
    }

    // abrir vista de lecciones
    private void openLessonListView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LessonListView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) tableModules.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Lecciones del módulo");
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // volver a la vista de cursos
    private void goBackToCourses() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CourseListView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Cursos");
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
