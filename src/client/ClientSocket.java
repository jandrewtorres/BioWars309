package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientSocket {
	private Socket theSocket;
    private static final Logger clientLogger = Logger.getLogger(Client.class.getName());
	
	public ClientSocket(String host, Integer port) {
			try {
				theSocket = new Socket();
				theSocket.connect(new InetSocketAddress(host, port), 5000);
			} catch (IOException e) {
				clientLogger.logp(Level.SEVERE, ClientSocket.class.getName(), "constructor", "Exception creating or connecting client socket");
			}
	}
	
	public InputStream getInputStream() throws IOException {
		return theSocket.getInputStream();
	}
	
	public OutputStream getOutputStream() throws IOException {
		return theSocket.getOutputStream();
	}
	
	public void closeSocket() {
		try {
			theSocket.close();
		} catch (IOException e) {
			clientLogger.logp(Level.SEVERE, ClientSocket.class.getName(), "closeSocket", "Exception closing the client socket");
		}
	}
}
