package co.edu.uptcsoft.viewController;

import co.edu.uptcsoft.controller.CourseController;
import co.edu.uptcsoft.test.CourseNotFoundException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class EditCourseController {

    @FXML private TextField txtCourseId;
    @FXML private TextField txtCourseTitle;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    private CourseController courseController;
    private CourseListController parentController;
    private String courseId;

    // Abrir ventana de edición
    public static void open(CourseListController parent, CourseController controller, String courseId, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(EditCourseController.class.getResource("/co/edu/uptcsoft/view/EditCourseView.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);

            EditCourseController editController = loader.getController();
            editController.setParentController(parent);
            editController.setCourseController(controller);

            // Setear datos cuando el Stage se muestre, asegurando que los @FXML estén inicializados
            stage.setOnShown(e -> editController.setCourseData(courseId, title));

            stage.setTitle("Editar Curso");
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setParentController(CourseListController parent) {
        this.parentController = parent;
    }

    public void setCourseController(CourseController controller) {
        this.courseController = controller;
    }

    public void setCourseData(String courseId, String title) {
        this.courseId = courseId;
        txtCourseId.setText(courseId);
        txtCourseTitle.setText(title);
    }

    @FXML
    private void initialize() {
        // Asignar acciones de botones
        btnSave.setOnAction(e -> onSave());
        btnCancel.setOnAction(e -> onCancel());
    }

    private void onSave() {
        String newTitle = txtCourseTitle.getText().trim();
        if (newTitle.isEmpty()) {
            showAlert("Error", "El título no puede estar vacío.", Alert.AlertType.ERROR);
            return;
        }

        try {
            courseController.updateCourse(courseId, newTitle);
            showAlert("Éxito", "Curso actualizado correctamente.", Alert.AlertType.INFORMATION);

            if (parentController != null) {
                parentController.refreshCourses();
            }
            closeWindow();
        } catch (CourseNotFoundException e) {
            showAlert("Error", "Curso no encontrado.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void onCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
