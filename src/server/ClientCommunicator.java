package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javafx.application.Platform;
import server.model.GameModel;
import server.model.Player;

public class ClientConnector extends Thread {
	private Socket clientSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	GameModel game;
		
	public ClientConnector(GameModel game, Socket clientSocket) throws IOException {
		this.clientSocket = clientSocket;
		this.game = game;
		this.out = new ObjectOutputStream(clientSocket.getOutputStream());
		this.in = new ObjectInputStream(clientSocket.getInputStream());
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
		String receivedMsg = (String) rxData;
		Platform.runLater(() -> {
			game.addPlayer(new Player(receivedMsg));
		});
	}
	
	private void transmitCommand(String data) {
			try {
				out.writeObject(data);
				out.flush();
			} catch (IOException e) {
				System.out.println("Exception transmitting command from server");
			}
	}
}
