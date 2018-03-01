package server.model;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.model.ObserverMessage.MESSAGE_TYPE;
import server.model.Player.PLAYER_STATUS;
import server.model.virus.VirusFactory;
import server.model.virus.VirusFactory.VIRUS_TYPE;

public class GameModel extends Observable {
	private ObservableList<Player> players;
	private ObservableList<Player> readOnlyPlayers;
	private Timer gameTimer;
	private ReadOnlyObjectWrapper<GAME_STATUS> statusProperty;
	private ReadOnlyLongWrapper currentTimeProperty;
		
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
		this.readOnlyPlayers = FXCollections.unmodifiableObservableList(this.players);
		statusProperty = new ReadOnlyObjectWrapper<>(GAME_STATUS.WAITING);
		gameTimer = null;
		currentTimeProperty = new ReadOnlyLongWrapper(0);
	}
	
	public ObservableList<Player> getPlayers() {
		return readOnlyPlayers;
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
		}
		
		setChanged();
		notifyObservers(new ObserverMessage(MESSAGE_TYPE.GAME_UPDATE, null));
	}
	
	public void setPlayerStatusReady(String playerName) {
		Player p = getPlayerByName(playerName);
		
		if(p == null)
			return;
		
		p.setPlayerStatusReady();
		
		setChanged();
		notifyObservers(new ObserverMessage(MESSAGE_TYPE.UPDATE_LOBBY, null));
		
		if(shouldGameStart()) {
			startGame();
		}
	}
	
	public Player getPlayerByName(String playerName) {
		for(Player p : players) {
			if(p.nameProperty().get().equals(playerName)) {
				return p;
			}
		}
		return null;
	}
	
	private Boolean shouldGameStart() {
		Boolean shouldStart = true;
		for(Player p : players) {
			if(!p.statusProperty().get().equals(PLAYER_STATUS.READY)) {
				shouldStart = false;
			}
		}
		
		if(players.size() < 2) {
			shouldStart = false;
		}
		
		return shouldStart;
	}
	
	public ReadOnlyObjectProperty<GAME_STATUS> getGameStatusProperty() {
		return statusProperty.getReadOnlyProperty();
	}
	
	public ReadOnlyLongProperty getCurrentTimeProperty() {
		return currentTimeProperty.getReadOnlyProperty();
	}

	public void applyVirus(Player associatedPlayer, Player opponent, VIRUS_TYPE vt) {
		associatedPlayer.getInventory().useVirus(vt);
		opponent.applyVirus(new VirusFactory().createVirus(vt));
	}
}
