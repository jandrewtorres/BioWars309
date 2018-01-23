package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import server.model.GameModel;

public class ServerSocketHandler extends Thread {
	
	private ServerSocket serverSocket;
	private GameModel game;
	
	public ServerSocketHandler(GameModel game, Integer port) {
		this.game = game;
		
		
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(5000);
		} catch (IOException e) {
			System.out.println("Exception in binding socket");
		}
	}
	
	public void run() {
		Socket clientSocket;
		
		while(!isInterrupted()) {

			try {
				clientSocket = serverSocket.accept();
				
				ClientCommunicator gameClient = new ClientCommunicator(game, clientSocket);
				gameClient.start();
				
			} catch (SocketTimeoutException ste) { 
				// DO NOTHING
			} catch(Exception e) {
				System.out.println("Exception in creating a connection to client");
			}
		}
		
		try {
			serverSocket.close();
		} catch (IOException e) {
			System.out.println("Exception in closing socket.");
		}
	}
}
