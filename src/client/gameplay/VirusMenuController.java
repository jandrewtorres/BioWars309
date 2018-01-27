package client.gameplay;

import client.Client;
import client.model.ClientModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class VirusMenuController {
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
		}catch(Exception e) {
			System.out.println("can't close submenu");
		}
	}
	
	public void setClientApp(Client app) {
		this.clientApp = app;
	}
	
}
