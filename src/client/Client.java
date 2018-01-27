package client;

import java.io.FileInputStream;
import java.util.Properties;

import client.gameplay.GamePlayController;
import client.lobby.LobbyController;
import client.login.LoginController;
import client.model.ClientModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Client extends Application {
    private static final String CONFIG_FILE_NAME = "client_config.properties";
    
    private ClientSocket clientSocket;
    private Properties clientProperties;
    private Stage primaryStage;
    private ClientModel clientModel;
    
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
		this.primaryStage = stage;
		
		loadProperties(getPropFile());
		
		clientSocket = new ClientSocket(clientProperties.getProperty(CLIENT_PROPERTIES.HOST.name).trim(),
				Integer.parseInt(clientProperties.getProperty(CLIENT_PROPERTIES.SOCKET_PORT.name).trim()));
		ServerCommunicator communicator = new ServerCommunicator(clientSocket.getOutputStream(), clientSocket.getInputStream());
		clientModel = new ClientModel(communicator);
		communicator.setModel(clientModel);
		
		LoginController controller = new LoginController(clientModel);
		controller.setClientApp(this);
		
		stage.setTitle("Biowars");
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/Login.fxml"));
		loader.setController(controller);
		Parent root = (Parent) loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		Thread clientThread = new Thread(communicator);
		clientThread.start();
		
		clientModel.gameStarted.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // Only if completed
                if (newValue) {
                		try {
						switchToGamePlay();
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
            }
        });
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent t) {
		    		clientSocket.closeSocket();
		        Platform.exit();
		        System.exit(0);
		    }
		});
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public void switchToLobby() throws Exception {

		LobbyController controller = new LobbyController(clientModel);
		controller.setClientApp(this);
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/Lobby.fxml"));
		loader.setController(controller);
		Parent root = (Parent) loader.load();
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
	}
	
	public void switchToGamePlay() throws Exception {
		GamePlayController controller = new GamePlayController(clientModel);
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/GamePlay.fxml"));
		loader.setController(controller);
		Parent root = (Parent) loader.load();
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
