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
import javafx.event.EventHandler;
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
	
	@FXML
	private Label inventoryColdCountLabel;
	@FXML
	private Label inventoryFluCountLabel;
	@FXML
	private Label inventoryPoxCountLabel;
	@FXML
	private Label inventorySarsCountLabel;
	
	@FXML
	private AnchorPane coldInventoryPane;
	@FXML
	private AnchorPane fluInventoryPane;
	@FXML
	private AnchorPane poxInventoryPane;
	@FXML
	private AnchorPane sarsInventoryPane;

	@FXML
	private Label popNum;
	@FXML
	private Label goldNum;
	@FXML
	private Label popRate;
	@FXML
	private Label goldRate;
	
	private List<PlayerStatusPane> statusPanes;
	
	private ClientModel model;

	private Client clientApp;
		
	public GamePlayController(ClientModel model) {
		this.model = model;
		statusPanes = new ArrayList<>();
	}
	
	@FXML
	private void initialize() {
		bindGameClock();
		bindInventoryLabels();
		bindInventoryVaccineLabels();
		initStatusPanes();
		configureStatusPanes();
		initStatsPanel();
	}
	
	private void bindGameClock() {
		gameTimeLabel.textProperty().bind(
				Bindings.createStringBinding(() -> 
					formatInterval(model.getGameTime().get())
					, model.getGameTime()));
	}
	
	private void bindInventoryVaccineLabels() {
		// Nothing yet
	}
	
	private void bindInventoryLabels() {
		inventoryColdCountLabel.textProperty().bind(
				Bindings.createStringBinding(() ->
						Integer.toString(model.getMyPlayer().getInventory().getColdVirusCount().get()),
						model.getMyPlayer().getInventory().getColdVirusCount()));
		inventoryFluCountLabel.textProperty().bind(
				Bindings.createStringBinding(() ->
						Integer.toString(model.getMyPlayer().getInventory().getFluVirusCount().get()),
						model.getMyPlayer().getInventory().getFluVirusCount()));
		inventoryPoxCountLabel.textProperty().bind(
				Bindings.createStringBinding(() ->
						Integer.toString(model.getMyPlayer().getInventory().getPoxVirusCount().get()),
						model.getMyPlayer().getInventory().getPoxVirusCount()));
		inventorySarsCountLabel.textProperty().bind(
				Bindings.createStringBinding(() ->
						Integer.toString(model.getMyPlayer().getInventory().getSarsVirusCount().get()),
						model.getMyPlayer().getInventory().getSarsVirusCount()));
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
	
	private void initStatsPanel() {
		popNum.textProperty().bind(
				Bindings.createStringBinding(
						() -> String.format("%d", model.getMyPlayer().populationProperty().intValue()), 
						model.getMyPlayer().populationProperty()));
		goldNum.textProperty().bind(
				Bindings.createStringBinding(
						() -> String.format("%d", model.getMyPlayer().goldProperty().intValue()), 
						model.getMyPlayer().goldProperty()));
		popRate.textProperty().bind(
				Bindings.createStringBinding(
						() -> String.format("%d", model.getMyPlayer().populationProperty().intValue()), 
						model.getMyPlayer().populationProperty()));
		goldRate.textProperty().bind(
				Bindings.createStringBinding(
						() -> String.format("%d", model.getMyPlayer().goldProperty().intValue()), 
						model.getMyPlayer().goldProperty()));
	}
	
	private void configureStatusPanes() {
		Integer playerCounter = 0;
		ObservableList<Player> players = model.getPlayers();
		
		for(PlayerStatusPane p : statusPanes) {
			if(playerCounter < players.size()) {
				p.bindValues(players.get(playerCounter));
				p.pane.setOnDragOver(initDrop);
				p.pane.setOnDragEntered(choosePlayer);
				p.pane.setOnDragExited(notChoosePlayer);
				p.pane.setOnDragDropped(attackPlayer);
			}
			else {
				p.hide();
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
				//remove virus from player inventory
			} catch (Exception error) {
				clientLogger.logp(Level.WARNING, GamePlayController.class.getName(), "attackPlayer", "could not attack");
			}
        }
        e.consume();
	}
	EventHandler<DragEvent> initDrop = new EventHandler<DragEvent>() {
		@Override
		public void handle(DragEvent e) {
        	if (e.getDragboard().hasString()) {
        		e.acceptTransferModes(TransferMode.ANY);
        	}
        	e.consume();		
		}
	};
	EventHandler<DragEvent> choosePlayer = new EventHandler<DragEvent>() {
		@Override
		public void handle(DragEvent e) {
			((Node) e.getSource()).setStyle("-fx-border-color: red;");
			e.consume();		
		}
	};
	EventHandler<DragEvent> notChoosePlayer = new EventHandler<DragEvent>() {
		@Override
		public void handle(DragEvent e) {
			((Node) e.getSource()).setStyle("-fx-border-color: transparent;");
			e.consume();		
		}
	};
	EventHandler<DragEvent> attackPlayer = new EventHandler<DragEvent>() {
		@Override
		public void handle(DragEvent e) {
			Dragboard db = e.getDragboard();
	        boolean success = false;
	        if (db.hasString()) {
	           //attack logic here
	           success = true;
	        }
	        e.setDropCompleted(success);
	        e.consume();	
		}
	};
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

