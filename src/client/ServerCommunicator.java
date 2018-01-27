package client;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import client.model.ClientModel;
import javafx.application.Platform;
import server.model.Player;
import server.model.Player.PLAYER_STATUS;

public class ServerCommunicator implements Runnable {
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private Boolean running;
	ClientModel model;
	
	public ServerCommunicator(OutputStream out, InputStream in) {
		running = true;
		
		try {
			this.out = new ObjectOutputStream(out);
			this.in = new ObjectInputStream(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void transmitMessage(Object message) {
		try {
			out.writeObject(message);
			out.flush();
		} catch(IOException ioe) {
			running = false;
			closeStreams();
		}
	}
	
	private void receiveMessage(Object rxData) {
		Element root = ((Document)rxData).getDocumentElement();
		NodeList playerList = root.getChildNodes();
		
		String messageType = root.getNodeName();
		
		if(messageType.equals("LOBBY_UPDATE")) {
			Platform.runLater(() -> {
				model.players.clear();
				for(int player_index = 0; player_index < playerList.getLength(); player_index++) {
					Node player = playerList.item(player_index);
					String playerName = player.getChildNodes().item(0).getTextContent();
					String playerStatus = player.getChildNodes().item(1).getTextContent();
					model.players.add(new Player(playerName));
					model.getPlayerByName(playerName).statusProperty.set(PLAYER_STATUS.fromString(playerStatus));
				}
				/*
				ArrayList<String> pNames = new ArrayList<>();
				for(int player_index = 0; player_index < playerList.getLength(); player_index++) {
					Node player = playerList.item(player_index);
					String playerName = player.getChildNodes().item(0).getTextContent();
					String playerStatus = player.getChildNodes().item(1).getTextContent();
					Player p = model.getPlayerByName(playerName);
					if(p == null) {
						model.addPlayer(new Player(playerName));
						pNames.add(playerName);
					}
					else {
						model.getPlayerByName(playerName).statusProperty.set(PLAYER_STATUS.fromString(playerStatus));
					}
				}
				*/
			});
		}
		else if(messageType.equals("GAME_STARTED")) {
			Platform.runLater(() -> 
				model.gameStarted.setValue(true)
			);
		}
	}
	
	@Override
	public void run() {
		while(running) {

			try {
				Thread.sleep(250);
				receiveMessage(in.readObject());
			} catch (ClassNotFoundException e) {
				closeStreams();
				System.out.println("Class Not Found - Exception in reading object from input stream");
			} catch(EOFException e) {
				System.out.println("Lost connection to server. Goodbye.");
				closeStreams();
				Platform.exit();
				System.exit(0);
			} catch (Exception e) {
				System.out.println("Shutting down client");
				running = false;
				closeStreams();
			}
		}
	}
	
    private void closeStreams() {
        
        try {
            out.close();
        }
        catch (Exception e) {
        		System.out.println("Exception in closing the client output stream");
        }
        
        try {
            in.close();
        }
        catch (Exception e) {
        		System.out.println("Exception in closing the client input stream");
        }
        
    }
    
    public void setModel(ClientModel model) {
    		this.model = model;
    }
}    

