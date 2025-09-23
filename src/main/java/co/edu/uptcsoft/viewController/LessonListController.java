package co.edu.uptcsoft.viewController;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import co.edu.uptcsoft.controller.CourseController;
import co.edu.uptcsoft.model.Course;
import co.edu.uptcsoft.model.Lesson;
import co.edu.uptcsoft.model.Module;
import co.edu.uptcsoft.model.TreeNode;
import co.edu.uptcsoft.service.ProgressService;
import co.edu.uptcsoft.test.CourseNotFoundException;
import co.edu.uptcsoft.test.DuplicateLessonException;
import co.edu.uptcsoft.test.LessonNotFoundException;
import co.edu.uptcsoft.test.ModuleNotFoundException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Controlador para la lista de lecciones (por módulo).
 * Integra seguimiento de progreso por lección (checkbox "Hecha").
 */
public class LessonListController implements Initializable {

    @FXML private Button btnBack;
    @FXML private CheckBox chkSelectAll;

    @FXML private TableView<LessonRow> tableLessons;
    @FXML private TableColumn<LessonRow, Boolean> colSelect;      // selección múltiple (tuya)
    @FXML private TableColumn<LessonRow, Boolean> colDone;        // progreso (NUEVA)
    @FXML private TableColumn<LessonRow, String>  colLessonName;
    @FXML private TableColumn<LessonRow, String>  colLessonType;

    @FXML private Button btnAddLesson;
    @FXML private Button btnDeleteLesson;
    @FXML private Button btnEditLesson;

    private CourseController courseController;
    private String courseId;
    private String moduleId;

    // Contexto para progreso
    private int currentStudentId = 0;      // pásalo desde la pantalla anterior
    private String currentCourseId = null; // pásalo desde el flujo (normalmente == courseId)

    // Service de progreso: para mark/unmark y consulta de lecciones hechas
    private final ProgressService progressService =
            new ProgressService(new co.edu.uptcsoft.model.CoursesManager());

    private final ObservableList<LessonRow> observableLessons = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // columnas existentes
        colSelect.setCellValueFactory(cell -> cell.getValue().selectedProperty());
        colSelect.setCellFactory(CheckBoxTableCell.forTableColumn(colSelect));

        colLessonName.setCellValueFactory(cell -> cell.getValue().titleProperty());
        colLessonType.setCellValueFactory(cell -> cell.getValue().typeProperty());

        // --------- Progreso: columna "Hecha" ----------
        // Usamos una propiedad booleana del Row (doneProperty) que refleje el repo
        colDone.setCellValueFactory(cell -> cell.getValue().doneProperty());
        colDone.setCellFactory(CheckBoxTableCell.forTableColumn(colDone));
        colDone.setEditable(true);
        tableLessons.setEditable(true);


        tableLessons.setItems(observableLessons);

        // doble click -> editar
        tableLessons.setOnMouseClicked((MouseEvent e) -> {
            if (e.getClickCount() == 2) {
                onEditLesson(null);
            }
        });

        // seleccionar todos (tu lógica de selección múltiple, no afecta progreso)
        chkSelectAll.selectedProperty().addListener((obs, oldV, newV) -> {
            for (LessonRow r : observableLessons) r.setSelected(newV);
        });
    }

    /**
     * Establece el contexto de la vista.
     * @param controller CourseController para navegar el árbol
     * @param courseId   ID de curso al que pertenece el módulo
     * @param moduleId   ID de módulo cuyas lecciones listamos
     * @param studentId  ID de estudiante para marcar progreso
     */
    public void setContext(CourseController controller, String courseId, String moduleId, int studentId) {
        this.courseController = controller;
        this.courseId = courseId;
        this.moduleId = moduleId;

        // contexto de progreso
        this.currentStudentId = studentId;
        this.currentCourseId = courseId; // en la mayoría de flujos coincide con courseId
        boolean sinEstudiante = (this.currentStudentId == 0);
        tableLessons.setEditable(!sinEstudiante);
        refreshTable();
    }

    private void refreshTable() {
        observableLessons.clear();

        TreeNode<?> courseNode = findCourseNode(courseId);
        if (courseNode == null) { showError("Curso no encontrado",""); return; }

        TreeNode<?> moduleNode = findModuleNode(courseNode, moduleId);
        if (moduleNode == null) { showError("Módulo no encontrado",""); return; }

        @SuppressWarnings("unchecked")
        List<TreeNode<Lesson>> lessons = (List<TreeNode<Lesson>>) (List<?>) moduleNode.getChildren();

        // Obtener set de lecciones completadas del estudiante-curso para pintar "Hecha"
        Set<String> done = (currentCourseId != null && currentStudentId != 0)
                ? progressService.repoGetCompleted(currentStudentId, currentCourseId)
                : Collections.emptySet();

       for (TreeNode<Lesson> ln : lessons) {
        LessonRow row = new LessonRow(ln);
        final String lessonId = ln.getData().getId(); // <- usa una variable final
        row.setDone(done.contains(lessonId));

        // guarda JSON cada vez que cambie el check
        row.doneProperty().addListener((obs, was, isNow) -> {
            if (currentCourseId == null || currentStudentId == 0) return;
            
            if (isNow) {
                progressService.markLessonCompleted(currentStudentId, currentCourseId, lessonId);
            } else {
                progressService.unmarkLesson(currentStudentId, currentCourseId, lessonId);
            }
        });

        observableLessons.add(row);
    }
        tableLessons.setItems(observableLessons);
    }

    private TreeNode<?> findCourseNode(String id) {
        if (courseController == null) return null;
        List<TreeNode<Course>> courses = courseController.getCourses();
        for (TreeNode<Course> cNode : courses) {
            if (cNode.getData().getId().equals(id)) return cNode;
        }
        return null;
    }

    private TreeNode<?> findModuleNode(TreeNode<?> courseNode, String moduleId) {
        @SuppressWarnings("unchecked")
        List<TreeNode<Module>> modules = (List<TreeNode<Module>>) (List<?>) courseNode.getChildren();
        for (TreeNode<Module> m : modules) {
            if (m.getData().getId().equals(moduleId)) return m;
        }
        return null;
    }

    @FXML
    private void goBackToCourses(ActionEvent event) {
        Stage st = (Stage) btnBack.getScene().getWindow();
        st.close();
    }

    @FXML
    private void onAddLesson(ActionEvent event) {
        Dialog<Triple> dialog = new Dialog<>();
        dialog.setTitle("Añadir lección");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        TextField idField = new TextField();
        idField.setPromptText("ID");
        TextField titleField = new TextField();
        titleField.setPromptText("Título");
        TextField typeField = new TextField();
        typeField.setPromptText("Tipo");

        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.add(new Label("ID:"), 0, 0); gp.add(idField, 1, 0);
        gp.add(new Label("Título:"), 0, 1); gp.add(titleField, 1, 1);
        gp.add(new Label("Tipo:"), 0, 2); gp.add(typeField, 1, 2);

        dialog.getDialogPane().setContent(gp);
        dialog.setResultConverter(bt -> {
            if (bt == ButtonType.OK) return new Triple(idField.getText().trim(), titleField.getText().trim(), typeField.getText().trim());
            return null;
        });

        Optional<Triple> res = dialog.showAndWait();
        res.ifPresent(t -> {
            try {
                courseController.addLesson(courseId, moduleId, t.id, t.title, t.type);
                refreshTable();
                showInfo("Lección agregada", "Lección añadida correctamente.");
            } catch (DuplicateLessonException e) {
                showError("Duplicado", "Ya existe una lección con ese ID.");
            } catch (CourseNotFoundException | ModuleNotFoundException e) {
                showError("No encontrado", e.getMessage());
            } catch (IllegalArgumentException e) {
                showError("Entrada inválida", e.getMessage());
            } catch (Exception e) {
                showError("Error", e.getMessage());
            }
        });
    }

    @FXML
    private void onEditLesson(ActionEvent event) {
        LessonRow sel = tableLessons.getSelectionModel().getSelectedItem();
        if (sel == null) { showError("Selecciona", "Selecciona una lección para editar."); return; }

        TextInputDialog dlgTitle = new TextInputDialog(sel.getTitle());
        dlgTitle.setHeaderText("Nuevo título");
        Optional<String> newTitleOpt = dlgTitle.showAndWait();

        if (newTitleOpt.isPresent()) {
            String newTitle = newTitleOpt.get().trim();
            TextInputDialog dlgType = new TextInputDialog(sel.getType());
            dlgType.setHeaderText("Nuevo tipo");
            Optional<String> newTypeOpt = dlgType.showAndWait();
            if (newTypeOpt.isPresent()) {
                String newType = newTypeOpt.get().trim();
                try {
                    courseController.updateLesson(courseId, moduleId, sel.getLessonNode().getData().getId(), newTitle, newType);
                    refreshTable();
                    showInfo("Actualizado", "Lección actualizada.");
                } catch (CourseNotFoundException | ModuleNotFoundException | LessonNotFoundException e) {
                    showError("No encontrado", e.getMessage());
                } catch (IllegalArgumentException e) {
                    showError("Entrada inválida", e.getMessage());
                } catch (Exception e) {
                    showError("Error", e.getMessage());
                }
            }
        }
    }

    @FXML
    private void onDeleteLesson(ActionEvent event) {
        // Borrar todas las seleccionadas (si hay)
        List<LessonRow> selected = observableLessons.stream().filter(LessonRow::isSelected).collect(Collectors.toList());
        if (selected.isEmpty()) {
            LessonRow single = tableLessons.getSelectionModel().getSelectedItem();
            if (single != null) selected = Collections.singletonList(single);
        }
        if (selected == null || selected.isEmpty()) { showError("Selecciona","Selecciona lecciones para eliminar."); return; }

        Alert conf = new Alert(Alert.AlertType.CONFIRMATION, "Eliminar " + selected.size() + " lección(es)?", ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> opt = conf.showAndWait();
        if (opt.isPresent() && opt.get() == ButtonType.OK) {
            StringBuilder errors = new StringBuilder();
            for (LessonRow r : selected) {
                String lid = r.getLessonNode().getData().getId();
                try {
                    courseController.deleteLesson(courseId, moduleId, lid);
                } catch (Exception e) {
                    errors.append("No se pudo eliminar ").append(lid).append(": ").append(e.getMessage()).append("\n");
                }
            }
            refreshTable();
            if (errors.length() > 0) showError("Algunos errores", errors.toString());
            else showInfo("Eliminadas", "Lecciones eliminadas correctamente.");
        }
    }

    // ---------- util ----------
    private void showError(String t, String m) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(t); a.setHeaderText(null); a.setContentText(m); a.showAndWait();
    }

    private void showInfo(String t, String m) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(t); a.setHeaderText(null); a.setContentText(m); a.showAndWait();
    }

    // ================== ROW WRAPPER ==================
    /**
     * Wrapper para manejar columnas (incluye 'done' para progreso).
     * No toca tu modelo.
     */
    public static class LessonRow {
        private final TreeNode<Lesson> lessonNode;
        private final StringProperty title = new SimpleStringProperty();
        private final StringProperty type  = new SimpleStringProperty();

        private final BooleanProperty selected = new SimpleBooleanProperty(false); // selección múltiple (tuya)
        private final BooleanProperty done     = new SimpleBooleanProperty(false); // progreso (NUEVA)

        public LessonRow(TreeNode<Lesson> node) {
            this.lessonNode = node;
            this.title.set(node.getData().getTitle());
            this.type.set(node.getData().getType());
        }

        public TreeNode<Lesson> getLessonNode() { return lessonNode; }

        public StringProperty  titleProperty()   { return title; }
        public StringProperty  typeProperty()    { return type;  }
        public BooleanProperty selectedProperty(){ return selected; }
        public BooleanProperty doneProperty()    { return done; }

        public void setSelected(boolean v) { selected.set(v); }
        public boolean isSelected()        { return selected.get(); }

        public void setDone(boolean v)     { done.set(v); }
        public boolean isDone()            { return done.get(); }

        public String getTitle()           { return title.get(); }
        public String getType()            { return type.get(); }
    }

    // Helper simple para devolver 3 valores del dialogo
    private static class Triple {
        final String id, title, type;
        Triple(String id, String title, String type) { this.id=id; this.title=title; this.type=type; }
    }



}

