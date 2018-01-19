package server.viewer;

import java.util.Observable;
import java.util.Observer;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import server.model.GameModel;
import server.model.Player;

public class ServerViewerController {
	
	@FXML
	private TableView<Player> playerTable;
	@FXML
	private TableColumn<Player, String> playerNameColumn;
	@FXML
	private TableColumn<Player, Integer> goldColumn;
	@FXML
	private Label gameStatusLabel;
	@FXML
	private Label gameTimeLabel;
	
	private GameModel game;
	
	public ServerViewerController() {
		
	}
	
	@FXML
	private void initialize() {
		playerNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		goldColumn.setCellValueFactory(cellData -> cellData.getValue().goldProperty().asObject());
	}
	
	public void setModel(GameModel game) {
		this.game = game;
		playerTable.setItems(game.getPlayers());
		gameStatusLabel.textProperty().bind(game.statusProperty().asString());
		gameTimeLabel.textProperty().bind(game.currentTimeProperty().asString());
	}
}
