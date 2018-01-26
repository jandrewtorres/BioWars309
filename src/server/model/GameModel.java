package server.model;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.model.Player.PLAYER_STATUS;

public class GameModel extends Observable {
	private ObservableList<Player> players;
	public Timer gameTimer;
	public ObjectProperty<GAME_STATUS> gameStatusProperty;
	public LongProperty currentTimeProperty;
		
	public static enum GAME_STATUS {
		WAITING("Waiting for players..."),
		IN_PROGRESS("Game in progress..."),
		OVER("Game Over!");
		
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
				tick();
			});
		}
	}
	
	public GameModel() {
		this.players = FXCollections.observableArrayList();
		gameStatusProperty = new SimpleObjectProperty<>(GAME_STATUS.WAITING);
		gameTimer = null;
		currentTimeProperty = new SimpleLongProperty(0);
	}
	
	public ObjectProperty<GAME_STATUS> statusProperty() {
		return gameStatusProperty;
	}
	
	public LongProperty currentTimeProperty() {
		return currentTimeProperty;
	}
	
	public ObservableList<Player> getPlayers() {
		return players;
	}
	
	public void removePlayer(Player player) {
		players.remove(player);
		
		setChanged();
		notifyObservers("UPDATE_PLAYERS");
	}
	
	public void addPlayer(Player player) {
		players.add(player);
		
		setChanged();
		notifyObservers("UPDATE_PLAYERS");
	}
	
	public void startGame() {
		gameStatusProperty.setValue(GAME_STATUS.IN_PROGRESS);
		gameTimer = new Timer();
		gameTimer.scheduleAtFixedRate(new ClockTask(), 0, 1000);
		
		setChanged();
		notifyObservers("GAME_STARTED");
	}
	
	public void tick() {
		for(Player player : players) {
			player.tick();
			
			setChanged();
			notifyObservers("TICK");
		}
	}
	
	public void setPlayerStatusReady(String playerName) {
		getPlayerByName(playerName).statusProperty.set(PLAYER_STATUS.READY);
		
		setChanged();
		notifyObservers("UPDATE_PLAYERS");
		
		if(shouldGameStart()) {
			startGame();
		}
	}
	
	private Player getPlayerByName(String playerName) {
		for(Player p : players) {
			if(p.nameProperty.get().equals(playerName)) {
				return p;
			}
		}
		return null;
	}
	
	private Boolean shouldGameStart() {
		Boolean shouldStart = true;
		for(Player p : players) {
			if(!p.statusProperty.get().equals(PLAYER_STATUS.READY)) {
				shouldStart = false;
			}
		}
		
		if(players.size() < 2) {
			shouldStart = false;
		}
		
		return shouldStart;
	}
}
