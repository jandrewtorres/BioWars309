package server.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Player {
	private StringProperty name;
	private IntegerProperty gold;
	
	public Player(String name) {
		this.name = new SimpleStringProperty(name);
		this.gold = new SimpleIntegerProperty(0);
	}
	
	public StringProperty nameProperty() {
		return name;
	}
	
	public IntegerProperty goldProperty() {
		return gold;
	}
}
