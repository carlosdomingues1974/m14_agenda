module com.school.m14_agenda {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.school.m14_agenda to javafx.fxml;
    exports com.school.m14_agenda;
}