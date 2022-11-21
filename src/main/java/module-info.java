module com.example.agendaactividad {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;
    requires java.xml.bind;


    exports com.example.agendaactividad;
    opens com.example.agendaactividad to javafx.fxml;
    exports com.example.agendaactividad.controlador;
    opens com.example.agendaactividad.controlador to javafx.fxml;
    opens com.example.agendaactividad.modelo to java.xml.bind;
    exports com.example.agendaactividad.util;



}