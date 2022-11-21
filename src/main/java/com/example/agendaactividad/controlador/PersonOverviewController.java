package com.example.agendaactividad.controlador;

import com.example.agendaactividad.HelloApplication;
import com.example.agendaactividad.modelo.Person;
import com.example.agendaactividad.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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

        // Clear person details.
        showPersonDetails(null);

        // Listen for selection changes and show the person details when changed.
        tblName.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
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

    private void showPersonDetails(Person person) {
        if (person != null) {
            // Rellena las etiquetas con la información del objeto persona.
            lblFirstName.setText(person.getFirstName());
            lblLastName.setText(person.getLastName());
            lblStreet.setText(person.getStreet());
            lblPostal.setText(Integer.toString(person.getPostalCode()));
            lblCity.setText(person.getCity());

            // TODO: We need a way to convert the birthday into a String!
            lblBirthday.setText(DateUtil.format(person.getBirthday()));

            // birthdayLabel.setText(...);
        } else {
            // Persona es nula, elimina todo el texto.
            lblFirstName.setText("");
            lblLastName.setText("");
            lblStreet.setText("");
            lblPostal.setText("");
            lblCity.setText("");
            lblBirthday.setText("");
        }
    }

    /**
     * Se llama cuando el usuario hace clic en el botón de borrar.
     */
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = tblName.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0){
            tblName.getItems().remove(selectedIndex);
        }else {
            // No se ha seleccionado nada.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(helloApplication.getStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }

    }

    /**
     * Se llama cuando el usuario hace clic en el botón nuevo. Abre un diálogo para editar
     * detalle para la nueva Persona
     */
    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();
        boolean okClicked = helloApplication.showPersonEditDialog(tempPerson);
        if (okClicked) {
            helloApplication.getPersonData().add(tempPerson);
        }
    }

    /**
     *
     * Se llama cuando el usuario hace clic en el botón de edición. Abre un cuadro de diálogo para editar
     * detalles para la persona seleccionada.
     */
    @FXML
    private void handleEditPerson() {
        Person selectedPerson = tblName.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = helloApplication.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }

        } else {
            // nada seleccionado
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(helloApplication.getStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }


}
