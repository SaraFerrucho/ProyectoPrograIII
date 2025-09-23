package co.edu.uptcsoft.viewController;

import co.edu.uptcsoft.controller.CourseController;
import co.edu.uptcsoft.test.CourseNotFoundException;
import co.edu.uptcsoft.test.ModuleNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditModuleController {

    @FXML private TextField txtModuleId;
    @FXML private TextField txtModuleTitle;
    @FXML private Button btnSave, btnCancel;

    private String moduleId;
    private String courseId;
    private CourseController courseController;
    private ModuleListController parentController;

    // Este método se llama desde ModuleListController
    public void setModuleData(String moduleId, String title, String courseId,
                              ModuleListController parentController, CourseController courseController) {
        this.moduleId = moduleId;
        this.courseId = courseId;
        this.parentController = parentController;
        this.courseController = courseController;

        txtModuleId.setText(moduleId);
        txtModuleTitle.setText(title);
    }

    @FXML
    private void onSave() {
        String newTitle = txtModuleTitle.getText().trim();
        if (newTitle.isEmpty()) {
            showAlert("Error", "El título no puede estar vacío.", Alert.AlertType.ERROR);
            return;
        }

        try {
            courseController.updateModule(courseId, moduleId, newTitle);
            showAlert("Éxito", "Módulo actualizado correctamente.", Alert.AlertType.INFORMATION);

            if (parentController != null) {
                parentController.refreshModules(); // Refrescar tabla
            }

            closeWindow();
        } catch (CourseNotFoundException e) {
            showAlert("Error", "Curso no encontrado.", Alert.AlertType.ERROR);
        } catch (ModuleNotFoundException e) {
            showAlert("Error", "Módulo no encontrado.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
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
