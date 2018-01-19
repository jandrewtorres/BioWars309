package server.model;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameModel {
	private ObservableList<Player> players;
	public Timer gameTimer;
	public ObjectProperty<GAME_STATUS> gameStatusProperty;
	public IntegerProperty currentTimeProperty;
	
	public static enum GAME_STATUS {
		WAITING("Waiting"),
		RUNNING("Running"),
		OVER("Over");
		
		private String name;
		
		@Override
		public String toString() {
			return name;
		}
		
		private GAME_STATUS(String n) {
			name = n;
		}
	}
	
	private class ClockTask extends TimerTask {
		public void run() {
			Platform.runLater(() -> {
				currentTimeProperty.set(currentTimeProperty.get() + 1);
				System.out.println(currentTimeProperty.getValue().toString());
			});
			tick();
		}
	}
	
	public GameModel() {
		this.players = FXCollections.observableArrayList();
		gameStatusProperty = new SimpleObjectProperty<>(GAME_STATUS.WAITING);
		gameTimer = null;
		currentTimeProperty = new SimpleIntegerProperty(0);
		startGame();
	}
	
	public ObjectProperty<GAME_STATUS> statusProperty() {
		return gameStatusProperty;
	}
	
	public IntegerProperty currentTimeProperty() {
		return currentTimeProperty;
	}
	
	public ObservableList<Player> getPlayers() {
		return players;
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public void startGame() {
		gameStatusProperty.setValue(GAME_STATUS.RUNNING);
		gameTimer = new Timer();
		gameTimer.scheduleAtFixedRate(new ClockTask(), 0, 1000);
	}
	
	public void tick() {
		System.out.println("Tick");
	}
}
