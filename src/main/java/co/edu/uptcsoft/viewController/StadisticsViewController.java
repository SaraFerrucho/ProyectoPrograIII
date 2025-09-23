package co.edu.uptcsoft.viewController;

import java.util.List;

import co.edu.uptcsoft.controller.CourseController;
import co.edu.uptcsoft.controller.StudentController;
import co.edu.uptcsoft.model.Course;
import co.edu.uptcsoft.model.Student;
import co.edu.uptcsoft.model.TreeNode;
import co.edu.uptcsoft.service.ProgressService;
import co.edu.uptcsoft.util.AppContext;
import co.edu.uptcsoft.util.SceneManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

public class StadisticsViewController {

    @FXML private BarChart<String, Number> barChart;
    @FXML private Label lblStudent;                 // opcional: puedes quitarlo del FXML si no lo usas
    @FXML private ComboBox<Student> cmbStudent;     // NUEVO selector de estudiante

    // Reutiliza el mismo "árbol" que el resto
    private final CourseController  courseController  = new CourseController();
    private final StudentController studentController = new StudentController();
    private final ProgressService   progressService   = new ProgressService(courseController.getCoursesManager());

    @FXML
    public void initialize() {
        // Cargar datos base
        courseController.loadData();
        studentController.loadData();

        // Poblar combo con estudiantes
        List<Student> students = studentController.listStudents();
        cmbStudent.setItems(FXCollections.observableArrayList(students));

        // Mostrar nombre bonito en el combo
        cmbStudent.setCellFactory(cb -> new ListCell<>() {
            @Override protected void updateItem(Student s, boolean empty) {
                super.updateItem(s, empty);
                setText(empty || s == null ? "" : s.getName() + " (ID " + s.getId() + ")");
            }
        });
        cmbStudent.setButtonCell(new ListCell<>() {
            @Override protected void updateItem(Student s, boolean empty) {
                super.updateItem(s, empty);
                setText(empty || s == null ? "" : s.getName() + " (ID " + s.getId() + ")");
            }
        });

        // Preseleccionar si vienes desde Estudiantes (AppContext)
        if (AppContext.currentStudentId != 0) {
            students.stream()
                    .filter(s -> s.getId() == AppContext.currentStudentId)
                    .findFirst()
                    .ifPresent(cmbStudent::setValue);
        }

        // Recalcular cuando cambie la selección
        cmbStudent.valueProperty().addListener((obs, oldV, newV) -> loadChartForSelected());

        // Primera carga (si ya hay estudiante seleccionado)
        loadChartForSelected();
    }

    private void loadChartForSelected() {
        barChart.getData().clear();

        Student sel = cmbStudent.getValue();
        if (sel == null) {
            if (lblStudent != null) lblStudent.setText("Selecciona un estudiante.");
            return;
        }

        if (lblStudent != null) lblStudent.setText("Estudiante actual: " + sel.getId());

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Progreso (%)");

        for (TreeNode<Course> node : courseController.getCourses()) {
            Course c = node.getData();
            double pct = progressService.coursePercentage(sel.getId(), c.getId()); // 0..100
            double r1 = Math.round(pct * 10.0) / 10.0;
            series.getData().add(new XYChart.Data<>(c.getTitle(), r1));
        }

        barChart.getData().add(series);
    }

    @FXML
    private void onRefresh() {
        // Recalcular con el estudiante actual del combo
        loadChartForSelected();
    }

    @FXML
    private void onShowDetail() {
        Student sel = cmbStudent.getValue();
        if (sel == null) {
            new Alert(Alert.AlertType.INFORMATION, "Seleccione un estudiante.").showAndWait();
            return;
        }
        StringBuilder sb = new StringBuilder("Porcentaje por curso (ID " + sel.getId() + "):\n");
        for (TreeNode<Course> node : courseController.getCourses()) {
            Course c = node.getData();
            double pct = Math.round(progressService.coursePercentage(sel.getId(), c.getId()) * 10.0) / 10.0;
            sb.append(" - ").append(c.getId()).append(" (").append(c.getTitle()).append("): ")
              .append(pct).append(" %\n");
        }
        new Alert(Alert.AlertType.INFORMATION, sb.toString()).showAndWait();
    }

    @FXML
    private void goBackToMain() {
        SceneManager.switchScene("co/edu/uptcsoft/view/MainView.fxml");
    }
}

