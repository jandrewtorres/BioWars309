package server.model;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.model.virus.Virus;
import server.model.virus.VirusFactory;
import server.model.virus.VirusFactory.VIRUS_TYPE;

public class Player {
	private ReadOnlyStringWrapper nameProperty;
	private ReadOnlyObjectWrapper<PLAYER_STATUS> statusProperty;
	
	private ReadOnlyIntegerWrapper gold;
	private ReadOnlyIntegerWrapper population;

	private Integer goldIncreaseIncrement;
	private Integer populationIncreaseIncrement;
	
	private ObservableList<Virus> viruses;
	
	public enum PLAYER_STATUS {
		IN_LOBBY("IN_LOBBY"),
		READY("READY"),
		PLAYING("PLAYING");
		
		private String text;
	    private static Map map = new HashMap<>();

		static {
	        for (PLAYER_STATUS status : PLAYER_STATUS.values()) {
	            map.put(status.text, status);
	        }
	    }
		
		@Override
		public String toString() {
			return text;
		}
		
	    public static PLAYER_STATUS fromString(String status) {
	        return (PLAYER_STATUS) map.get(status);
	    }
		
		private PLAYER_STATUS(String t) {
			text = t;
		}
	}
	
	public Player(String name) {
		this.nameProperty = new ReadOnlyStringWrapper(name);
		this.gold = new ReadOnlyIntegerWrapper(0);
		this.population = new ReadOnlyIntegerWrapper(10000);
		this.statusProperty = new ReadOnlyObjectWrapper<>(PLAYER_STATUS.IN_LOBBY);
		
		goldIncreaseIncrement = 10;
		populationIncreaseIncrement = 5;
		
		viruses = FXCollections.observableArrayList();
	}
	
	public ReadOnlyIntegerProperty goldProperty() {
		return gold.getReadOnlyProperty();
	}
	
	public ReadOnlyIntegerProperty populationProperty() {
		return population.getReadOnlyProperty();
	}
	
	public ReadOnlyStringProperty nameProperty() {
		return nameProperty.getReadOnlyProperty();
	}
	
	public ReadOnlyObjectProperty<PLAYER_STATUS> statusProperty() {
		return statusProperty.getReadOnlyProperty();
	}
	
	public void setPlayerStatusReady() {
		statusProperty.set(PLAYER_STATUS.READY);
	}
	
	public void setPlayerStatus(PLAYER_STATUS status) {
		statusProperty.set(status);
	}
	
	public void tick() {
		updateGold();
		updatePopulation();
	}
	
	private void updateGold() {
		gold.set(gold.get() + goldIncreaseIncrement);
	}
	
	private void updatePopulation() {
		population.set(population.get() + populationIncreaseIncrement);
	}
	
	public void updateStats(Integer newGold, Integer newPop) {
		population.set(newPop);
		gold.set(newGold);
	}
	
	public void buyVirus(VIRUS_TYPE type) {
		System.out.println("Player: " + nameProperty().get() + " buying a " + type.toString() + "virus");
		Virus v = new VirusFactory().createVirus(type);
		viruses.add(v);
		gold.set(gold.get() - v.getPrice());
	}
}
