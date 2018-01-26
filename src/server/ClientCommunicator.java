package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import server.model.GameModel;
import server.model.Player;
import server.model.Player.PLAYER_STATUS;

public class ClientCommunicator extends Thread implements Observer {
	private Socket clientSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	GameModel game;
		
	public ClientCommunicator(GameModel game, Socket clientSocket) throws IOException {
		this.clientSocket = clientSocket;
		this.game = game;
		this.out = new ObjectOutputStream(clientSocket.getOutputStream());
		this.in = new ObjectInputStream(clientSocket.getInputStream());
		game.addObserver(this);
	}
	
	public void run() {
		try {
			while(!isInterrupted()) {
				receiveObject(in.readObject());
			}
		} catch (Exception e) {
			System.out.println("Exception receiving object from client");
		}
	}
	
	private void receiveObject(Object rxData) 
	{
		Element root = ((Document)rxData).getDocumentElement();
		String playerName = root.getChildNodes().item(0).getTextContent();
		String messageType = root.getNodeName();
		if(messageType.equals("REGISTER")) {
			Platform.runLater(() -> {
				game.addPlayer(new Player(playerName));
			});
		}
		else if(messageType.equals("PLAYER_READY")) {
			Platform.runLater(() -> {
				game.setPlayerStatusReady(playerName);
			});
		}
	}
	
	private void transmitCommand(Object data) {
			try {
				out.writeObject(data);
				out.flush();
			} catch (IOException e) {
				System.out.println("Exception transmitting command from server");
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
				nameElem.appendChild(messageDoc.createTextNode(p.nameProperty.get()));
				
				Element statusElem = messageDoc.createElement("STATUS");
				statusElem.appendChild(messageDoc.createTextNode(p.statusProperty.get().toString()));
				
				playerElem.appendChild(nameElem);
				playerElem.appendChild(statusElem);
				updateLobbyElem.appendChild(playerElem);
			}
			
			messageDoc.appendChild(updateLobbyElem);
			
			transmitCommand(messageDoc);
		} catch (ParserConfigurationException e) {
			System.out.println("Exception in notifying server of ready status");
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
			System.out.println("Exception in notifying server of ready status");
		}
	}
	
	private void tickUpdate() {
		// TO DO
	}
	
	@Override
	public void update(Observable o, Object arg) {
		String cmd = (String) arg;
		if(cmd.equals("UPDATE_PLAYERS")) {
			updateLobby();
		}
		else if(cmd.equals("GAME_STARTED")) {
			gameStarted();
		}
		else if(cmd.equals("TICK")) {
			tickUpdate();
		}
	}
}
