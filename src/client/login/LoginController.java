package client.login;

import java.io.Serializable;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import client.ServerCommunicator;
import client.lobby.LobbyController;
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
	ServerCommunicator communicator;
	@FXML
	Button enterButton;
	@FXML
	TextField screenNameTextField;
	
	public LoginController(ServerCommunicator communicator) {
		this.communicator = communicator;
	}
	
	@FXML
	private void initialize() {
		
	}
	
	@FXML
	private void onEnterButtonClicked(ActionEvent event) throws Exception {
		if(registerClient(screenNameTextField.getText())) {
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			
			LobbyController controller = new LobbyController(communicator);
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/client/lobby/Lobby.fxml"));
			loader.setController(controller);
			Parent root = (Parent) loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
		}
	}
	
	private Boolean registerClient(String clientName) {
		Boolean registered = false;
		try {
			// Create a new document
			// <REGISTER>
			//    <NAME>David</NAME>
			// </REGISTER>
			Document messageDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

			Element registerElem = messageDoc.createElement("REGISTER");
			
			Element nameElem = messageDoc.createElement("NAME");
			nameElem.appendChild(messageDoc.createTextNode(clientName));
			registerElem.appendChild(nameElem);
			
			messageDoc.appendChild(registerElem);
			
			communicator.transmitMessage(messageDoc);
			registered = true;
		} catch (ParserConfigurationException e) {
			System.out.println("Exception in registering client");
		}
		return registered;
	}
}
