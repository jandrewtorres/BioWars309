package client.gameplay;

import java.util.logging.Level;
import java.util.logging.Logger;

import client.Client;
import client.model.ClientModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class VirusMktController {
    private static final Logger clientLogger = Logger.getLogger(Client.class.getName());

	private ClientModel model;
	private Client clientApp;

	@FXML
	private Button exitButton;
	
	public VirusMktController(ClientModel model) {
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
		}catch(Exception e) {
			clientLogger.logp(Level.SEVERE, VirusMktController.class.getName(), "exitMenu", "Exception closing virus market menu");
		}
	}
	
	public void setClientApp(Client app) {
		this.clientApp = app;
	}
}
