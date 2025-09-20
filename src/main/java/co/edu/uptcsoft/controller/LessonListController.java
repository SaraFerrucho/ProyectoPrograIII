package co.edu.uptcsoft.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import co.edu.uptcsoft.model.Lesson;
import javafx.collections.*;

public class LessonListController {

    @FXML private TableView<Lesson> tableLessons;
    @FXML private TableColumn<Lesson, Boolean> colSelect;
    @FXML private TableColumn<Lesson, String> colLessonName;
    @FXML private TableColumn<Lesson, String> colLessonType;

    @FXML private CheckBox chkSelectAll;

    @FXML private Button btnAddLesson;
    @FXML private Button btnDeleteLesson;
    @FXML private Button btnEditLesson;
    @FXML private Button btnBack;

    private ObservableList<Lesson> lessonList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // columna con checkbox
        colSelect.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        colSelect.setCellFactory(CheckBoxTableCell.forTableColumn(colSelect));

        // nombre de la leccion
        colLessonName.setCellValueFactory(new PropertyValueFactory<>("title"));

        // tipo de leccion
        colLessonType.setCellValueFactory(new PropertyValueFactory<>("type"));
        // setear lista
        tableLessons.setItems(lessonList);

        // manejar checkbox global
        chkSelectAll.setOnAction(e -> {
            boolean selected = chkSelectAll.isSelected();
            for (Lesson lesson : lessonList) {
                lesson.setSelected(selected);
            }
            tableLessons.refresh();
        });

        // botones (ejemplo simple, se implementan luego)
        btnAddLesson.setOnAction(e -> handleAddLesson());
        btnDeleteLesson.setOnAction(e -> handleDeleteLesson());
        btnEditLesson.setOnAction(e -> handleEditLesson());
    }

    private void handleAddLesson() {
        // TODO abrir ventana para agregar una nueva leccion
        System.out.println("Añadir lección");
    }

    private void handleDeleteLesson() {
        lessonList.removeIf(Lesson::isSelected);
    }

    private void handleEditLesson() {
        Lesson selected = tableLessons.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // TODO abrir ventana para editar tipo de leccion
     System.out.println("Editar tipo de lección: " + selected.getTitle());

        }
    }

    // metodo para cargar las lecciones del modulo actual
    public void loadLessons(ObservableList<Lesson> lessons) {
        lessonList.setAll(lessons);
    }
}
