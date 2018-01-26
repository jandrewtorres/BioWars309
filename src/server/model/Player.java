package server.model;

import java.lang.invoke.MethodHandles.Lookup;
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
	

	private Integer GOLD_INCREASE_VALUE;
	private Integer POPULATION_INCREASE_VALUE;
	
	public static enum PLAYER_STATUS {
		IN_LOBBY("IN_LOBBY"),
		READY("READY"),
		PLAYING("PLAYING");
		
		String name;
	    private static Map map = new HashMap<>();

		static {
	        for (PLAYER_STATUS status : PLAYER_STATUS.values()) {
	            map.put(status.name, status);
	        }
	    }
		@Override
		public String toString() {
			return name;
		}
		
	    public static PLAYER_STATUS fromString(String status) {
	        return (PLAYER_STATUS) map.get(status);
	    }
		
		private PLAYER_STATUS(String n) {
			name = n;
		}
	}
	
	public Player(String name) {
		this.nameProperty = new SimpleStringProperty(name);
		this.gold = new SimpleIntegerProperty(0);
		this.population = new SimpleIntegerProperty(10000);
		this.statusProperty = new SimpleObjectProperty<PLAYER_STATUS>(PLAYER_STATUS.IN_LOBBY);
		
		GOLD_INCREASE_VALUE = 10;
		POPULATION_INCREASE_VALUE = 5;
	}
	
	public IntegerProperty goldProperty() {
		return gold;
	}
	
	public IntegerProperty populationProperty() {
		return population;
	}
	
	public void tick() {
		updateGold();
		updatePopulation();
	}
	
	private void updateGold() {
		gold.set(gold.get() + GOLD_INCREASE_VALUE);
	}
	
	private void updatePopulation() {
		population.set(population.get() + POPULATION_INCREASE_VALUE);
	}
}
