package co.edu.uptcsoft.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    private static Stage mainStage;
    private static String mainView = "MainView.fxml"; // ruta de tu vista principal

    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    public static void switchScene(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/" + fxml));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 🔙 Método para regresar al menú principal
    public static void goBack() {
        switchScene(mainView);
    }
}
