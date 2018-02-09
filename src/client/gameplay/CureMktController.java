package client.gameplay;

import java.util.logging.Level;
import java.util.logging.Logger;

import client.Client;
import client.model.ClientModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.StageStyle;
import server.model.cure.CureFactory.CURE_TYPE;
import server.model.virus.VirusFactory.VIRUS_TYPE;

public class CureMktController {
	private ClientModel model;
	private Client clientApp;
	private Alert alert;
	
	@FXML
	private Button buyColdCure;
	@FXML
	private Button buyFluCure;
	@FXML
	private Button buyPoxCure;
	@FXML 
	private Button buySarsCure;
	@FXML
	private Button exitButton;
	
    private static final Logger clientLogger = Logger.getLogger(Client.class.getName());
    
	public CureMktController(ClientModel model) {
		this.model = model;
		alert = new Alert(AlertType.WARNING);
		alert.initStyle(StageStyle.UTILITY);
		alert.setHeaderText("Insufficient Funds");
	}
	
	@FXML
	private void initialize() {
		// Nothing here ... yet
	}
	
	@FXML
	private void getColdCure(ActionEvent event) {
		if(model.buyCure(CURE_TYPE.COLDCURE)) {
			try {
				clientApp.closeMenu(exitButton);
			}catch(Exception e) {
				clientLogger.logp(Level.WARNING, CureMktController.class.getName(), "getColdCure", "Exception buying cold vaccine");
			}
		}else {
			alert.setContentText("More gold needed to buy cold cure");
			alert.showAndWait();
		}
	}
	@FXML
	private void getFluCure(ActionEvent event) {
		if(model.buyCure(CURE_TYPE.FLUCURE)) {
			try {
				clientApp.closeMenu(exitButton);
			}catch(Exception e) {
				clientLogger.logp(Level.WARNING, CureMktController.class.getName(), "getFluCure", "Exception buying flu vaccine");
			}
		}else {
			alert.setContentText("More gold needed to buy flu cure");
			alert.showAndWait();
		}
	}
	@FXML
	private void getPoxCure(ActionEvent event) {
		if(model.buyCure(CURE_TYPE.POXCURE)) {
			try {
				clientApp.closeMenu(exitButton);
			}catch(Exception e) {
				clientLogger.logp(Level.WARNING, CureMktController.class.getName(), "getPoxCure", "Exception buying pox vaccine");
			}
		}else {
			alert.setContentText("More gold needed to buy pox cure");
			alert.showAndWait();
		}
	}
	@FXML
	private void getSarsCure(ActionEvent event) {
		if(model.buyCure(CURE_TYPE.SARSCURE)) {
			try {
				clientApp.closeMenu(exitButton);
			}catch(Exception e) {
				clientLogger.logp(Level.WARNING, CureMktController.class.getName(), "getSarsCure", "Exception buying sars cure");
			}
		}else {
			alert.setContentText("More gold needed to buy sars cure");
			alert.showAndWait();
		}
	}
	@FXML
	private void exitMenu(ActionEvent event) {
		try {
			clientApp.closeMenu(exitButton);
		}catch(Exception e) {
			System.out.println("can't close submenu");
		}
	}
	
	public void setClientApp(Client app) {
		this.clientApp = app;
	}
}
