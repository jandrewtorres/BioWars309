package client.gameplay;

import java.util.logging.Level;
import java.util.logging.Logger;

import client.Client;
import client.model.ClientModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
