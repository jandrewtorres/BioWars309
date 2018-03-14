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

import javafx.collections.ObservableList;
import server.model.GameModel;
import server.model.ObserverMessage;
import server.model.Player;
import server.model.cure.CureFactory.CURE_TYPE;
import server.model.virus.VirusFactory.VIRUS_TYPE;

public class ClientCommunicator extends Thread implements Observer {
	private static Logger serverLogger = Logger.getLogger(ServerApp.class.getName());
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private GameModel game;
	private Player associatedPlayer;
		
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
				associatedPlayer = new Player(playerName);
				game.addPlayer(associatedPlayer);
		}
		else if(messageType.equals("PLAYER_READY")) {
				game.setPlayerStatusReady(playerName);
		}
		else if(messageType.equals("BUY_VIRUS")) {
				associatedPlayer.buyVirus(VIRUS_TYPE.fromString(root.getChildNodes().item(1).getTextContent()));
		}
		else if(messageType.equals("BUY_CURE")) {
				associatedPlayer.buyCure(CURE_TYPE.fromString(root.getChildNodes().item(1).getTextContent()));
		}
		else if(messageType.equals("APPLY_VIRUS")) {
				VIRUS_TYPE vt = VIRUS_TYPE.fromString(root.getChildNodes().item(2).getTextContent());
				Player opponent = game.getPlayerByName(root.getChildNodes().item(1).getTextContent());
				game.applyVirus(associatedPlayer, opponent, vt);
		}
		else if(messageType.equals("APPLY_CURE")) {
				CURE_TYPE ct = CURE_TYPE.fromString(root.getChildNodes().item(1).getTextContent());
				associatedPlayer.applyCure(ct);
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
		ObservableList<Player> players = game.getReadOnlyPlayers();
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
			serverLogger.logp(Level.SEVERE, ClientCommunicator.class.getName(), "updateLobby", "Exception creating player lobby update message");
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
			serverLogger.logp(Level.SEVERE, ClientCommunicator.class.getName(), "gameStarted", "Exception creating game started update message");
		}
	}
	
	private void gameUpdate() {
		Document messageDoc;
		try {
			messageDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element gameUpdateElem = messageDoc.createElement("GAME_UPDATE");
			
			Element gameTimeElem = messageDoc.createElement("GAME_TIME");
			gameTimeElem.appendChild(messageDoc.createTextNode(Long.toString(game.getCurrentTimeProperty().get())));
			
			gameUpdateElem.appendChild(gameTimeElem);
			
			Element playersElem = messageDoc.createElement("PLAYERS");
			for(Player p : game.getReadOnlyPlayers()) {
				Element playerElem = messageDoc.createElement("PLAYER");
				
				Element nameElem = messageDoc.createElement("NAME");
				nameElem.appendChild(messageDoc.createTextNode(p.nameProperty().get()));
				
				Element goldElem = messageDoc.createElement("GOLD");
				goldElem.appendChild(messageDoc.createTextNode(Integer.toString(p.goldProperty().get())));
				
				Element populationElem = messageDoc.createElement("POPULATION");
				populationElem.appendChild(messageDoc.createTextNode(Integer.toString(p.populationProperty().get())));
				
				playerElem.appendChild(nameElem);
				playerElem.appendChild(goldElem);
				playerElem.appendChild(populationElem);
				
				playersElem.appendChild(playerElem);
			}
			gameUpdateElem.appendChild(playersElem);
			messageDoc.appendChild(gameUpdateElem);
			transmitCommand(messageDoc);
		} catch(ParserConfigurationException e) {
			serverLogger.logp(Level.SEVERE, ClientCommunicator.class.getName(), "tickUpdate", "Exception creating tick game update message");
		}
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
		case GAME_UPDATE:
			gameUpdate();
			break;
		}
	}
}
