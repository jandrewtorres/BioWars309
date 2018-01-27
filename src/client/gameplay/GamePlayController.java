package client.gameplay;

import client.Client;
import client.model.ClientModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class GamePlayController {
	@FXML
	Button virusIcon;
	
	ClientModel model;

	Client clientApp;
	
	public GamePlayController(ClientModel model) {
		this.model = model;
	}
	
	@FXML
	private void initialize() {
		// Nothing here... yet
	}
	
	@FXML
	private void openVirusMenu(ActionEvent event) {
		try {
			clientApp.openVirusMenu();
		}catch(Exception e) {
			System.out.println("error opening submenu");
		}

	}
	
	public void setClientApp(Client app) {
		this.clientApp = app;
	}
}

