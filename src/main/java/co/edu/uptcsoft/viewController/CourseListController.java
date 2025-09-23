package co.edu.uptcsoft.viewController;

import java.io.IOException;

import co.edu.uptcsoft.controller.CourseController;
import co.edu.uptcsoft.model.Course;
import co.edu.uptcsoft.model.TreeNode;
import co.edu.uptcsoft.util.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class CourseListController {

    @FXML
    private TableView<Course> tableCourses;
    @FXML
    private TableColumn<Course, String> colCourseId;
    @FXML
    private TableColumn<Course, String> colCourseTitle;
    @FXML
    private Button btnAddCourse, btnDeleteCourse, btnEditCourse, btnBack;

    private final ObservableList<Course> courseList = FXCollections.observableArrayList();
    private final CourseController courseController = new CourseController();

    @FXML
    public void initialize() {
        colCourseId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getId()));
        colCourseTitle.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitle()));

        colCourseId.setText("ID del Curso");
        colCourseTitle.setText("Título del Curso");

        loadCourses();

        tableCourses.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2 && !tableCourses.getSelectionModel().isEmpty()) {
                openModuleListView();
            }
        });

        btnAddCourse.setOnAction(e -> AddCourseController.open(this));
        btnEditCourse.setOnAction(e -> editSelectedCourse());
        btnDeleteCourse.setOnAction(e -> deleteSelectedCourse());
        btnBack.setOnAction(e -> goBackToMain());
    }

    private void loadCourses() {
        courseController.loadData(); // Cargar desde JSON
        courseList.clear();
        for (TreeNode<Course> node : courseController.getCourses()) {
            courseList.add(node.getData());
        }
        tableCourses.setItems(courseList);
    }

    private void deleteSelectedCourse() {
        Course selected = tableCourses.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                courseController.deleteCourse(selected.getId());
                loadCourses();
            } catch (Exception e) {
                showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private void editSelectedCourse() {
        Course selected = tableCourses.getSelectionModel().getSelectedItem();
        if (selected != null) {
            EditCourseController.open(this, courseController, selected.getId(), selected.getTitle());
        }
    }

    private void openModuleListView() {
        Course selectedCourse = tableCourses.getSelectionModel().getSelectedItem();
        if (selectedCourse == null) {
            showAlert("Seleccione un curso", "Debe seleccionar un curso para ver los módulos.",
                    Alert.AlertType.WARNING);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uptcsoft/view/ModuleListView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Módulos del curso: " + selectedCourse.getTitle());

            ModuleListController controller = loader.getController();
            controller.setController(courseController); // PASAR la misma instancia de controlador
            controller.setCourse(selectedCourse.getId());

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir la ventana de módulos.", Alert.AlertType.ERROR);
        }
    }

    public void refreshCourses() {
        loadCourses(); // Recargar tabla
    }

    @FXML
     private void goBackToMain() {
        SceneManager.switchScene("co/edu/uptcsoft/view/MainView.fxml");
    }

    private void showAlert(String title, String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
