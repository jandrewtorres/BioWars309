package server;

import java.util.Observable;
import java.util.Observer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import server.model.GameModel;

public class ServerViewerController implements Observer {
	
	@FXML
	private Label numClientsLabel;
	
	private GameModel game;
	
	public ServerViewerController() {
		
	}
	
	@FXML
	private void initialize() {
		
	}
	
	public void setModel(GameModel game) {
		this.game = game;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println("out");
		this.numClientsLabel.setText(Integer.toString(game.players.size()));
	}
}
