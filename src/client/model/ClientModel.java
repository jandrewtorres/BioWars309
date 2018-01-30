package client.model;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import client.Client;
import client.ServerCommunicator;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.model.Player;

public class ClientModel {
    private static final Logger clientLogger = Logger.getLogger(Client.class.getName());

	private ServerCommunicator communicator;
	private String clientName;
	private ObservableList<Player> players;
	private BooleanProperty gameStarted;
	
	public ClientModel(ServerCommunicator communicator) {
		this.communicator = communicator;
		this.players = FXCollections.observableArrayList();
		this.gameStarted = new SimpleBooleanProperty(false);
	}
	
	public Boolean registerClient(String clientName) {
		Boolean registered = false;
		this.clientName = clientName;
		
		try {
			Document messageDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

			Element registerElem = messageDoc.createElement("REGISTER");
			
			Element nameElem = messageDoc.createElement("NAME");
			nameElem.appendChild(messageDoc.createTextNode(clientName));
			registerElem.appendChild(nameElem);
			
			messageDoc.appendChild(registerElem);
			
			communicator.transmitMessage(messageDoc);
			registered = true;
		} catch (ParserConfigurationException e) {
			clientLogger.logp(Level.SEVERE, ClientModel.class.getName(), "registerClient", "Exception in registering client");
		}
		return registered;
	}
	
	public void setClientStatusReady() {
		Document messageDoc;
		try {
			messageDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element requestPlayersElem = messageDoc.createElement("PLAYER_READY");
			
			Element nameElem = messageDoc.createElement("NAME");
			nameElem.appendChild(messageDoc.createTextNode(clientName));
			requestPlayersElem.appendChild(nameElem);
			
			messageDoc.appendChild(requestPlayersElem);
			
			communicator.transmitMessage(messageDoc);
		} catch (ParserConfigurationException e) {
			clientLogger.logp(Level.SEVERE, ClientModel.class.getName(), "setClientStatusReady", "Exception in notifying server of client ready status");
		}
	}
	
	public Player getPlayerByName(String playerName) {
		for(Player p : players) {
			if(p.nameProperty().get().equals(playerName)) {
				return p;
			}
		}
		return null;
	}
	
	public void addPlayer(Player p) {
		players.add(p);
	}
	
	public ObservableList<Player> getPlayers() {
		return players;
	}
	
	public void startGame() {
		gameStarted.set(true);
	}
	
	public BooleanProperty getGameStartedProperty() {
		return gameStarted;
	}
}
