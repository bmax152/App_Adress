package mb.makery.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import mb.makery.address.MainApp;
import mb.makery.address.model.Person;
import mb.makery.address.util.DateUtil;

public class PersonOverviewController {

		@FXML
	    private TableView<Person> personTable;
	    @FXML
	    private TableColumn<Person, String> firstNameColumn;
	    @FXML
	    private TableColumn<Person, String> lastNameColumn;

	    @FXML
	    private Label firstNameLabel;
	    @FXML
	    private Label lastNameLabel;
	    @FXML
	    private Label streetLabel;
	    @FXML
	    private Label postalCodeLabel;
	    @FXML
	    private Label cityLabel;
	    @FXML
	    private Label birthdayLabel;

	    // Reference to the main application.
	    private MainApp mainApp;

	    /**
	     * The constructor.
	     * The constructor is called before the initialize() method.
	     */
	    public PersonOverviewController() {
	    }

	    /**
	     * Initializes the controller class. This method is automatically called
	     * after the fxml file has been loaded.
	     */
	    @FXML
	    private void initialize() {
	    	// Initialize the person table with the two columns.
	        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
	        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
	        
	        // On réinitialise
	        showPersonDetails(null);

	        // On change les détails en fonction du champs selectionner
	        personTable.getSelectionModel().selectedItemProperty().addListener(
	        		(observable, oldValue, newValue) -> showPersonDetails(newValue));
	    }

	    /**
	     * Is called by the main application to give a reference back to itself.
	     * 
	     * @param mainApp
	     */
	    public void setMainApp(MainApp mainApp) {
	        this.mainApp = mainApp;

	        // Add observable list data to the table
	        personTable.setItems(mainApp.getPersonData());
	    }
	    
	    private void showPersonDetails(Person person) {
	    	
	    	if(person != null) {
	    		firstNameLabel.setText(person.getFirstName());
	            lastNameLabel.setText(person.getLastName());
	            streetLabel.setText(person.getStreet());
	            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
	            cityLabel.setText(person.getCity());
	            
	            //On utilise la classe créer dans utile pour transformer la date en string avec le pattern choisis 
	            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
	    	}else {
	    		firstNameLabel.setText("");
	            lastNameLabel.setText("");
	            streetLabel.setText("");
	            postalCodeLabel.setText("");
	            cityLabel.setText("");
	            birthdayLabel.setText("");
	    	}
	    }
	    
	    //Bouton de suppression de personne 
	    
	    @FXML
	    private void handleDeletePerson() {
	    	//Récupere le l'indexe de la personne selectionner dans la liste
	    	int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
	    	
	    	if(selectedIndex >= 0) {
	    		//Supprime la personne à l'index selectionner
		    	personTable.getItems().remove(selectedIndex);
	    	}else {
	    		//Si personne n'est selectionner, on affiche une alerte!
	    		Alert alert = new Alert(AlertType.WARNING);
	            alert.initOwner(mainApp.getPrimaryStage());
	            alert.setTitle("**Aucune Selection**");
	            alert.setHeaderText("Aucune Personne de Sélectionner");
	            alert.setContentText("S'il vous plait, veuillez sélectionner une personne.");
	            alert.showAndWait();
	    	}
	    	
	    }
	    
	    /**
	     * Called when the user clicks the new button. Opens a dialog to edit
	     * details for a new person.
	     */
	    @FXML
	    private void handleNewPerson() {
	        Person tempPerson = new Person();
	        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
	        if (okClicked) {
	            mainApp.getPersonData().add(tempPerson);
	        }
	    }

	    /**
	     * Called when the user clicks the edit button. Opens a dialog to edit
	     * details for the selected person.
	     */
	    @FXML
	    private void handleEditPerson() {
	        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
	        if (selectedPerson != null) {
	            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
	            if (okClicked) {
	                showPersonDetails(selectedPerson);
	            }

	        } else {
	            // Nothing selected.
	            Alert alert = new Alert(AlertType.WARNING);
	            alert.initOwner(mainApp.getPrimaryStage());
	            alert.setTitle("No Selection");
	            alert.setHeaderText("No Person Selected");
	            alert.setContentText("Please select a person in the table.");

	            alert.showAndWait();
	        }
	    }
}
