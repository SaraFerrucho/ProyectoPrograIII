package co.edu.uptcsoft.viewController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import co.edu.uptcsoft.controller.CourseController;
import co.edu.uptcsoft.model.Module;
import co.edu.uptcsoft.model.TreeNode;
import co.edu.uptcsoft.test.CourseNotFoundException;
import co.edu.uptcsoft.test.DuplicateModuleException;
import co.edu.uptcsoft.test.ModuleNotFoundException;
import co.edu.uptcsoft.util.AppContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModuleListController {

    @FXML
    private TableView<Module> tableModules;
    @FXML
    private TableColumn<Module, String> colModuleId;
    @FXML
    private TableColumn<Module, String> colModuleTitle;

    @FXML
    private Button btnAddModule;
    @FXML
    private Button btnDeleteModule;
    @FXML
    private Button btnViewLessons;
    @FXML
    private Button btnBack;

    private CourseController courseController; // ahora se recibe desde CourseListController
    private final ObservableList<Module> moduleList = FXCollections.observableArrayList();
    private String currentCourseId;

    public void setController(CourseController controller) {
        this.courseController = controller;
    }

    public void setCourse(String courseId) {
        this.currentCourseId = courseId;
        loadModules();
    }

    @FXML
    public void initialize() {
        colModuleId
                .setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getId()));
        colModuleTitle.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitle()));

        btnAddModule.setOnAction(e -> openAddModuleDialog());
        btnDeleteModule.setOnAction(e -> deleteSelectedModule());
        btnViewLessons.setOnAction(e -> viewLessonsOfSelectedModule());
        btnBack.setOnAction(e -> goBackToCourses());

        tableModules.setRowFactory(tv -> {
            TableRow<Module> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    editSelectedModule(row.getItem());
                }
            });
            return row;
        });
    }

    private void loadModules() {
        moduleList.clear();
        try {
            List<TreeNode<Module>> modules = courseController.getCourses().stream()
                    .filter(c -> c.getData().getId().equals(currentCourseId))
                    .flatMap(c -> c.getChildren().stream().map(n -> (TreeNode<Module>) n))
                    .collect(Collectors.toList()); // <- CORRECCIÓN

            for (TreeNode<Module> node : modules) {
                moduleList.add(node.getData());
            }
            tableModules.setItems(moduleList);
        } catch (Exception e) {
            showError("Error cargando módulos", e.getMessage());
        }
    }

    private void openAddModuleDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Añadir módulo");
        dialog.setHeaderText("Nuevo módulo para el curso: " + currentCourseId);
        dialog.setContentText("ID del módulo:");

        dialog.showAndWait().ifPresent(id -> {
            TextInputDialog titleDialog = new TextInputDialog();
            titleDialog.setTitle("Título del módulo");
            titleDialog.setHeaderText("Ingrese el título del módulo");
            titleDialog.setContentText("Título:");
            titleDialog.showAndWait().ifPresent(title -> {
                try {
                    courseController.addModule(currentCourseId, id, title);
                    loadModules();
                } catch (DuplicateModuleException ex) {
                    showError("Error", "El módulo ya existe.");
                } catch (CourseNotFoundException ex) {
                    showError("Error", "Curso no encontrado.");
                } catch (Exception ex) {
                    showError("Error", ex.getMessage());
                }
            });
        });
    }

    private void deleteSelectedModule() {
        Module selected = tableModules.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                courseController.deleteModule(currentCourseId, selected.getId());
                loadModules();
            } catch (ModuleNotFoundException e) {
                showError("Error", "Módulo no encontrado.");
            } catch (CourseNotFoundException e) {
                showError("Error", "Curso no encontrado.");
            } catch (Exception e) {
                showError("Error", e.getMessage());
            }
        }
    }

    private void editSelectedModule(Module module) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uptcsoft/view/EditModuleView.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(loader.load()));

            EditModuleController controller = loader.getController();
            controller.setModuleData(module.getId(), module.getTitle(), currentCourseId, this, courseController);

            stage.setTitle("Editar módulo");
            stage.showAndWait();

            loadModules(); // refrescar tabla después de cerrar
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     private void viewLessonsOfSelectedModule() {
        Module selected = tableModules.getSelectionModel().getSelectedItem();
        if (selected != null) {
            openLessons(currentCourseId, selected.getId());
        } else {
            showError("Selecciona un módulo", "Debes seleccionar un módulo de la tabla.");
        }
    }

    public void refreshModules() {
        loadModules();
    }

    private void showError(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }

    @FXML
    private void goBackToCourses() {
        // por ejemplo, cerrar ventana actual
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }
    private void openLessons(String courseId, String moduleId) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/co/edu/uptcsoft/view/LessonListView.fxml")
            );
            Scene scene = new Scene(loader.load());

            LessonListController ctrl = loader.getController();
            int studentId = AppContext.currentStudentId; // del StudentView
            ctrl.setContext(this.courseController, courseId, moduleId, studentId);

            // Escoge cualquier nodo de este controller para obtener la Stage
            Stage stage = (Stage) tableModules.getScene().getWindow(); // o btnViewLessons.getScene().getWindow()
            stage.setScene(scene);
            stage.setTitle("Lecciones");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR,
                "No se pudo abrir Lecciones: " + e.getMessage()).showAndWait();
        }
    }

    

}
