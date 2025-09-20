package co.edu.uptcsoft.controller;

import co.edu.uptcsoft.model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class StudentViewController {

    @FXML
    private TableView<Student> tableStudents;

    @FXML
    private TableColumn<Student, Integer> colId;

    @FXML
    private TableColumn<Student, String> colName;

    @FXML
    private TableColumn<Student, String> colEmail;

    @FXML
    private TableColumn<Student, String> colCourse;

    @FXML
    private Button btnAddStudent;

    @FXML
    private Button btnDeleteStudent;

    @FXML
    private Button btnBack;

    @FXML
    private void initialize() {
        // CONFIGURAR LAS COLUMNAS
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        colCourse.setCellValueFactory(cellData -> cellData.getValue().courseProperty());
    }

    @FXML
    private void handleAddStudent() {
        System.out.println("Registrar nuevo estudiante...");
        // aqui iria la logica para abrir ventana de registro
    }

    @FXML
    private void handleDeleteStudent() {
        Student selected = tableStudents.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println("Eliminar estudiante: " + selected.getName());
            // aqui iria la logica para eliminar
        } else {
            System.out.println("Selecciona un estudiante primero.");
        }
    }

    @FXML
    private void handleBack() {
        System.out.println("Volviendo al menu principal...");
        // aqui se puede cargar el mainview
    }
}
