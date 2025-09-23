package co.edu.uptcsoft.viewController;

import java.util.Optional;
import java.util.regex.Pattern; // Importar para validación de email

import co.edu.uptcsoft.controller.StudentController;
import co.edu.uptcsoft.model.Student;
import co.edu.uptcsoft.test.DuplicateStudentException;
import co.edu.uptcsoft.test.StudentNotFoundException;
import co.edu.uptcsoft.util.AppContext;
import co.edu.uptcsoft.util.SceneManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;

public class StudentViewController {

    @FXML private TableView<Student> tableStudents;
    @FXML private TableColumn<Student, Number> colId;
    @FXML private TableColumn<Student, String> colName;
    @FXML private TableColumn<Student, String> colEmail;
    @FXML private TableColumn<Student, String> colCourse;   // <-- NUEVA

    @FXML private Button btnAddStudent;
    @FXML private Button btnEditStudent;
    @FXML private Button btnDeleteStudent;

    private final StudentController studentController = new StudentController();
    private final ObservableList<Student> studentList = FXCollections.observableArrayList();

    // Regex para validar email (formato básico: usuario@dominio.com)
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$");

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()));
        colName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        colEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));

        // TODO: reemplazar por la propiedad real del curso (nombre o id) si tu modelo la tiene
        colCourse.setCellValueFactory(data -> new SimpleStringProperty("")); // placeholder

        studentController.loadData();
        loadStudents();
        tableStudents.setRowFactory(tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(ev -> {
                if (!row.isEmpty() && ev.getClickCount() == 2) {
                    Student s = row.getItem();
                    AppContext.currentStudentId = s.getId(); // guardar contexto
                    SceneManager.switchScene("co/edu/uptcsoft/view/CourseListView.fxml");
                }
            });
            return row;
        });
        
    }

    private void loadStudents() {
        studentList.setAll(studentController.listStudents());
        tableStudents.setItems(studentList);
    }

    @FXML
    private void onAddStudent() {
        // Solicitar ID
        TextInputDialog idDialog = new TextInputDialog();
        idDialog.setTitle("Nuevo Estudiante");
        idDialog.setHeaderText("Ingrese el ID del estudiante");
        idDialog.setContentText("ID:");
        Optional<String> idResult = idDialog.showAndWait();

        if (idResult.isEmpty() || idResult.get().trim().isEmpty()) {
            showError("Entrada inválida", "El ID no puede estar vacío.");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idResult.get().trim());
            if (id <= 0) {
                showError("Entrada inválida", "El ID debe ser un número positivo.");
                return;
            }
        } catch (NumberFormatException e) {
            showError("Entrada inválida", "El ID debe ser un número entero.");
            return;
        }

        // Solicitar Nombre
        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Nuevo Estudiante");
        nameDialog.setHeaderText("Ingrese el nombre del estudiante");
        nameDialog.setContentText("Nombre:");
        Optional<String> nameResult = nameDialog.showAndWait();

        if (nameResult.isEmpty() || nameResult.get().trim().isEmpty()) {
            showError("Entrada inválida", "El nombre no puede estar vacío.");
            return;
        }
        String name = nameResult.get().trim();

        // Solicitar Email
        TextInputDialog emailDialog = new TextInputDialog();
        emailDialog.setTitle("Nuevo Estudiante");
        emailDialog.setHeaderText("Ingrese el email del estudiante");
        emailDialog.setContentText("Email:");
        Optional<String> emailResult = emailDialog.showAndWait();

        if (emailResult.isEmpty() || emailResult.get().trim().isEmpty()) {
            showError("Entrada inválida", "El email no puede estar vacío.");
            return;
        }
        String email = emailResult.get().trim();

        // Validar formato de email
        if (!isValidEmail(email)) {
            showError("Email inválido", "El email debe tener un formato válido (ej: usuario@dominio.com).");
            return;
        }

        try {
            studentController.addStudent(id, name, email);
            loadStudents();
            showInfo("Éxito", "Estudiante añadido correctamente.");
        } catch (DuplicateStudentException e) {
            showError("Error", e.getMessage());
        } catch (IllegalArgumentException e) {
            showError("Error de validación", e.getMessage());
        } catch (Exception e) {
            showError("Error", "Ocurrió un error al añadir el estudiante: " + e.getMessage());
        }
    }

    @FXML
    private void onEditStudent() {
        Student selected = tableStudents.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Solicitar nuevo nombre
            TextInputDialog nameDialog = new TextInputDialog(selected.getName());
            nameDialog.setTitle("Editar Estudiante");
            nameDialog.setHeaderText("Ingrese el nuevo nombre para " + selected.getName());
            nameDialog.setContentText("Nombre:");
            Optional<String> nameResult = nameDialog.showAndWait();

            if (nameResult.isEmpty() || nameResult.get().trim().isEmpty()) {
                showError("Entrada inválida", "El nombre no puede estar vacío.");
                return;
            }
            String newName = nameResult.get().trim();

            // Solicitar nuevo email
            TextInputDialog emailDialog = new TextInputDialog(selected.getEmail());
            emailDialog.setTitle("Editar Estudiante");
            emailDialog.setHeaderText("Ingrese el nuevo email para " + selected.getName());
            emailDialog.setContentText("Email:");
            Optional<String> emailResult = emailDialog.showAndWait();

            if (emailResult.isEmpty() || emailResult.get().trim().isEmpty()) {
                showError("Entrada inválida", "El email no puede estar vacío.");
                return;
            }
            String newEmail = emailResult.get().trim();

            // Validar formato de email
            if (!isValidEmail(newEmail)) {
                showError("Email inválido", "El email debe tener un formato válido (ej: usuario@dominio.com).");
                return;
            }

            try {
                studentController.updateStudent(selected.getId(), newName, newEmail);
                loadStudents();
                showInfo("Éxito", "Estudiante actualizado correctamente.");
            } catch (StudentNotFoundException e) {
                showError("Error", "Estudiante no encontrado.");
            } catch (IllegalArgumentException e) {
                showError("Error de validación", e.getMessage());
            } catch (Exception e) {
                showError("Error", "Ocurrió un error al actualizar el estudiante: " + e.getMessage());
            }
        } else {
            showError("Atención", "Selecciona un estudiante primero para editar.");
        }
    }

    @FXML
    private void onDeleteStudent() {
        Student selected = tableStudents.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmar Eliminación");
            confirmation.setHeaderText("¿Está seguro de que desea eliminar al estudiante?");
            confirmation.setContentText("Estudiante: " + selected.getName() + " (ID: " + selected.getId() + ")");

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    studentController.deleteStudent(selected.getId());
                    loadStudents();
                    showInfo("Éxito", "Estudiante eliminado correctamente.");
                } catch (StudentNotFoundException e) {
                    showError("Error", "Estudiante no encontrado.");
                } catch (Exception e) {
                    showError("Error eliminando estudiante", e.getMessage());
                }
            }
        } else {
            showError("Atención", "Selecciona un estudiante para eliminar.");
        }
    }

    // Método privado para validar el formato de email usando regex
    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    @FXML
    private void goBackToMain() {
        co.edu.uptcsoft.util.SceneManager.switchScene("co/edu/uptcsoft/view/MainView.fxml");
    }

    private void showError(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showInfo(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
