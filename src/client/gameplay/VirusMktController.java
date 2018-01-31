package client.gameplay;

import client.Client;
import client.model.ClientModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class VirusMktController {
	ClientModel model;
	Client clientApp;
	
	@FXML
	private Button buyCold;
	@FXML 
	private Button buyFlu;
	@FXML
	private Button buyPox;
	@FXML 
	private Button buySARS;
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
	private void getCold(ActionEvent event) {
		try {
			clientApp.closeMenu(exitButton);
		}catch(Exception e) {
			System.out.println("can't buy Cold");
		}
	}
	@FXML
	private void getFlu(ActionEvent event) {
		try {
			clientApp.closeMenu(exitButton);
		}catch(Exception e) {
			System.out.println("can't buy Flu");
		}
	}
	@FXML
	private void getPox(ActionEvent event) {
		try {
			clientApp.closeMenu(exitButton);
		}catch(Exception e) {
			System.out.println("can't buy Pox");
		}
	}
	@FXML
	private void getSARS(ActionEvent event) {
		try {
			clientApp.closeMenu(exitButton);
		}catch(Exception e) {
			System.out.println("can't buy SARS");
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
