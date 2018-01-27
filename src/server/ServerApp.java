package server;

import java.io.FileInputStream;
import java.util.Properties;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import server.model.GameModel;
import server.viewer.ServerViewerController;

public class ServerApp extends Application {
	
	final private static String CONFIG_FILE_NAME = "server_config.properties";
    final private static String CONFIG_DIR_SYSTEM_PROPERTY_NAME = "CONFIG_DIR";

	private Properties serverProperties;
	
	private enum SERVER_PROPERTIES {
		CLIENT_PORT("ClientPort");
		
		public String text;
		
		private SERVER_PROPERTIES(String n) {
			text = n;
		}
	}
	
	private String getPropFile() {
        if (System.getProperty("CONFIG_DIR") == null) {
            System.setProperty("CONFIG_DIR", "config");
        }
		return System.getProperty("CONFIG_DIR")
				+ System.getProperty("file.separator")
				+ CONFIG_FILE_NAME;
	}
	
	private void loadProperties(String propertiesFile) {
		try {
			serverProperties = new Properties();
			serverProperties.load(new FileInputStream(propertiesFile));
		} catch (Exception e) 
		{
			System.out.println("Exception in reading properties file");
		}
	}
	
	private void startListening(GameModel game) {
		ServerSocketHandler sockHandler = new ServerSocketHandler(game, Integer.parseInt(serverProperties.getProperty(SERVER_PROPERTIES.CLIENT_PORT.text)));
		sockHandler.start();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		loadProperties(getPropFile());
		
		GameModel game = new GameModel();
		ServerViewerController controller = new ServerViewerController(game);
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/ServerViewer.fxml"));
		loader.setController(controller);
		Parent root = (Parent) loader.load();
		stage.setTitle("Biowars Server");
		Scene scene = new Scene(root);
		
		stage.setScene(scene);
		stage.show();
				
		startListening(game);
		
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent t) {
		        Platform.exit();
		        System.exit(0);
		    }
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
