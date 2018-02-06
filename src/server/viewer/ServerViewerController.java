package server.viewer;

import java.util.concurrent.TimeUnit;

import common.ClockFormatter;
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
	private TableColumn<Player, Integer> populationColumn;
	@FXML
	private Label gameStatusLabel;
	@FXML
	private Label gameTimeLabel;
	
	private GameModel game;
	
	public ServerViewerController(GameModel game) {
		this.game = game;
	}
	
	@FXML
	private void initialize() {
		playerNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		goldColumn.setCellValueFactory(cellData -> cellData.getValue().goldProperty().asObject());
		populationColumn.setCellValueFactory(cellData -> cellData.getValue().populationProperty().asObject());
		
		playerTable.setItems(game.getPlayers());
		gameStatusLabel.textProperty().bind(game.getGameStatusProperty().asString());
		gameTimeLabel.textProperty().bind(
			Bindings.createStringBinding(() -> 
				ClockFormatter.formatInterval(game.getCurrentTimeProperty().get())
				, game.getCurrentTimeProperty()));
	}
}
