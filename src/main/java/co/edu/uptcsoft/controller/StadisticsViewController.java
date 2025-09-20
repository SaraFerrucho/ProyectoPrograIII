package co.edu.uptcsoft.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;

public class StadisticsViewController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private Button btnBack;

    @FXML
    private void initialize() {
        // EJEMPLO DE DATOS DE PRUEBA
        XYChart.Series<String, Number> data = new XYChart.Series<>();
        data.setName("Estudiantes por curso");
        data.getData().add(new XYChart.Data<>("programacion", 25));
        data.getData().add(new XYChart.Data<>("Bases de datos", 18));
        data.getData().add(new XYChart.Data<>("Electiva I", 40));
        data.getData().add(new XYChart.Data<>("Ingles", 30));

        barChart.getData().add(data);
    }

    @FXML
    private void handleBack() {
        System.out.println("Volviendo al menu principal...");
        // aqui se puede cargar el mainview
    }
}
