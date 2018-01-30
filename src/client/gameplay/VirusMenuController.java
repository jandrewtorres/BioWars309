package client.gameplay;

import java.util.logging.Level;
import java.util.logging.Logger;

import client.Client;
import client.model.ClientModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class VirusMenuController {
    private static final Logger clientLogger = Logger.getLogger(Client.class.getName());

	ClientModel model;
	Client clientApp;
	public BooleanProperty actionTaken = new SimpleBooleanProperty();

	@FXML
	private Button exitButton;
	
	public VirusMenuController(ClientModel model) {
		this.model = model;
	}
	
	@FXML
	private void initialize() {
		// Nothing here ... yet
	}
	
	@FXML
	private void exitMenu(ActionEvent event) {
		try {
			clientApp.closeVirusMenu(exitButton);
		}
		catch(Exception e) {
			clientLogger.logp(Level.SEVERE, VirusMenuController.class.getName(), "exitMenu", "Exception closing the virus menu");
		}
	}
	
	public void setClientApp(Client app) {
		this.clientApp = app;
	}
	
}
