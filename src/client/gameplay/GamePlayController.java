package client.gameplay;

import client.ServerCommunicator;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;

public class GamePlayController {
	ServerCommunicator model;
	@FXML
	private ProgressBar virusProg;
	@FXML 
	private ProgressBar cureProg;
	
	public GamePlayController(ServerCommunicator model) {
		this.model = model;
	}
	
	@FXML
	private void initialize() {
		virusProg.getStyleClass().add("hide");
		cureProg.getStyleClass().add("hide");
	}
}
