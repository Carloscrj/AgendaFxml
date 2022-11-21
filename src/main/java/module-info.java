module com.example.agendaactividad {
    requires javafx.controls;
    requires javafx.fxml;


    exports com.example.agendaactividad;
    opens com.example.agendaactividad to javafx.fxml;
    exports com.example.agendaactividad.controlador;
    opens com.example.agendaactividad.controlador to javafx.fxml;

}