module com.mycompany.schronisko {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
//    requires org.hibernate.orm.core;
//    requires doyto.query.api;

    opens com.mycompany.schronisko to javafx.fxml;
    opens com.mycompany.schronisko.models to org.hibernate.orm.core;
    exports com.mycompany.schronisko;
    exports com.mycompany.schronisko.models;
    exports com.mycompany.controllers;
    opens com.mycompany.controllers to javafx.fxml,org.hibernate.orm.core;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires static lombok;
    requires java.naming;
    requires org.postgresql.jdbc;
    requires java.validation;
    requires java.desktop;
}
