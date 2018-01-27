package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientSocket {
	private Socket theSocket;
	
	public ClientSocket(String host, Integer port) {
			try {
				theSocket = new Socket();
				theSocket.connect(new InetSocketAddress(host, port), 5000);
			} catch (IOException e) {
				e.printStackTrace();
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
			e.printStackTrace();
		}
	}
}
