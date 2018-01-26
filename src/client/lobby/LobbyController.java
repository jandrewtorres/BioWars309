package client.lobby;

import client.model.ClientModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import server.model.Player;

public class LobbyController {
	ClientModel model;
	@FXML
	Button readyButton;
	@FXML
	TableView<Player> lobbyTable;
	@FXML
	TableColumn<Player, String> playerNameColumn;
	@FXML
	TableColumn<Player, String> playerStatusColumn;
	
	public LobbyController(ClientModel model) {
		this.model = model;
	}
	
	@FXML
	private void initialize() {
		playerNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty);
		playerStatusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty.asString());
		
		lobbyTable.setItems(model.players);
	}
	
	@FXML
	private void onReadyButtonClicked() {
		model.setClientStatusReady();
	}
}
