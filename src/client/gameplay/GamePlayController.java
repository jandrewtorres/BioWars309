package client.gameplay;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.Client;
import client.model.ClientModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class GamePlayController {
    private static final Logger clientLogger = Logger.getLogger(Client.class.getName());

	@FXML
	private Button virusIcon;
	@FXML
	private Button cureIcon;
	@FXML
	private Button virusBuy;
	@FXML
	private Label gameTimeLabel;
	
	private ClientModel model;

	private Client clientApp;
	
	public GamePlayController(ClientModel model) {
		this.model = model;
	}
	
	@FXML
	private void initialize() {
		gameTimeLabel.textProperty().bind(
				Bindings.createStringBinding(() -> 
					formatInterval(model.getGameTime().get())
					, model.getGameTime()));
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

