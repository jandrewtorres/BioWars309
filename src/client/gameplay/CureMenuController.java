package client.gameplay;

import client.Client;
import client.model.ClientModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CureMenuController {
	ClientModel model;
	Client clientApp;
	@FXML
	private Label vaccineCt;
	@FXML
	private Button useVaccine;
	@FXML
	private Label medCt;
	@FXML
	private Button useMeds;
	@FXML
	private Label IVCt;
	@FXML
	private Button useIV;
	@FXML
	private Label hospitalCt;
	@FXML
	private Button useHospital;
	@FXML
	private Button exitButton;
	
	public CureMenuController(ClientModel model) {
		this.model = model;
	}
	
	@FXML
	private void initialize() {
		// Nothing here ... yet
	}
	@FXML
	private void applyVaccine(ActionEvent event) {
		try {
			clientApp.closeMenu(exitButton);
		}catch(Exception e) {
			System.out.println("Can't use vaccine");
		}
	}
	@FXML
	private void applyMeds(ActionEvent event) {
		try {
			clientApp.closeMenu(exitButton);
		}catch(Exception e) {
			System.out.println("Can't use meds");
		}
	}
	@FXML
	private void applyIV(ActionEvent event) {
		try {
			clientApp.closeMenu(exitButton);
		}catch(Exception e) {
			System.out.println("Can't use IV");
		}
	}
	@FXML
	private void applyHospital(ActionEvent event) {
		try {
			clientApp.closeMenu(exitButton);
		}catch(Exception e) {
			System.out.println("Can't use hospital");
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
