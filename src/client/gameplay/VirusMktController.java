package client.gameplay;

import java.util.logging.Level;
import java.util.logging.Logger;

import client.Client;
import client.model.ClientModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.StageStyle;
import server.model.virus.VirusFactory.VIRUS_TYPE;

public class VirusMktController {

	
	@FXML
	private Button buyColdButton;
	@FXML 
	private Button buyFluButton;
	@FXML
	private Button buyPoxButton;
	@FXML 
	private Button buySARSButton;
    private static final Logger clientLogger = Logger.getLogger(Client.class.getName());

	private ClientModel model;
	private Client clientApp;

	@FXML
	private Button exitButton;
	
	public VirusMktController(ClientModel model) {
		this.model = model;
	}
	
	@FXML
	private void initialize() {
		// Nothing here ... yet
	}
	
	@FXML
	private void buyColdVirus(ActionEvent event) {
		if(model.buyVirus(VIRUS_TYPE.COLD)) {
			try {
				clientApp.closeMenu(exitButton);
			}catch(Exception e) {
				System.out.println("can't buy Cold");
			}
		}else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initStyle(StageStyle.UTILITY);
			alert.setHeaderText("Insufficient Funds");
			alert.setContentText("More gold needed to buy cold virus");

			alert.showAndWait();
		}
	}
	@FXML
	private void buyFluVirus(ActionEvent event) {
		if(model.buyVirus(VIRUS_TYPE.FLU)) {
			try {
				clientApp.closeMenu(exitButton);
			}catch(Exception e) {
				System.out.println("can't buy Flu");
			}
		}else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initStyle(StageStyle.UTILITY);
			alert.setHeaderText("Insufficient Funds");
			alert.setContentText("More gold needed to buy flu virus");

			alert.showAndWait();
		}
	}
	@FXML
	private void buyPoxVirus(ActionEvent event) {
		if(model.buyVirus(VIRUS_TYPE.POX)) {
			try {
				clientApp.closeMenu(exitButton);
			}catch(Exception e) {
				System.out.println("can't buy Pox");
			}
		}else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initStyle(StageStyle.UTILITY);
			alert.setHeaderText("Insufficient Funds");
			alert.setContentText("More gold needed to buy pox virus");

			alert.showAndWait();
		}
	}
	
	@FXML
	private void buySARSVirus(ActionEvent event) {
		if(model.buyVirus(VIRUS_TYPE.SARS)) {
			try {
				clientApp.closeMenu(exitButton);
			}catch(Exception e) {
				System.out.println("can't buy SARS");
			}
		}else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initStyle(StageStyle.UTILITY);
			alert.setHeaderText("Insufficient Funds");
			alert.setContentText("More gold needed to buy sars virus");

			alert.showAndWait();
		}
	}
	@FXML
	private void exitMenu(ActionEvent event) {
		try {
			clientApp.closeMenu(exitButton);
		}catch(Exception e) {
			clientLogger.logp(Level.SEVERE, VirusMktController.class.getName(), "exitMenu", "Exception closing virus market menu");
		}
	}
	
	public void setClientApp(Client app) {
		this.clientApp = app;
	}
}
