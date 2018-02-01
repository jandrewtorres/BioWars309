package client.gameplay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.Client;
import client.model.ClientModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import server.model.Player;


public class GamePlayController {
    private static final Logger clientLogger = Logger.getLogger(Client.class.getName());

	@FXML
	private Button virusIcon;
	@FXML
	private Button cureIcon;
	@FXML
	private Button virusBuy;
	@FXML
	Button cureBuy;
	@FXML
	private Label gameTimeLabel;
	
	@FXML
	private AnchorPane playerOneAnchorPane;
	@FXML
	private AnchorPane playerTwoAnchorPane;
	@FXML
	private AnchorPane playerThreeAnchorPane;
	@FXML
	private AnchorPane playerFourAnchorPane;
	
	@FXML
	private Label playerOneNameLabel;
	@FXML
	private Label playerTwoNameLabel;
	@FXML
	private Label playerThreeNameLabel;
	@FXML
	private Label playerFourNameLabel;
	
	@FXML
	private Label playerOnePopLabel;
	@FXML
	private Label playerTwoPopLabel;
	@FXML
	private Label playerThreePopLabel;
	@FXML
	private Label playerFourPopLabel;
		
	private ClientModel model;

	private Client clientApp;
	
	private Map<String, List<Node>> playerHUDMap;
	
	public GamePlayController(ClientModel model) {
		this.model = model;
	}
	
	@FXML
	private void initialize() {
		gameTimeLabel.textProperty().bind(
				Bindings.createStringBinding(() -> 
					formatInterval(model.getGameTime().get())
					, model.getGameTime()));
		
		initPlayerPanes();
	}
	
	private void initPlayerPanes() {
		Integer numPlayers = model.getPlayers().size();
		
		Player pOne = model.getPlayers().get(0);
		playerOneNameLabel.textProperty().bind(pOne.nameProperty());
		playerOnePopLabel.textProperty().bind(Bindings.createStringBinding(() ->
			Integer.toString(pOne.populationProperty().asObject().get()), pOne.populationProperty()));
	}
	
	@FXML
	private void openCureMenu(ActionEvent event) {
		try {
			clientApp.openCureMenu();
		}catch(Exception e) {
			System.out.println("error opening submenu");
		}
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
	@FXML
	private void openCureMkt(ActionEvent event) {
		try {
			clientApp.openCureMkt();
		}catch(Exception e) {
			System.out.println("error opening submenu");
		}

	}
	public void setClientApp(Client app) {
		this.clientApp = app;
	}
	
    private String formatInterval(final long l)
    {
        final long hr = TimeUnit.SECONDS.toHours(l);
        final long min = TimeUnit.SECONDS.toMinutes(l - TimeUnit.HOURS.toSeconds(hr));
        final long sec = TimeUnit.SECONDS.toSeconds(l - TimeUnit.HOURS.toSeconds(hr) - TimeUnit.MINUTES.toSeconds(min));
        return String.format("%02d:%02d:%02d", hr, min, sec);
    }
}

