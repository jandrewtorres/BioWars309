package server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class GameModel extends Observable {
	public List<Player> players;
	
	public GameModel() {
		this.players = new ArrayList<>();
	}

	public void addPlayer(Player player) {
		players.add(player);
		
		setChanged();
		notifyObservers();
	}
}
