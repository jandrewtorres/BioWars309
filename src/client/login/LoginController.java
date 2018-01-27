package client.login;

import client.Client;
import client.model.ClientModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {
	ClientModel model;
	@FXML
	Button enterButton;
	@FXML
	TextField screenNameTextField;
	
	Client clientApp;
	
	public LoginController(ClientModel model) {
		this.model = model;
	}
	
	@FXML
	private void initialize() {
		// Nothing here... yet
	}
	
	@FXML
	private void onEnterButtonClicked(ActionEvent event) {
		if(model.registerClient(screenNameTextField.getText())) {			
			try {
				clientApp.switchToLobby();
			} catch (Exception e) {
				System.out.println("Exception in switching to Lobby scene");
			}
		}
	}
	
	public void setClientApp(Client app) {
		this.clientApp = app;
	}
}
