package client.gameplay;

import java.util.logging.Level;
import java.util.logging.Logger;

import client.Client;
import client.model.ClientModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class GamePlayController {
    private static final Logger clientLogger = Logger.getLogger(Client.class.getName());

	@FXML
	Button virusIcon;
	@FXML
	Button cureIcon;
	@FXML
	Button virusBuy;
	
	ClientModel model;

	Client clientApp;
	
	public GamePlayController(ClientModel model) {
		this.model = model;
	}
	
	@FXML
	private void initialize() {
		// Nothing here... yet...
	}
	
	@FXML
	private void openVirusMenu(ActionEvent event) {
		try {
			clientApp.openVirusMenu();
		}catch(Exception e) {
			clientLogger.logp(Level.SEVERE, GamePlayController.class.getName(), "openVirusMenu", "Exception opening virus menu");
		}

	}
	@FXML
	private void openVirusMkt(ActionEvent event) {
		try {
			clientApp.openVirusMkt();
		}catch(Exception e) {
			clientLogger.logp(Level.SEVERE, GamePlayController.class.getName(), "openVirusMkt", "Exception opening virus market");
		}

	}
	public void setClientApp(Client app) {
		this.clientApp = app;
	}
}

