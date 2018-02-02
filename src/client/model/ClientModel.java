package client.model;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import client.Client;
import client.ServerCommunicator;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.model.Player;
import server.model.virus.Virus;
import server.model.virus.VirusFactory;
import server.model.virus.VirusFactory.VIRUS_TYPE;

public class ClientModel {
    private static final Logger clientLogger = Logger.getLogger(Client.class.getName());

	private ServerCommunicator communicator;
	private String clientName;
	private ObservableList<Player> players;
	private ObservableList<Player> readOnlyPlayers;
	private ReadOnlyBooleanWrapper gameStarted;
	private ReadOnlyLongWrapper gameTime;
	
	public ClientModel(ServerCommunicator communicator) {
		this.communicator = communicator;
		this.players = FXCollections.observableArrayList();
		this.readOnlyPlayers = FXCollections.unmodifiableObservableList(this.players);
		this.gameStarted = new ReadOnlyBooleanWrapper(false);
		this.gameTime = new ReadOnlyLongWrapper(0);
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
	
	public Boolean buyVirus(VIRUS_TYPE type) {
		Virus v = new VirusFactory().createVirus(type);
		System.out.println(Integer.toString(v.getPrice()));
		if(v.getPrice() > getMyPlayer().goldProperty().get()) {
			return false;
		}
		
		Document messageDoc;
		try {
			messageDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element buyVirusElem = messageDoc.createElement("BUY_VIRUS");
			
			Element nameElem = messageDoc.createElement("NAME");
			nameElem.appendChild(messageDoc.createTextNode(clientName));
			buyVirusElem.appendChild(nameElem);
			
			Element virusTypeElem = messageDoc.createElement("TYPE");
			virusTypeElem.appendChild(messageDoc.createTextNode(type.toString()));
			buyVirusElem.appendChild(virusTypeElem);
			
			messageDoc.appendChild(buyVirusElem);
			
			communicator.transmitMessage(messageDoc);
		} catch(ParserConfigurationException e) {
			clientLogger.logp(Level.SEVERE, ClientModel.class.getName(), "buyVirus", "Exception in trasmitting buy virus message to server");
			return false;
		}
		return true;
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
		return readOnlyPlayers;
	}

	public ObservableList<Player> getOpposingPlayers() {
		ObservableList<Player> opposingPlayers = FXCollections.observableArrayList();
		for(Player p : players) {
			if(!p.nameProperty().get().equals(clientName)) {
				opposingPlayers.add(p);
			}
		}
		return opposingPlayers;
	}
	
	public Player getMyPlayer() {
		return getPlayerByName(clientName);
	}
	
	public void startGame() {
		gameStarted.set(true);
	}
	
	public ReadOnlyBooleanProperty getGameStartedProperty() {
		return gameStarted.getReadOnlyProperty();
	}
	
	public void clearPlayers() {
		players.clear();
	}
	
	public void updateGameTime(Long time) {
		gameTime.set(time);
	}
	
	public ReadOnlyLongProperty getGameTime() {
		return gameTime.getReadOnlyProperty();
	}
}
