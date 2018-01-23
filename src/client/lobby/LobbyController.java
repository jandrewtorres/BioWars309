package client.lobby;

import client.ServerCommunicator;
import javafx.fxml.FXML;

public class LobbyController {
	ServerCommunicator model;
	
	public LobbyController(ServerCommunicator model) {
		this.model = model;
	}
	
	@FXML
	private void initialize() {
		
	}
}
