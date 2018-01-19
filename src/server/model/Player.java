package server.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Player {
	private StringProperty name;
	private IntegerProperty gold;
	private IntegerProperty population;
	
	private Integer GOLD_INCREASE_VALUE;
	private Integer POPULATION_INCREASE_VALUE;
	
	public Player(String name) {
		this.name = new SimpleStringProperty(name);
		this.gold = new SimpleIntegerProperty(0);
		this.population = new SimpleIntegerProperty(10000);
		
		GOLD_INCREASE_VALUE = 10;
		POPULATION_INCREASE_VALUE = 5;
	}
	
	public StringProperty nameProperty() {
		return name;
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
