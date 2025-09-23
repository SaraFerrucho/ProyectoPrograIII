package co.edu.uptcsoft;

import co.edu.uptcsoft.util.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        SceneManager.setStage(stage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uptcsoft/view/MainView.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Proyecto Progra III");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
