module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    //opens com.example.library to javafx.fxml;
    opens com.example.library;
    exports com.example.library;

    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    requires java.naming;
}