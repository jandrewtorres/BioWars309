package client.gameplay;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.Client;
import client.model.ClientModel;
import client.model.PlayerStatusPane;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import server.model.Player;


public class GamePlayController {
    private static final Logger clientLogger = Logger.getLogger(Client.class.getName());

	@FXML
	private Button virusIcon;
	@FXML
	private Button cureIcon;
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
	
	private List<PlayerStatusPane> statusPanes;
	
	private ClientModel model;

	private Client clientApp;
		
	public GamePlayController(ClientModel model) {
		this.model = model;
		statusPanes = new ArrayList<>();
	}
	
	@FXML
	private void initialize() {
		gameTimeLabel.textProperty().bind(
				Bindings.createStringBinding(() -> 
					formatInterval(model.getGameTime().get())
					, model.getGameTime()));
		initStatusPanes();
		configureStatusPanes();
	}
	
	private void initStatusPanes() {
		PlayerStatusPane playerOnePane = new PlayerStatusPane(playerOneAnchorPane, playerOneNameLabel, playerOnePopLabel);
		PlayerStatusPane playerTwoPane = new PlayerStatusPane(playerTwoAnchorPane, playerTwoNameLabel, playerTwoPopLabel);
		PlayerStatusPane playerThreePane = new PlayerStatusPane(playerThreeAnchorPane, playerThreeNameLabel, playerThreePopLabel);
		PlayerStatusPane playerFourPane = new PlayerStatusPane(playerFourAnchorPane, playerFourNameLabel, playerFourPopLabel);
		statusPanes.add(playerOnePane);
		statusPanes.add(playerTwoPane);
		statusPanes.add(playerThreePane);
		statusPanes.add(playerFourPane);
	}
	
	private void configureStatusPanes() {
		Integer playerCounter = 0;
		ObservableList<Player> players = model.getPlayers();
		
		for(PlayerStatusPane pane : statusPanes) {
			if(playerCounter < players.size()) {
				pane.bindValues(players.get(playerCounter));
			}
			else {
				pane.hide();
			}
			playerCounter += 1;
		}
	}
	@FXML
	private void dragBegin(MouseEvent e) {
		Dragboard db =  ((Node) e.getSource()).startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        content.putString(((Node) e.getSource()).getId());
        db.setContent(content);
        
        e.consume();
	}
	@FXML
	private void attackDone(DragEvent e) {
		if (e.getTransferMode() == TransferMode.MOVE) {
            try {
				//add logic here
			} catch (Exception error) {
				clientLogger.logp(Level.WARNING, VirusMenuController.class.getName(), "attackPlayer", "Exception closing the virus menu");
			}
        }
        e.consume();
	}
	@FXML
	private void hoverPlayer(DragEvent e) {
        if (e.getDragboard().hasString()) {
            e.acceptTransferModes(TransferMode.ANY);
        }
        e.consume();
	}
	@FXML
	private void choosePlayer(DragEvent e) {
		((Node) e.getSource()).setStyle("-fx-border-color: red;");
		e.consume();
	}
	@FXML
	private void notChoosePlayer(DragEvent e) {
		((Node) e.getSource()).setStyle("-fx-border-color: black;");
		e.consume();
	}
	@FXML
	private void attackPlayer(DragEvent e) {
		Dragboard db = e.getDragboard();
        boolean success = false;
        if (db.hasString()) {
           //attack logic here
           success = true;
        }
        e.setDropCompleted(success);
        e.consume();
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

