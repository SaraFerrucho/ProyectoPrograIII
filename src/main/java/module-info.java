module co.edu.uptcsoft {
    requires javafx.controls;
    requires javafx.fxml;

    opens co.edu.uptcsoft to javafx.fxml;
    exports co.edu.uptcsoft;
}
