package client.lobby;

import java.util.Observable;
import java.util.Observer;

import client.Client;
import client.model.ClientModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import server.model.Player;

public class LobbyController {
	ClientModel model;
	Client clientApp;
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
	
	public void setClientApp(Client app) {
		this.clientApp = app;
	}
}
