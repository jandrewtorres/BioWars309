package server;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import server.model.ServerModel;

public class ServerViewerController {
	@FXML
	private Label numClientsLabel;
	
	private ServerModel model;
	
	public ServerViewerController() {
		model = new ServerModel();
	}
	
	@FXML
	private void initialize() {
		updateUI();
	}
	
	public void setModel(ServerModel model) {
		this.model = model;
	}
	
	public void addClient() {
		model.numClients += 1;
		updateUI();
	}
	
	public void updateUI() {
		numClientsLabel.setText(Integer.toString(model.numClients));
	}
}
