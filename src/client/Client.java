package client;

import java.io.FileInputStream;
import java.util.Properties;

import client.login.LoginController;
import client.model.ClientModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application {
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
	
	private void loadProperties(String propertiesFile) {
		try 
		{
			clientProperties = new Properties();
			clientProperties.load(new FileInputStream(propertiesFile));
		} catch (Exception e) 
		{
			System.out.println("Exception in reading properties file.");
		}
	}

	private String getPropFile() {
		if(System.getProperty("CONFIG_DIR") == null) {
			System.setProperty("CONFIG_DIR", "config");
		}
		return System.getProperty("CONFIG_DIR") 
				+ System.getProperty("file.separator") 
				+ CONFIG_FILE_NAME;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		loadProperties(getPropFile());
		
		clientSocket = new ClientSocket(clientProperties.getProperty(CLIENT_PROPERTIES.HOST.name).trim(),
				Integer.parseInt(clientProperties.getProperty(CLIENT_PROPERTIES.SOCKET_PORT.name).trim()));
		ServerCommunicator communicator = new ServerCommunicator(clientSocket.getOutputStream(), clientSocket.getInputStream());
		ClientModel clientModel = new ClientModel(communicator);
		communicator.setModel(clientModel);
		LoginController controller = new LoginController(clientModel);
		
		stage.setTitle("Biowars");
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/client/login/Login.fxml"));
		loader.setController(controller);
		Parent root = (Parent) loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		Thread clientThread = new Thread(communicator);
		clientThread.start();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
