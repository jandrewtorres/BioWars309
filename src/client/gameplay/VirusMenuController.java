package client.gameplay;

import client.Client;
import client.model.ClientModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class VirusMenuController {
	@FXML
	private Button exitButton;
	ClientModel model;
	
	Client clientApp;
	public BooleanProperty actionTaken = new SimpleBooleanProperty();
	
	public VirusMenuController(ClientModel model) {
		this.model = model;
	}
	public BooleanProperty actionTakenProperty() {
		return actionTaken;
	}
	public boolean ifActionTaken() {
		return actionTakenProperty().get();
	}
	@FXML
	private void initialize() {

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
