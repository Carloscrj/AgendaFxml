package com.example.agendaactividad;

import java.io.IOException;

import com.example.agendaactividad.controlador.PersonOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.example.agendaactividad.modelo.Person;


public class HelloApplication extends Application {
    private static final String APP_XML = "RootLayout.fxml";
    private static final String USER_MENU_XML = "PersonOverview.fxml";

    private AnchorPane menu;
    private BorderPane rootLayout;

    //Los datos como lista observable de Personas.
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Agenda");
        loadLayouts();
        Scene scene = new Scene(rootLayout);
        stage.setScene(scene);
        stage.show();
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


    public static void main(String[] args) {
        launch();
    }

}