package client.gameplay;

import client.model.ClientModel;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;

public class GamePlayController {
	ClientModel model;
	@FXML
	private ProgressBar virusProg;
	@FXML 
	private ProgressBar cureProg;
	
	public GamePlayController(ClientModel model) {
		this.model = model;
	}
	
	@FXML
	private void initialize() {
		virusProg.getStyleClass().add("hide");
		cureProg.getStyleClass().add("hide");
	}
}
