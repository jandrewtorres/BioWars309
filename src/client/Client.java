package client;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.gameplay.CureMktController;
import client.gameplay.GamePlayController;
import client.gameplay.VirusMktController;
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
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Client extends Application {
    private static final String CONFIG_FILE_NAME = "client_config.properties";
    private static final String CONFIG_DIR_SYSTEM_PROPERTY_NAME = "CONFIG_DIR";
    private static final Logger clientLogger = Logger.getLogger(Client.class.getName());

    private ClientSocket clientSocket;
    private Properties clientProperties;
    private Stage primaryStage;
    private ClientModel clientModel;
    
    private enum CLIENT_PROPERTIES {
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
		
		clientModel.getGameStartedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // Only if completed
                if (newValue) {
                		try {
						switchToGamePlay();
					} catch (Exception e) {
						clientLogger.logp(Level.SEVERE, Client.class.getName(), "start", "Exception switching to the gameplay screen.");
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
		primaryStage.centerOnScreen();
	}
	
	public void openVirusMkt() throws Exception{
		VirusMktController virusController = new VirusMktController(clientModel);
		virusController.setClientApp(this);		
		String viewLocation = "/views/VirusBuy.fxml";
		
		FXMLLoader loader = new FXMLLoader();
		setLoader(loader,virusController,viewLocation);
		setStage(loader);			
	}
	
	public void openCureMkt() throws Exception{
		CureMktController cureController = new CureMktController(clientModel);
		cureController.setClientApp(this);
		String viewLocation = "/views/CureBuy.fxml";
				
		FXMLLoader loader = new FXMLLoader();
		setLoader(loader,cureController,viewLocation);
		setStage(loader);

	}
	
	public void gameOverLoss() throws Exception{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/GameOver.fxml"));
		Canvas overlay = new Canvas(primaryStage.getWidth(),primaryStage.getHeight());
		overlay.setOpacity(0.5);
		overlay.getGraphicsContext2D().setFill(Color.DARKGREY);
		AnchorPane root = new AnchorPane();
		root.getChildren().add(overlay);
		Stage overLayStage = new Stage();
        overLayStage.initModality(Modality.APPLICATION_MODAL);
        overLayStage.initStyle(StageStyle.UNDECORATED);
        overLayStage.initOwner(primaryStage);
        Scene scene = new Scene(root);
        overLayStage.setScene(scene);
        overLayStage.show();
        Parent gameOver = (Parent) loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initOwner(overLayStage);
        Scene sceneOver = new Scene(gameOver);
        stage.setScene(sceneOver);
        stage.setX(primaryStage.getX()+Math.abs(primaryStage.getWidth()-sceneOver.getWidth())/4);
        stage.setY(primaryStage.getY()+Math.abs(primaryStage.getHeight()-sceneOver.getHeight())/4);
        stage.show();
	}
		
	public void gameOverWin() throws Exception{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/GameWin.fxml"));
		setStage(loader);
	}
		
	public void setLoader (FXMLLoader loader , Object controller, String viewLocation)
	{
		loader.setLocation(getClass().getResource(viewLocation));
		loader.setController(controller);
	}
	
	public void setStage (FXMLLoader loader) throws Exception
	{
		Parent root = (Parent) loader.load();
		Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initOwner(primaryStage);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setX(primaryStage.getX()+Math.abs(primaryStage.getWidth()-scene.getWidth())/4);
        stage.setY(primaryStage.getY()+Math.abs(primaryStage.getHeight()-scene.getHeight())/4);
        stage.show();
	}
	
	public void closeMenu(Button source) throws Exception{
		Stage stage = (Stage)source.getScene().getWindow();
		stage.hide();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
