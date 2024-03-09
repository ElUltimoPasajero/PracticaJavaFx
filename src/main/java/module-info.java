module com.example.practicajavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jasperreports;
    requires java.desktop;


    opens com.example.practicajavafx to javafx.fxml;
    exports com.example.practicajavafx;
}