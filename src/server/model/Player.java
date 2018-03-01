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
import server.model.cure.Cure;
import server.model.cure.CureFactory;
import server.model.cure.CureFactory.CURE_TYPE;
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
	private Integer virusDecrementFactor;

	private Inventory playerInventory;
	private ObservableList<Virus> virusApplied;

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
		this.gold = new ReadOnlyIntegerWrapper(500);
		this.population = new ReadOnlyIntegerWrapper(10000);
		this.statusProperty = new ReadOnlyObjectWrapper<>(PLAYER_STATUS.IN_LOBBY);
		
		goldIncreaseIncrement = 10;
		populationIncreaseIncrement = 5;
		
		playerInventory = new Inventory();
		virusApplied = FXCollections.observableArrayList();
		
		virusDecrementFactor = 0;
	}
	
	public ReadOnlyIntegerProperty goldProperty() {
		return gold.getReadOnlyProperty();
	}
	
	public ReadOnlyIntegerProperty populationProperty() {
		return population.getReadOnlyProperty();
	}
	public Integer populationIncrementProperty() {
		return populationIncreaseIncrement;
	}
	public Integer goldIncrementProperty() {
		return goldIncreaseIncrement;
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
		population.set(population.get() + populationIncreaseIncrement - virusDecrementFactor);
	}
	
	public void updateStats(Integer newGold, Integer newPop) {
		population.set(newPop);
		gold.set(newGold);
	}
	
	public void buyVirus(VIRUS_TYPE type) {
		playerInventory.buyVirus(type);
		gold.set(gold.get() - new VirusFactory().createVirus(type).getPrice());
	}
	
	public void buyCure(CURE_TYPE type) {
		playerInventory.buyCure(type);
		gold.set(gold.get() - new CureFactory().createCure(type).getPrice());
	}
	
	public Inventory getInventory() {
		return playerInventory;
	}
	
	public void applyVirus(Virus v) {
		virusApplied.add(v);
		virusDecrementFactor = virusDecrementFactor + v.getDecrementToPopulation();
	}
	
	public void applyCure(CURE_TYPE ct) {
		Cure c = new CureFactory().createCure(ct);
		Virus v = new VirusFactory().createVirus(c.getCounterActedVirus());
		Virus s = null;
		for(Virus va : virusApplied) {
			if(va.getType() == v.getType()) {
				s = va;
				break;
			}
		}
		if (s != null) {
			virusApplied.remove(s);
			virusDecrementFactor -= s.getDecrementToPopulation();
		}
	}
}
