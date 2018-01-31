package client.gameplay;

import client.Client;
import client.model.ClientModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import server.model.Player;

public class VirusMenuController {
	ClientModel model;
	Client clientApp;

	@FXML
	private Button exitButton;
	@FXML
	private Button useVirus;
	@FXML
	private ChoiceBox<Player> coldTarget;
	@FXML
	private ChoiceBox<Player> fluTarget;
	@FXML
	private ChoiceBox<Player> poxTarget;
	@FXML
	private ChoiceBox<Player> sarsTarget;
	
	public VirusMenuController(ClientModel model) {
		this.model = model;
	}
	
	@FXML
	private void initialize() {
		coldTarget.setItems(model.players);
		fluTarget.setItems(model.players);
		poxTarget.setItems(model.players);
		sarsTarget.setItems(model.players);
	}
	@FXML
	private void applyVirus() {
		//nothing yet
	}
	@FXML
	private void exitMenu(ActionEvent event) {
		try {
			clientApp.closeMenu(exitButton);
		}catch(Exception e) {
			System.out.println("can't close submenu");
		}
	}
	
	public void setClientApp(Client app) {
		this.clientApp = app;
	}
	
}
