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
import server.model.ObserverMessage.MESSAGE_TYPE;
import server.model.Player.PLAYER_STATUS;

public class GameModel extends Observable {
	private ObservableList<Player> players;
	public Timer gameTimer;
	public ObjectProperty<GAME_STATUS> statusProperty;
	public LongProperty currentTimeProperty;
		
	public enum GAME_STATUS {
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
		statusProperty = new SimpleObjectProperty<>(GAME_STATUS.WAITING);
		gameTimer = null;
		currentTimeProperty = new SimpleLongProperty(0);
	}
	
	public ObservableList<Player> getPlayers() {
		return players;
	}
	
	public void removePlayer(Player player) {
		players.remove(player);
		
		setChanged();
		notifyObservers(new ObserverMessage(MESSAGE_TYPE.UPDATE_LOBBY, null));
	}
	
	public void addPlayer(Player player) {
		players.add(player);
		
		setChanged();
		notifyObservers(new ObserverMessage(MESSAGE_TYPE.UPDATE_LOBBY, null));
	}
	
	public void startGame() {
		statusProperty.setValue(GAME_STATUS.IN_PROGRESS);
		gameTimer = new Timer();
		gameTimer.scheduleAtFixedRate(new ClockTask(), 0, 1000);
		
		setChanged();
		notifyObservers(new ObserverMessage(MESSAGE_TYPE.GAME_STARTED, null));
	}
	
	public void tick() {
		for(Player player : players) {
			player.tick();
			
			setChanged();
			notifyObservers("TICK");
		}
	}
	
	public void setPlayerStatusReady(String playerName) {
		Player p = getPlayerByName(playerName);
		
		if(p == null)
			return;
		
		p.statusProperty.set(PLAYER_STATUS.READY);
		
		setChanged();
		notifyObservers(new ObserverMessage(MESSAGE_TYPE.UPDATE_LOBBY, null));
		
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
