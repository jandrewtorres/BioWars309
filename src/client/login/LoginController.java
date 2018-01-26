package client.login;

import java.io.Serializable;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
	
	public LoginController(ClientModel model) {
		this.model = model;
	}
	
	@FXML
	private void initialize() {
		
	}
	
	@FXML
	private void onEnterButtonClicked(ActionEvent event) throws Exception {
		if(model.registerClient(screenNameTextField.getText())) {
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			
			LobbyController controller = new LobbyController(model);
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/client/lobby/Lobby.fxml"));
			loader.setController(controller);
			Parent root = (Parent) loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
		}
	}
}
