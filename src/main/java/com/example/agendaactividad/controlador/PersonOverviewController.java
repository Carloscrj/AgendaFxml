package com.example.agendaactividad.controlador;

import com.example.agendaactividad.HelloApplication;
import com.example.agendaactividad.modelo.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PersonOverviewController {

    // Referencia a la aplicación principal.
    private HelloApplication helloApplication;
    
    @FXML
    private TableView<Person> tblName;
    @FXML
    private TableColumn<Person, String> tbclColFirst;
    @FXML
    private TableColumn<Person, String> tbclColLast;

    @FXML
    private Label lblFirstName;
    @FXML
    private Label lblLastName;
    @FXML
    private Label lblStreet;
    @FXML
    private Label lblPostal;
    @FXML
    private Label lblCity;
    @FXML
    private Label lblBirthday;

    

    /**
     * El constructor
     * El constructor se llama antes que el método initialize().
     */
    public PersonOverviewController() {
    }

    /**
     * Inicializa la clase de controlador. Este método se llama automáticamente
     * después de que se haya cargado el archivo fxml.
     */
    @FXML
    private void initialize() {
        // Inicialice la tabla de personas con las dos columnas.
        tbclColFirst.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        tbclColLast.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
    }

    /**
     * Es llamado por la aplicación principal para devolver una referencia a sí mismo.
     *
     * @param helloApplication
     */
    public void setHelloApplication(HelloApplication helloApplication) {
        this.helloApplication = helloApplication;

        // Agregar datos de lista observables a la tabla
        tblName.setItems(helloApplication.getPersonData());
    }
}
