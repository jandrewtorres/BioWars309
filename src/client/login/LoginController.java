package client.login;

import java.util.logging.Level;
import java.util.logging.Logger;

import client.Client;
import client.model.ClientModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {
    private static final Logger clientLogger = Logger.getLogger(Client.class.getName());

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
				clientLogger.logp(Level.SEVERE, LoginController.class.getName(), "onEnterButtonClicked", "Exception switching to lobby scene");
			}
		}
	}
	
	public void setClientApp(Client app) {
		this.clientApp = app;
	}
}
