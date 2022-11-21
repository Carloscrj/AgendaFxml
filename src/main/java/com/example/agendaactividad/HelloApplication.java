package com.example.agendaactividad;

import java.io.File;
import java.io.IOException;

import com.example.agendaactividad.controlador.PersonEditDialogController;
import com.example.agendaactividad.controlador.PersonOverviewController;
import com.example.agendaactividad.controlador.RootLayoutController;
import com.example.agendaactividad.modelo.PersonListWrapper;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.example.agendaactividad.modelo.Person;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.util.prefs.Preferences;


public class HelloApplication extends Application {
    private static final String APP_XML = "RootLayout.fxml";
    private static final String USER_MENU_XML = "PersonOverview.fxml";
    private static final String EDIT_DIALOG_XML = "PersonEditDialog.fxml";
    private Stage stage;


    private AnchorPane menu;
    private BorderPane rootLayout;

    //Los datos como lista observable de Personas.
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        stage.setTitle("Agenda");
        stage.getIcons().add(new Image("file:src/main/resources/com/example/agendaactividad/images/iconoAgenda.png"));
        loadLayouts();
        Scene scene = new Scene(rootLayout);
        stage.setScene(scene);

        stage.show();

        // Try to load last opened person file.
        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }

    private void loadLayouts() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource(APP_XML));
            rootLayout = loader.load();

            FXMLLoader loader2 = new FXMLLoader();
            loader2.setLocation(HelloApplication.class.getResource(USER_MENU_XML));
            menu = loader2.load();


            rootLayout.setCenter(menu);

            // Dale al controlador acceso a la aplicación principal.
            PersonOverviewController controller = loader2.getController();
            controller.setHelloApplication(this);

            RootLayoutController controller2 = loader.getController();
            controller2.setHelloApplication(this);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Constructor
    public HelloApplication() {
        personData.add(new Person("Hans", "Muster"));
        personData.add(new Person("Ruth", "Mueller"));
        personData.add(new Person("Heinz", "Kurz"));
        personData.add(new Person("Cornelia", "Meier"));
        personData.add(new Person("Werner", "Meyer"));
        personData.add(new Person("Lydia", "Kunz"));
        personData.add(new Person("Anna", "Best"));
        personData.add(new Person("Stefan", "Meier"));
        personData.add(new Person("Martin", "Mueller"));
    }

    //Devuelve los datos como una lista observable de Personas.
    public ObservableList<Person> getPersonData() {
        return personData;
    }

    public Stage getStage() {
        return stage;
    }


    /**
     *Abre un diálogo para editar los detalles de la persona especificada. Si el usuario
     * hace clic en Aceptar, los cambios se guardan en el objeto persona proporcionado y se devuelve true
     * se devuelve.
     *
     * @param person el objeto persona a editar
     * @return true si el usuario hizo clic en OK, false en caso contrario.
     */
    public boolean showPersonEditDialog(Person person) {
        try {
            // Cargue el archivo fxml y cree un nuevo escenario para el diálogo emergente.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Crear el escenario de diálogo.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Coloca a la persona en el controlador.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // Mostrar el diálogo y esperar a que el usuario lo cierre
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the person file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     *
     * @return
     */
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(HelloApplication.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     *
     * @param file the file or null to remove the path
     */
    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(HelloApplication.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            stage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            stage.setTitle("HOLA");
        }
    }

    /**
     * Loads person data from the specified file. The current person data will
     * be replaced.
     *
     * @param file
     */

    public void loadPersonDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);

            personData.clear();
            personData.addAll(wrapper.getPersons());

            // Save the file path to the registry.
            setPersonFilePath(file);

        } catch (Exception e) { // catches ANY exception

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Could not load data from file:\\n\" + file.getPath()");

            alert.showAndWait();

        }
    }

    /**
     * Saves the current person data to the specified file.
     *
     * @param file
     */
    public void savePersonDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            PersonListWrapper wrapper = new PersonListWrapper();
            wrapper.setPersons(personData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setPersonFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();

        }
    }



    public static void main(String[] args) {
        launch();
    }

}