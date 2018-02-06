package server.model;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.model.virus.Virus;
import server.model.virus.VirusFactory;
import server.model.virus.VirusFactory.VIRUS_TYPE;

public class Inventory {
	private ObservableList<Virus> viruses;
	private ReadOnlyIntegerWrapper coldVirusCount;
	private ReadOnlyIntegerWrapper fluVirusCount;
	private ReadOnlyIntegerWrapper poxVirusCount;
	private ReadOnlyIntegerWrapper sarsVirusCount;
	
	public Inventory() {
		viruses = FXCollections.observableArrayList();
		coldVirusCount = new ReadOnlyIntegerWrapper(0);
		fluVirusCount = new ReadOnlyIntegerWrapper(0);
		poxVirusCount = new ReadOnlyIntegerWrapper(0);
		sarsVirusCount = new ReadOnlyIntegerWrapper(0);
	}
	
	public ReadOnlyIntegerProperty getColdVirusCount() {
		return coldVirusCount.getReadOnlyProperty();
	}
	
	public ReadOnlyIntegerProperty getFluVirusCount() {
		return fluVirusCount.getReadOnlyProperty();
	}
	
	public ReadOnlyIntegerProperty getPoxVirusCount() {
		return poxVirusCount.getReadOnlyProperty();
	}
	
	public ReadOnlyIntegerProperty getSarsVirusCount() {
		return poxVirusCount.getReadOnlyProperty();
	}
	
	public Boolean hasVirusOfType(VIRUS_TYPE type) {
		Boolean found = false;
		for(Virus v : viruses) {
			if(v.getType().equals(type)) {
				found = true;
				break;
			}
		}
		return found;
	}
	
	public void buyVirus(VIRUS_TYPE type) {
		Virus v = new VirusFactory().createVirus(type);
		viruses.add(v);
		setVirusCount(type, 1);
	}
	
	public void useVirus(VIRUS_TYPE type) {
		setVirusCount(type, -1);
		
		for(Virus v : viruses) {
			if(v.getType().equals(type)) {
				viruses.remove(v);
				break;
			}
		}
	}
	
	private void setVirusCount(VIRUS_TYPE type, Integer amount) {
		switch(type) {
			case COLD:
				coldVirusCount.set(coldVirusCount.get() + amount);
				break;
			case FLU:
				fluVirusCount.set(fluVirusCount.get() + amount);
				break;
			case POX:
				poxVirusCount.set(poxVirusCount.get() + amount);
				break;
			case SARS:
				sarsVirusCount.set(sarsVirusCount.get() + amount);
				break;
			default:
				break;
		}
	}
}
