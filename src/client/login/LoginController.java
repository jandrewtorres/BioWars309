package client.login;

import java.io.Serializable;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import client.Client;
import client.ServerCommunicator;
import client.lobby.LobbyController;
import client.model.ClientModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
