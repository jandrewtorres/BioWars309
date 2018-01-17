package server;

import java.io.FileInputStream;
import java.util.Properties;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.model.ServerModel;

public class ServerApp extends Application {
	
	private static String CONFIG_FILE_NAME = "server_config.properties";
	private Properties serverProperties;
	
	private static enum SERVER_PROPERTIES {
		CLIENT_PORT("ClientPort");
		
		public String name;
		
		private SERVER_PROPERTIES(String n) {
			name = n;
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
	
	private void startListening(ServerViewerController controller) {
		ServerSocketHandler sockHandler = new ServerSocketHandler(controller, Integer.parseInt(serverProperties.getProperty(SERVER_PROPERTIES.CLIENT_PORT.name)));
		sockHandler.start();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ServerViewer.fxml"));
		Parent root = (Parent) loader.load();
		stage.setTitle("Biowars Server");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		ServerViewerController controller = loader.getController();
				
		loadProperties(getPropFile());
		startListening(controller);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
