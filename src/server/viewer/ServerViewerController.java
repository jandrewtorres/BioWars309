package server.viewer;

import java.util.concurrent.TimeUnit;

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
		playerNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty);
		goldColumn.setCellValueFactory(cellData -> cellData.getValue().populationProperty().asObject());
		populationColumn.setCellValueFactory(cellData -> cellData.getValue().populationProperty().asObject());
		
		playerTable.setItems(game.getPlayers());
		gameStatusLabel.textProperty().bind(game.statusProperty.asString());
		gameTimeLabel.textProperty().bind(
			Bindings.createStringBinding(() -> 
				formatInterval(game.currentTimeProperty.get())
				, game.currentTimeProperty));
	}
	
    private String formatInterval(final long l)
    {
        final long hr = TimeUnit.SECONDS.toHours(l);
        final long min = TimeUnit.SECONDS.toMinutes(l - TimeUnit.HOURS.toSeconds(hr));
        final long sec = TimeUnit.SECONDS.toSeconds(l - TimeUnit.HOURS.toSeconds(hr) - TimeUnit.MINUTES.toSeconds(min));
        return String.format("%02d:%02d:%02d", hr, min, sec);
    }
}
