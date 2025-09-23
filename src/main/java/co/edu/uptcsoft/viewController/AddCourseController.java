package co.edu.uptcsoft.viewController;

import co.edu.uptcsoft.controller.CourseController;
import co.edu.uptcsoft.test.DuplicateCourseException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCourseController {

    @FXML
    private TextField txtCourseId;
    @FXML
    private TextField txtCourseTitle;
    @FXML
    private Button btnSave, btnCancel;

    private final CourseController courseController = new CourseController();
    private CourseListController parentController;

    public void setParentController(CourseListController parentController) {
        this.parentController = parentController;
    }

    @FXML
    public void initialize() {
        btnSave.setOnAction(e -> saveCourse());
        btnCancel.setOnAction(e -> ((Stage) btnCancel.getScene().getWindow()).close());
    }

    private void saveCourse() {
        String id = txtCourseId.getText().trim();
        String title = txtCourseTitle.getText().trim();

        if (id.isEmpty() || title.isEmpty()) {
            showAlert("Error", "ID y título no pueden estar vacíos", Alert.AlertType.WARNING);
            return;
        }

        try {
            // Cargar datos existentes antes de añadir uno nuevo
            courseController.loadData();
            courseController.addCourse(id, title);
            showAlert("Éxito", "Curso añadido correctamente", Alert.AlertType.INFORMATION);

            if (parentController != null) {
                parentController.refreshCourses();
            }

            ((Stage) btnSave.getScene().getWindow()).close();
        } catch (DuplicateCourseException ex) {
            showAlert("Error", ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void open(CourseListController parent) {
        try {
            FXMLLoader loader = new FXMLLoader(AddCourseController.class.getResource("/co/edu/uptcsoft/view/AddCourseView.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(loader.load()));

            AddCourseController controller = loader.getController();
            controller.setParentController(parent);

            stage.setTitle("Añadir Curso");
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
