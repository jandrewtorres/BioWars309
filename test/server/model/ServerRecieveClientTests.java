package server.model;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import server.ServerApp;
import server.ServerSocketHandler;
import server.model.GameModel;
import server.model.GameModel.GAME_STATUS;
import server.model.Player.PLAYER_STATUS;
import server.viewer.ServerViewerController;
import client.Client;
import client.ClientSocket;
import client.ServerCommunicator;
import client.model.ClientModel;
import javafx.stage.Stage;

public class ServerRecieveClientTests 
{	    
    private static final String CONFIG_FILE_NAME = "client_config.properties";
    private static final String CONFIG_DIR_SYSTEM_PROPERTY_NAME = "CONFIG_DIR";
    private static final Logger clientLogger = Logger.getLogger(Client.class.getName());

    private ClientSocket clientSocket;
    private Properties clientProperties;
    private Stage primaryStage;
    private ClientModel clientModel;
    
    public enum CLIENT_PROPERTIES {
    		HOST("Host"),
    		SOCKET_PORT("SocketPort");
    	
    		private String text;
    		
    		private CLIENT_PROPERTIES(String n) {
    			text = n;
    		}
    		
    		public String getText() {
    			return text;
    		}
    }
	
    private void loadProperties(String propertiesFile) {
		try 
		{
			clientProperties = new Properties();
			clientProperties.load(new FileInputStream(propertiesFile));
		} catch (Exception e) 
		{
			clientLogger.logp(Level.SEVERE, Client.class.getName(), "loadProperties", "Exception in reading properties file.");
		}
	}

	private String getPropFile() {
		if(System.getProperty(CONFIG_DIR_SYSTEM_PROPERTY_NAME) == null) {
			System.setProperty(CONFIG_DIR_SYSTEM_PROPERTY_NAME, "config");
		}
		return System.getProperty(CONFIG_DIR_SYSTEM_PROPERTY_NAME) 
				+ System.getProperty("file.separator") 
				+ CONFIG_FILE_NAME;
	}
	
	final String CONFIG_FILE_NAME_SERVER = "server_config.properties";
    final String CONFIG_DIR_SYSTEM_PROPERTY_NAME_SERVER = "CONFIG_DIR";
    final Logger serverLogger = Logger.getLogger(ServerApp.class.getName());

	Properties serverProperties;
	
	public enum SERVER_PROPERTIES {
		CLIENT_PORT("ClientPort");
		
		private String text;
		
		private SERVER_PROPERTIES(String n) {
			text = n;
		}
		
		public String getText() {
			return text;
		}
	}
	
	private String getPropFileServer() {
        if (System.getProperty(CONFIG_DIR_SYSTEM_PROPERTY_NAME_SERVER) == null) {
            System.setProperty(CONFIG_DIR_SYSTEM_PROPERTY_NAME_SERVER, "config");
        }
		return System.getProperty(CONFIG_DIR_SYSTEM_PROPERTY_NAME_SERVER)
				+ System.getProperty("file.separator")
				+ CONFIG_FILE_NAME_SERVER;
	}
	
	private void loadPropertiesServer(String propertiesFile) {
		try {
			serverProperties = new Properties();
			serverProperties.load(new FileInputStream(propertiesFile));
		} catch (Exception e) 
		{
			serverLogger.logp(Level.SEVERE, ServerApp.class.getName(), "loadProperties", "Exception loading properties file");
		}
	}
	
	@Test
	public void serverRecieveReg1() throws Exception
	{		
		/*
		loadPropertiesServer(getPropFileServer());		
		GameModel game = new GameModel();
		
		ServerSocketHandler sockHandler = new ServerSocketHandler(game, Integer.parseInt(serverProperties.getProperty(SERVER_PROPERTIES.CLIENT_PORT.text)));
		sockHandler.start();
		
		
		loadProperties(getPropFile());
		
		clientSocket = new ClientSocket(clientProperties.getProperty(CLIENT_PROPERTIES.HOST.text).trim(),
				Integer.parseInt(clientProperties.getProperty(CLIENT_PROPERTIES.SOCKET_PORT.text).trim()));
		ServerCommunicator communicator = new ServerCommunicator(clientSocket.getOutputStream(), clientSocket.getInputStream());
				
		String clientName = "BOBBY";		
		Document messageDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element registerElem = messageDoc.createElement("REGISTER");		
		Element nameElem = messageDoc.createElement("NAME");
		nameElem.appendChild(messageDoc.createTextNode(clientName));
		registerElem.appendChild(nameElem);	
		messageDoc.appendChild(registerElem);	
		
		sockHandler.getClientCommunicator().receiveObject(messageDoc);
		
		assert(game.getPlayers().size() == 1);
		
		clientSocket.closeSocket();
		sockHandler.closeSocket();
		*/		
	}
	
	@Test
	public void serverRecieveReg2() throws Exception
	{		
		/*
		loadPropertiesServer(getPropFileServer());		
		GameModel game = new GameModel();
		
		ServerSocketHandler sockHandler = new ServerSocketHandler(game, Integer.parseInt(serverProperties.getProperty(SERVER_PROPERTIES.CLIENT_PORT.text)));
		sockHandler.start();
		
		
		loadProperties(getPropFile());
		
		clientSocket = new ClientSocket(clientProperties.getProperty(CLIENT_PROPERTIES.HOST.text).trim(),
				Integer.parseInt(clientProperties.getProperty(CLIENT_PROPERTIES.SOCKET_PORT.text).trim()));
		ServerCommunicator communicator = new ServerCommunicator(clientSocket.getOutputStream(), clientSocket.getInputStream());
				
		String clientName = "BOBBY";		
		Document messageDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element registerElem = messageDoc.createElement("REGISTER");		
		Element nameElem = messageDoc.createElement("NAME");
		nameElem.appendChild(messageDoc.createTextNode(clientName));
		registerElem.appendChild(nameElem);	
		messageDoc.appendChild(registerElem);
		sockHandler.getClientCommunicator().receiveObject(messageDoc);
		
		String clientName1 = "BILLY";		
		Document messageDoc1 = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element registerElem1 = messageDoc1.createElement("REGISTER");		
		Element nameElem1 = messageDoc1.createElement("NAME");
		nameElem1.appendChild(messageDoc1.createTextNode(clientName1));
		registerElem1.appendChild(nameElem1);	
		messageDoc1.appendChild(registerElem1);	
		
		sockHandler.getClientCommunicator().receiveObject(messageDoc1);
		
		assert(game.getPlayers().size() == 2);
	
		clientSocket.closeSocket();
		sockHandler.closeSocket();
		*/		
	}
	
	@Test
	public void serverRecieveCheckReady() throws Exception
	{
		/*
		loadPropertiesServer(getPropFileServer());		
		GameModel game = new GameModel();
		
		ServerSocketHandler sockHandler = new ServerSocketHandler(game, Integer.parseInt(serverProperties.getProperty(SERVER_PROPERTIES.CLIENT_PORT.text)));
		sockHandler.start();
		
		
		loadProperties(getPropFile());
		
		clientSocket = new ClientSocket(clientProperties.getProperty(CLIENT_PROPERTIES.HOST.text).trim(),
				Integer.parseInt(clientProperties.getProperty(CLIENT_PROPERTIES.SOCKET_PORT.text).trim()));
		ServerCommunicator communicator = new ServerCommunicator(clientSocket.getOutputStream(), clientSocket.getInputStream());
			
		//Register Bobby
		String clientName = "BOBBY";		
		Document messageDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element registerElem = messageDoc.createElement("REGISTER");		
		Element nameElem = messageDoc.createElement("NAME");
		nameElem.appendChild(messageDoc.createTextNode(clientName));
		registerElem.appendChild(nameElem);	
		messageDoc.appendChild(registerElem);
		sockHandler.getClientCommunicator().receiveObject(messageDoc);
		
		//Register Billy
		String clientName1 = "BILLY";		
		Document messageDoc1 = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element registerElem1 = messageDoc1.createElement("REGISTER");		
		Element nameElem1 = messageDoc1.createElement("NAME");
		nameElem1.appendChild(messageDoc1.createTextNode(clientName1));
		registerElem1.appendChild(nameElem1);	
		messageDoc1.appendChild(registerElem1);	
		
		sockHandler.getClientCommunicator().receiveObject(messageDoc1);
		
		//Ready up Bobby
		Document messageDoc3 = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element requestPlayersElem = messageDoc3.createElement("PLAYER_READY");		
		Element name = messageDoc3.createElement("NAME");
		name.appendChild(messageDoc3.createTextNode("BOBBY"));
		requestPlayersElem.appendChild(name);
		messageDoc3.appendChild(requestPlayersElem);		
		sockHandler.getClientCommunicator().receiveObject(messageDoc3);
		Player Bobby = game.getPlayerByName("BOBBY");
		assert(Bobby.statusProperty().getValue() == PLAYER_STATUS.READY);
		
		clientSocket.closeSocket();
		sockHandler.closeSocket();
		*/
	}
	@Test
	public void serverRecieveCheckBothReady() throws Exception
	{
		/*
		loadPropertiesServer(getPropFileServer());		
		GameModel game = new GameModel();
		
		ServerSocketHandler sockHandler = new ServerSocketHandler(game, Integer.parseInt(serverProperties.getProperty(SERVER_PROPERTIES.CLIENT_PORT.text)));
		sockHandler.start();
		
		
		loadProperties(getPropFile());
		
		clientSocket = new ClientSocket(clientProperties.getProperty(CLIENT_PROPERTIES.HOST.text).trim(),
				Integer.parseInt(clientProperties.getProperty(CLIENT_PROPERTIES.SOCKET_PORT.text).trim()));
		ServerCommunicator communicator = new ServerCommunicator(clientSocket.getOutputStream(), clientSocket.getInputStream());
		//Register Bobby		
		String clientName = "BOBBY";		
		Document messageDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element registerElem = messageDoc.createElement("REGISTER");		
		Element nameElem = messageDoc.createElement("NAME");
		nameElem.appendChild(messageDoc.createTextNode(clientName));
		registerElem.appendChild(nameElem);	
		messageDoc.appendChild(registerElem);
		sockHandler.getClientCommunicator().receiveObject(messageDoc);
		
		//Register Billy
		String clientName1 = "BILLY";		
		Document messageDoc1 = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element registerElem1 = messageDoc1.createElement("REGISTER");		
		Element nameElem1 = messageDoc1.createElement("NAME");
		nameElem1.appendChild(messageDoc1.createTextNode(clientName1));
		registerElem1.appendChild(nameElem1);	
		messageDoc1.appendChild(registerElem1);			
		sockHandler.getClientCommunicator().receiveObject(messageDoc1);
		
		// Bobby Ready
		Document messageDoc3 = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element requestPlayersElem = messageDoc3.createElement("PLAYER_READY");		
		Element name = messageDoc3.createElement("NAME");
		name.appendChild(messageDoc3.createTextNode(clientName));
		requestPlayersElem.appendChild(name);
		messageDoc3.appendChild(requestPlayersElem);		
		sockHandler.getClientCommunicator().receiveObject(messageDoc3);
		Player Bobby = game.getPlayerByName("BOBBY");
		
		// Billy Ready
		Document messageDoc4 = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element requestPlayersElem1 = messageDoc4.createElement("PLAYER_READY");		
		Element name1 = messageDoc4.createElement("NAME");
		name1.appendChild(messageDoc4.createTextNode(clientName1));
		requestPlayersElem1.appendChild(name1);
		messageDoc4.appendChild(requestPlayersElem1);	
		sockHandler.getClientCommunicator().receiveObject(messageDoc3);
		Player Billy = game.getPlayerByName("BOBBY");
		
		assert(Bobby.statusProperty().getValue() == PLAYER_STATUS.READY);
		assert(Billy.statusProperty().getValue() == PLAYER_STATUS.READY);
		
		clientSocket.closeSocket();
		sockHandler.closeSocket();
		*/
	}
}
