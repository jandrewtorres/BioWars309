package client.gameplay;

import java.util.logging.Level;
import java.util.logging.Logger;

import client.Client;
import client.model.ClientModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import server.model.Player;

public class VirusMenuController {
    private static final Logger clientLogger = Logger.getLogger(Client.class.getName());

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
		coldTarget.setItems(model.getPlayers());
		fluTarget.setItems(model.getPlayers());
		poxTarget.setItems(model.getPlayers());
		sarsTarget.setItems(model.getPlayers());
	}
	@FXML
	private void applyVirus() {
		//nothing yet
	}
	@FXML
	private void exitMenu(ActionEvent event) {
		try {
			clientApp.closeMenu(exitButton);
		}
		catch(Exception e) {
			clientLogger.logp(Level.SEVERE, VirusMenuController.class.getName(), "exitMenu", "Exception closing the virus menu");
		}
	}
	
	public void setClientApp(Client app) {
		this.clientApp = app;
	}
	
}
