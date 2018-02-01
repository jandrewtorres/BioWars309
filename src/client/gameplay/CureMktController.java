package client.gameplay;

import client.Client;
import client.model.ClientModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CureMktController {
	ClientModel model;
	Client clientApp;

	@FXML
	private Button buyVaccine;
	@FXML
	private Button buyMeds;
	@FXML
	private Button buyIV;
	@FXML 
	private Button buyHospital;
	@FXML
	private Button exitButton;
	
	public CureMktController(ClientModel model) {
		this.model = model;
	}
	
	@FXML
	private void initialize() {
		// Nothing here ... yet
	}
	@FXML
	private void getVaccine(ActionEvent event) {
		try {
			clientApp.closeMenu(exitButton);
		}catch(Exception e) {
			System.out.println("can't buy vaccine");
		}
	}
	@FXML
	private void getMeds(ActionEvent event) {
		try {
			clientApp.closeMenu(exitButton);
		}catch(Exception e) {
			System.out.println("can't buy Meds");
		}
	}
	@FXML
	private void getIV(ActionEvent event) {
		try {
			clientApp.closeMenu(exitButton);
		}catch(Exception e) {
			System.out.println("can't buy IV");
		}
	}
	@FXML
	private void getHospital(ActionEvent event) {
		try {
			clientApp.closeMenu(exitButton);
		}catch(Exception e) {
			System.out.println("can't buy Hospital");
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
