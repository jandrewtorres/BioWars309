package client;

import java.io.FileInputStream;
import java.util.Properties;

import client.gameplay.GamePlayController;
import client.gameplay.VirusMenuController;
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
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Client extends Application {
    final private static String CONFIG_FILE_NAME = "client_config.properties";
    final private static String CONFIG_DIR_SYSTEM_PROPERTY_NAME = "CONFIG_DIR";
    
    private ClientSocket clientSocket;
    private Properties clientProperties;
    private Stage primaryStage;
    private ClientModel clientModel;
    
    private enum CLIENT_PROPERTIES {
    		HOST("Host"),
    		SOCKET_PORT("SocketPort");
    	
    		public String text;
    		
    		private CLIENT_PROPERTIES(String n) {
    			text = n;
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
		if(System.getProperty(CONFIG_DIR_SYSTEM_PROPERTY_NAME) == null) {
			System.setProperty(CONFIG_DIR_SYSTEM_PROPERTY_NAME, "config");
		}
		return System.getProperty(CONFIG_DIR_SYSTEM_PROPERTY_NAME) 
				+ System.getProperty("file.separator") 
				+ CONFIG_FILE_NAME;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.primaryStage = stage;
		
		loadProperties(getPropFile());
		
		clientSocket = new ClientSocket(clientProperties.getProperty(CLIENT_PROPERTIES.HOST.text).trim(),
				Integer.parseInt(clientProperties.getProperty(CLIENT_PROPERTIES.SOCKET_PORT.text).trim()));
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
		controller.setClientApp(this);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/GamePlay.fxml"));
		loader.setController(controller);
		Parent root = (Parent) loader.load();
		Scene scene = new Scene(root);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
	}
	
	public void openVirusMenu() throws Exception{
		VirusMenuController controller = new VirusMenuController(clientModel);
		controller.setClientApp(this);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/VirusMenu.fxml"));
		loader.setController(controller);
		Parent root = (Parent) loader.load();
		Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initOwner(primaryStage);
        stage.setTitle("Virus Menu");
        stage.setScene(new Scene(root));
        stage.show();
	}
	
	public void closeVirusMenu(Button source) throws Exception{
		Stage stage = (Stage)source.getScene().getWindow();
		stage.hide();
	}
	public void openVirusMkt() throws Exception{
		VirusMenuController controller = new VirusMenuController(clientModel);
		controller.setClientApp(this);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/virusBuy.fxml"));
		loader.setController(controller);
		Parent root = (Parent) loader.load();
		Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initOwner(primaryStage);
        stage.setTitle("Virus Menu");
        stage.setScene(new Scene(root));
        stage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
