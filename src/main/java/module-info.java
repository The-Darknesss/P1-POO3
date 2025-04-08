module com.example.prova2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.prova2 to javafx.fxml;
    exports com.example.prova2;
}