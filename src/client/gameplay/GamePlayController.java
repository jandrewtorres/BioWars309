package client.gameplay;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.Client;
import client.model.ClientModel;
import client.model.PlayerStatusPane;
import common.ClockFormatter;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import server.model.Player;
import server.model.cure.CureFactory.CURE_TYPE;
import server.model.virus.VirusFactory.VIRUS_TYPE;


public class GamePlayController {
    private static final Logger clientLogger = Logger.getLogger(Client.class.getName());
    
    Player currentSelectedDragPlayer;
    
	@FXML
	private Button virusIcon;
	@FXML
	private Button cureIcon;
	@FXML
	private Label gameTimeLabel;
	@FXML
	private Label playerNameLabel;
	
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
	private Label inventoryColdCureCountLabel;
	@FXML
	private Label inventoryFluCureCountLabel;
	@FXML
	private Label inventoryPoxCureCountLabel;
	@FXML
	private Label inventorySarsCureCountLabel;
	
	@FXML
	private AnchorPane coldCureInventoryPane;
	@FXML
	private AnchorPane fluCureInventoryPane;
	@FXML
	private AnchorPane poxCureInventoryPane;
	@FXML
	private AnchorPane sarsCureInventoryPane;
	
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
		this.statusPanes = new ArrayList<>();
	}
	
	@FXML
	private void initialize() {
		bindGameClock();
		bindInventoryLabels();
		configureInventoryPanes();
		configureVaccinePanes();
		bindInventoryVaccineLabels();
		initStatusPanes();
		configureStatusPanes();
		initStatsPanel();
		this.playerNameLabel.setText(model.getMyPlayer().nameProperty().get());
	}
	
	private void bindGameClock() {
		gameTimeLabel.textProperty().bind(
				Bindings.createStringBinding(() -> 
					ClockFormatter.formatInterval(model.getGameTime().get())
					, model.getGameTime()));
	}
	
	private void bindInventoryVaccineLabels() {
		inventoryColdCureCountLabel.textProperty().bind(
				Bindings.createStringBinding(() ->
						Integer.toString(model.getMyPlayer().getInventory().getColdCureCount().get()),
						model.getMyPlayer().getInventory().getColdCureCount()));
		inventoryFluCureCountLabel.textProperty().bind(
				Bindings.createStringBinding(() ->
						Integer.toString(model.getMyPlayer().getInventory().getFluCureCount().get()),
						model.getMyPlayer().getInventory().getFluCureCount()));
		inventoryPoxCureCountLabel.textProperty().bind(
				Bindings.createStringBinding(() ->
						Integer.toString(model.getMyPlayer().getInventory().getPoxCureCount().get()),
						model.getMyPlayer().getInventory().getPoxCureCount()));
		inventorySarsCureCountLabel.textProperty().bind(
				Bindings.createStringBinding(() ->
						Integer.toString(model.getMyPlayer().getInventory().getSarsCureCount().get()),
						model.getMyPlayer().getInventory().getSarsCureCount()));
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
		popRate.textProperty().set("0");
		goldRate.textProperty().set("0");
	}
	
	private void configureStatusPanes() {
		Integer playerCounter = 0;
		ObservableList<Player> players = model.getReadOnlyPlayers();
		
		for(PlayerStatusPane p : statusPanes) {
			if(playerCounter < players.size()) {
				Player currPlayer = players.get(playerCounter);
				p.bindValues(currPlayer);
				p.getAnchorPane().setOnDragOver(initDrop);
				p.getAnchorPane().setOnDragEntered(createDragEnteredHandler(currPlayer));
				p.getAnchorPane().setOnDragExited(notChoosePlayer);
				p.getAnchorPane().setOnDragDropped(attackPlayer);
			}
			else {
				p.hide();
			}
			playerCounter += 1;
		}
	}
	
	private void configureInventoryPanes() {
		coldInventoryPane.setOnDragDetected(createVirusDragDetectedHandler(VIRUS_TYPE.COLD));		
		fluInventoryPane.setOnDragDetected(createVirusDragDetectedHandler(VIRUS_TYPE.FLU));
		poxInventoryPane.setOnDragDetected(createVirusDragDetectedHandler(VIRUS_TYPE.POX));
		sarsInventoryPane.setOnDragDetected(createVirusDragDetectedHandler(VIRUS_TYPE.SARS));
	}
	
	private void configureVaccinePanes() {
		coldCureInventoryPane.setOnMousePressed(applyVaccine(CURE_TYPE.COLDCURE));
		fluCureInventoryPane.setOnMousePressed(applyVaccine(CURE_TYPE.FLUCURE));
		poxCureInventoryPane.setOnMousePressed(applyVaccine(CURE_TYPE.POXCURE));
		sarsCureInventoryPane.setOnMousePressed(applyVaccine(CURE_TYPE.SARSCURE));
	}
	
	private EventHandler<MouseEvent> applyVaccine(CURE_TYPE type){
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if(model.getMyPlayer().getInventory().hasCureOfType(type)) {
					Alert alert = new Alert(AlertType.WARNING, 
					            "Do you want to use " + type + "?",
					             ButtonType.OK, 
					             ButtonType.CANCEL);
					alert.setTitle("Vaccine Usage Alert");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.isPresent() && result.get() == ButtonType.OK) {
						model.applyCure(type);
					}
				}
				e.consume();
			}
		};
	}
	
	private EventHandler<MouseEvent> createVirusDragDetectedHandler(VIRUS_TYPE type) {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if(model.getMyPlayer().getInventory().hasVirusOfType(type)) {
					Dragboard db = ((Node) e.getSource()).startDragAndDrop(TransferMode.ANY);
					ClipboardContent content = new ClipboardContent();
					content.putString(type.toString());
					db.setContent(content);
				}
				e.consume();
			}
		};
	}
	
	EventHandler<DragEvent> attackPlayer = new EventHandler<DragEvent>() {
		@Override
		public void handle(DragEvent e) {
			Dragboard db = e.getDragboard();
	        boolean success = false;
	        if (db.hasString()) {
	           VIRUS_TYPE type = VIRUS_TYPE.fromString(db.getString());
	           model.applyVirusToOpponent(type, currentSelectedDragPlayer);
	           success = true;
	        }
	        e.setDropCompleted(success);
	        e.consume();	
		}
	};
	
	EventHandler<DragEvent> initDrop = new EventHandler<DragEvent>() {
		@Override
		public void handle(DragEvent e) {
	        	if (e.getDragboard().hasString()) {
	        		e.acceptTransferModes(TransferMode.ANY);
	        	}
	        	e.consume();		
		}
	};
	
	private EventHandler<DragEvent> createDragEnteredHandler(Player p) {
		return new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				currentSelectedDragPlayer = p;
				((Node) event.getSource()).setStyle("-fx-border-color: red;");
				event.consume();	
			}
		};
	}
	
	EventHandler<DragEvent> notChoosePlayer = new EventHandler<DragEvent>() {
		@Override
		public void handle(DragEvent e) {
			currentSelectedDragPlayer = null;
			((Node) e.getSource()).setStyle("-fx-border-color: transparent;");
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
			clientLogger.logp(Level.SEVERE, GamePlayController.class.getName(), "openCureMkt", "Exception opening cure market");
		}

	}
	
	public void setClientApp(Client app) {
		this.clientApp = app;
	}
}

