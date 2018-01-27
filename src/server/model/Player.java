package server.model;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Player {
	public StringProperty nameProperty;
	public ObjectProperty<PLAYER_STATUS> statusProperty;
	
	private IntegerProperty gold;
	private IntegerProperty population;
	

	private Integer goldIncreaseIncrement;
	private Integer populationIncreaseIncrement;
	
	public enum PLAYER_STATUS {
		IN_LOBBY("IN_LOBBY"),
		READY("READY"),
		PLAYING("PLAYING");
		
		String text;
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
		this.nameProperty = new SimpleStringProperty(name);
		this.gold = new SimpleIntegerProperty(0);
		this.population = new SimpleIntegerProperty(10000);
		this.statusProperty = new SimpleObjectProperty<PLAYER_STATUS>(PLAYER_STATUS.IN_LOBBY);
		
		goldIncreaseIncrement = 10;
		populationIncreaseIncrement = 5;
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
}
