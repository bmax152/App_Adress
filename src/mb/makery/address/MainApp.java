package mb.makery.address;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mb.makery.address.model.Person;
import mb.makery.address.view.PersonEditDialogController;
import mb.makery.address.view.PersonOverviewController;

public class MainApp extends Application {

	//Permet de r�cuperer le stage et le rootLayout (de la premi�re vue)
    private Stage primaryStage;
    private BorderPane rootLayout;


 	/**
 	 * The data as an observable list of Persons.
 	 */
 	private ObservableList<Person> personData = FXCollections.observableArrayList();

 	/**
 	 * Constructor
 	 */
 	public MainApp() {
 		// Add some sample data
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
   
 	/**
 	 * Returns the data as an observable list of Persons. 
 	 * @return
 	 */
 	public ObservableList<Person> getPersonData() {
 		return personData;
 	}
   
     
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        //Cr�e l'icone de la fen�tre!
        this.primaryStage.getIcons().add(new Image("file:resources/images/carnet.png"));
        //Permet de cr�er la fen�tre qui va englober la vue
        initRootLayout();
        //Ajoute la seconde vue au centre de la premi�re
        showPersonOverview();
    }
    
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Charge la 1ere vue du fkml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Montre la seconde vue dans la 1ere
     */
    public void showPersonOverview() {
        try {
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            
            // Attache la seconde vue au cenrtre de la premi�re 
            rootLayout.setCenter(personOverview);
            
            //Acc�s aux controlleur
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     *
     * @param person the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showPersonEditDialog(Person person) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modifier une Personne");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
