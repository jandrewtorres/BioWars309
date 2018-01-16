package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientSocket {
	private Socket clientSocket;
	
	public ClientSocket(String host, Integer port) {
			try {
				clientSocket = new Socket();
				clientSocket.connect(new InetSocketAddress(host, port), 5000);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public InputStream getInputStream() throws IOException {
		return clientSocket.getInputStream();
	}
	
	public OutputStream getOutputStream() throws IOException {
		return clientSocket.getOutputStream();
	}
	
	public void closeSocket() {
		try {
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
