package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import client.model.ClientModel;
import javafx.application.Platform;
import server.model.Player;
import server.model.Player.PLAYER_STATUS;

public class ServerCommunicator implements Runnable {
    private static final Logger clientLogger = Logger.getLogger(Client.class.getName());

	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private Boolean running;
	private ClientModel model;
	
	public ServerCommunicator(OutputStream out, InputStream in) {
		running = true;
		
		try {
			this.out = new ObjectOutputStream(out);
			this.in = new ObjectInputStream(in);
		} catch (IOException e) {
			clientLogger.logp(Level.SEVERE, ServerCommunicator.class.getName(), "constructor", "Exception creating or getting the object output and input streams");
		}

	}
	// public for unit test
	public void transmitMessage(Object message) {
		try {
			out.writeObject(message);
			out.flush();
		} catch(IOException ioe) {
			running = false;
			closeStreams();
		}
	}
	
	public void receiveMessage(Object rxData) {
		Element root = ((Document)rxData).getDocumentElement();
		String messageType = root.getNodeName();
		
		switch(messageType) {
			case "LOBBY_UPDATE":
				updateLobby(root);
				break;
			case "GAME_STARTED":
				startGame();
				break;
			case "GAME_UPDATE":
				gameUpdate(root);
				break;
			default:
				break;
		}
	}
	
	private void startGame() {
		Platform.runLater(() -> 
			model.startGame()
		);
	}
	
	private void updateLobby(Element root) {
		NodeList playerList = root.getChildNodes();
		Platform.runLater(() -> {
			model.clearPlayers();
			for(int player_index = 0; player_index < playerList.getLength(); player_index++) {
				Node player = playerList.item(player_index);
				String playerName = player.getChildNodes().item(0).getTextContent();
				String playerStatus = player.getChildNodes().item(1).getTextContent();
				model.addPlayer(new Player(playerName));
				model.getPlayerByName(playerName).setPlayerStatus(PLAYER_STATUS.fromString(playerStatus));
			}
		});
	}
	
	private void gameUpdate(Element root) {
		Node gameTimeNode = root.getFirstChild();
		NodeList playerList = gameTimeNode.getNextSibling().getChildNodes();
		Platform.runLater(() -> {
			model.updateGameTime(Long.parseLong(gameTimeNode.getTextContent()));
			for(Integer i = 0; i < playerList.getLength(); i++) {
				Node player = playerList.item(i);
				String playerName = player.getChildNodes().item(0).getTextContent();
				String playerGold = player.getChildNodes().item(1).getTextContent();
				String playerPop = player.getChildNodes().item(2).getTextContent();
				Player p = model.getPlayerByName(playerName);
				p.updateStats(Integer.parseInt(playerGold), Integer.parseInt(playerPop));
			}
		});
	}
	
	@Override
	public void run() {
		while(running) {
			try {
				Thread.sleep(250);
				receiveMessage(in.readObject());
			} catch (ClassNotFoundException e) {
				closeStreams();
				clientLogger.logp(Level.SEVERE, ServerCommunicator.class.getName(), "run", "Class Not Found - Exception in reading object from input stream");
			} catch(Exception e) {
				clientLogger.logp(Level.SEVERE, ServerCommunicator.class.getName(), "run", "Lost connection to server. Goodbye.");
				closeStreams();
				Platform.exit();
				System.exit(0);
			}
		}
	}
	
    private void closeStreams() {
        
        try {
            out.close();
        }
        catch (Exception e) {
        		clientLogger.logp(Level.SEVERE, ServerCommunicator.class.getName(), "closeStreams", "Exception closing the output stream");
        }
        
        try {
            in.close();
        }
        catch (Exception e) {
        		clientLogger.logp(Level.SEVERE, ServerCommunicator.class.getName(), "closeStreams", "Exception closing the input stream");
        }
        
    }
    
    public void setModel(ClientModel model) {
    		this.model = model;
    }
}    

