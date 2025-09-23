package co.edu.uptcsoft.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    private static Stage mainStage;  // La ventana principal
    private static String mainView = "co/edu/uptcsoft/view/MainView.fxml"; // Vista principal

    // Se llama desde App.java al iniciar
    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    // Cambiar la escena en la misma ventana
    public static void switchScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getClassLoader().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Regresar al menú principal
    public static void goBack() {
        switchScene(mainView);
    }
}
