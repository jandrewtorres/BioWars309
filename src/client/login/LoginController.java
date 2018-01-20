package client.login;

import client.ClientModel;
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
		model.transmitCommand(screenNameTextField.getText());
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
