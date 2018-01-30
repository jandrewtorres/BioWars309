package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import server.model.GameModel;
import server.model.ObserverMessage;
import server.model.Player;

public class ClientCommunicator extends Thread implements Observer {
	private static Logger serverLogger = Logger.getLogger(ServerApp.class.getName());
	private ObjectOutputStream out;
	private ObjectInputStream in;
	GameModel game;
	Player associatedPlayer;
		
	public ClientCommunicator(GameModel game, Socket clientSocket) throws IOException {
		this.game = game;
		this.out = new ObjectOutputStream(clientSocket.getOutputStream());
		this.in = new ObjectInputStream(clientSocket.getInputStream());
		game.addObserver(this);
	}
	
	@Override
	public void run() {
		try {
			while(!isInterrupted()) {
				receiveObject(in.readObject());
			}
		} 
		catch(EOFException e) {  
			game.deleteObserver(this);
			game.removePlayer(associatedPlayer);
		} catch (Exception e) {
			serverLogger.logp(Level.SEVERE, ClientCommunicator.class.getName(), "run", "Exception receiving object from client");
		}
	}
	
	private void receiveObject(Object rxData) 
	{
		Element root = ((Document)rxData).getDocumentElement();
		String playerName = root.getChildNodes().item(0).getTextContent();
		String messageType = root.getNodeName();
		if(messageType.equals("REGISTER")) {
			Platform.runLater(() -> {
				associatedPlayer = new Player(playerName);
				game.addPlayer(associatedPlayer);
			});
		}
		else if(messageType.equals("PLAYER_READY")) {
			Platform.runLater(() -> 
				game.setPlayerStatusReady(playerName)
			);
		}
	}
	
	private void transmitCommand(Object data) {
			try {
				out.writeObject(data);
				out.flush();
			} catch (IOException e) {
				serverLogger.logp(Level.SEVERE, ClientCommunicator.class.getName(), "transmitCommand", "Exception transmitting command from server");
			}
	}
	
	private void updateLobby() {
		Document messageDoc;
		ObservableList<Player> players = game.getPlayers();
		try {
			messageDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element updateLobbyElem = messageDoc.createElement("LOBBY_UPDATE");
			for(Player p : players) {
				Element playerElem = messageDoc.createElement("PLAYER");
				
				Element nameElem = messageDoc.createElement("NAME");
				nameElem.appendChild(messageDoc.createTextNode(p.nameProperty().get()));
				
				Element statusElem = messageDoc.createElement("STATUS");
				statusElem.appendChild(messageDoc.createTextNode(p.statusProperty().get().toString()));
				
				playerElem.appendChild(nameElem);
				playerElem.appendChild(statusElem);
				updateLobbyElem.appendChild(playerElem);
			}
			
			messageDoc.appendChild(updateLobbyElem);
			
			transmitCommand(messageDoc);
		} catch (ParserConfigurationException e) {
			serverLogger.logp(Level.SEVERE, ClientCommunicator.class.getName(), "updateLobby", "Exception in notifying server of ready status");
		}
	}
	
	private void gameStarted() {
		Document messageDoc;
		try {
			messageDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element startGameElem = messageDoc.createElement("GAME_STARTED");
			messageDoc.appendChild(startGameElem);
			
			transmitCommand(messageDoc);
		} catch (ParserConfigurationException e) {
			serverLogger.logp(Level.SEVERE, ClientCommunicator.class.getName(), "gameStarted", "Exception in notifying server of ready status");
		}
	}
	
	private void tickUpdate() {
		// TO DO
	}
	
	@Override
	public void update(Observable o, Object arg) {
		ObserverMessage msg = (ObserverMessage) arg;
		switch(msg.getMessageType()) {
		case UPDATE_LOBBY:
			updateLobby();
			break;
		case GAME_STARTED:
			gameStarted();
			break;
		case TICK:
			tickUpdate();
			break;
		}
	}
}
