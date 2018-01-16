package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javafx.application.Platform;

public class ClientConnector extends Thread {
	private Socket clientSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private ServerViewerController controller;
	
	public ClientConnector(ServerViewerController controller, Socket newSocket) throws IOException {
		clientSocket = newSocket;
		out = new ObjectOutputStream(clientSocket.getOutputStream());
		in = new ObjectInputStream(clientSocket.getInputStream());
		this.controller = controller;
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
			controller.addClient();
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
