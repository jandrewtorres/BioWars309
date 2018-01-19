package client;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Properties;

import forms.loginUI;

public class Client {
    private static final String CONFIG_FILE_NAME = "client_config.properties";
    
    private ClientSocket clientSocket;
        
    private Properties clientProperties;
    
    private static enum CLIENT_PROPERTIES {
    		HOST("Host"),
    		SOCKET_PORT("SocketPort");
    	
    		public String name;
    		
    		private CLIENT_PROPERTIES(String n) {
    			name = n;
    		}
    }
    
	public Client(String propertiesFile) throws IOException {
		loadProperties(propertiesFile);
		
		clientSocket = new ClientSocket(clientProperties.getProperty(CLIENT_PROPERTIES.HOST.name).trim(),
				Integer.parseInt(clientProperties.getProperty(CLIENT_PROPERTIES.SOCKET_PORT.name).trim()));
		
		ClientModel clientModel = new ClientModel(clientSocket.getOutputStream(), clientSocket.getInputStream());
		
		Thread clientThread = new Thread(clientModel);
		clientThread.start();
		clientModel.transmitCommand("THIS IS ME");
	}
	
	private void loadProperties(String propertiesFile) {
		try 
		{
			clientProperties = new Properties();
			clientProperties.load(new FileInputStream(propertiesFile));
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		if(System.getProperty("CONFIG_DIR") == null) {
			System.setProperty("CONFIG_DIR", "config");
		}
		
		try {
			new Client(System.getProperty("CONFIG_DIR") + System.getProperty("file.separator") + CONFIG_FILE_NAME);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
