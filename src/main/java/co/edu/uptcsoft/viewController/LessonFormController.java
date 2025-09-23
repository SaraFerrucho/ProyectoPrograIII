package co.edu.uptcsoft.viewController;

import co.edu.uptcsoft.controller.CourseController;
import co.edu.uptcsoft.model.Lesson;
import co.edu.uptcsoft.test.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LessonFormController {

    @FXML private TextField txtTitle;
    @FXML private ChoiceBox<String> choiceType;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    private final CourseController courseController = new CourseController();
    private String courseId;
    private String moduleId;
    private String lessonId; // null si es nueva

    public void setLessonData(String courseId, String moduleId, Lesson lesson) {
        this.courseId = courseId;
        this.moduleId = moduleId;
        if (lesson != null) {
            this.lessonId = lesson.getId();
            txtTitle.setText(lesson.getTitle());
            choiceType.setValue(lesson.getType());
        }
    }

    @FXML
    public void initialize() {
        choiceType.getItems().addAll("Video", "Lectura", "Quiz", "Práctica");
    }

    @FXML
    private void onSave() {
        try {
            String title = txtTitle.getText();
            String type = choiceType.getValue();

            if (lessonId == null) {
                // crear nueva lección
                String newId = "L" + System.currentTimeMillis();
                courseController.addLesson(courseId, moduleId, newId, title, type);
                showInfo("Éxito", "Lección creada.");
            } else {
                // actualizar
                courseController.updateLesson(courseId, moduleId, lessonId, title, type);
                showInfo("Éxito", "Lección actualizada.");
            }
            closeWindow();
        } catch (Exception e) {
            showError("Error", e.getMessage());
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

    private void showError(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }

    private void showInfo(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }
}
