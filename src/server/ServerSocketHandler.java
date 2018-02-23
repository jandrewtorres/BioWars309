package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.ClientSocket;
import server.model.GameModel;

public class ServerSocketHandler extends Thread {
    private static final Logger serverLogger = Logger.getLogger(ServerApp.class.getName());
    public ClientCommunicator gameClient;
    
	private ServerSocket serverSocket;
	private GameModel game;
	
	public ServerSocketHandler(GameModel game, Integer port) {
		this.game = game;
		
		
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(5000);
		} catch (IOException e) {
			serverLogger.logp(Level.SEVERE, ServerSocketHandler.class.getName(), "constructor", "Exception in binding socket");
		}
	}
	
	
	
	@Override
	public void run() {
		Socket clientSocket;
		
		while(!isInterrupted()) {

			try {
				clientSocket = serverSocket.accept();
				
				gameClient = new ClientCommunicator(game, clientSocket);
				gameClient.start();
				
			} catch (SocketTimeoutException ste) { 
				// DO NOTHING
			} catch(Exception e) {
				serverLogger.logp(Level.SEVERE, ServerSocketHandler.class.getName(), "run", "Exception in creating a connection to client");
			}
		}
		
		try {
			serverSocket.close();
		} catch (IOException e) {
			serverLogger.logp(Level.SEVERE, ServerSocketHandler.class.getName(), "run", "Exception in closing socket");
		}
	}
	
	public void closeSocket()
	{
		try {
			serverSocket.close();
		} catch (IOException e) {
			serverLogger.logp(Level.SEVERE, ClientSocket.class.getName(), "closeSocket", "Exception closing the client socket");
		}
	}
}
