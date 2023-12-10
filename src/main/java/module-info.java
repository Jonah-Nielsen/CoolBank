module com.example.cool_bank {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires java.sql;


    opens com.example.cool_bank to javafx.fxml;
    exports com.example.cool_bank;
}