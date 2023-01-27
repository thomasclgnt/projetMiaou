module projetMiaou {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens frontend to javafx.fxml;

    exports udp;
    exports tcp;
    exports bdd;
    exports frontend;
    exports data;
    exports service;
  
}
