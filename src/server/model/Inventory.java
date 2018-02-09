package server.model;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.model.cure.Cure;
import server.model.cure.CureFactory;
import server.model.cure.CureFactory.CURE_TYPE;
import server.model.virus.Virus;
import server.model.virus.VirusFactory;
import server.model.virus.VirusFactory.VIRUS_TYPE;

public class Inventory {
	private ObservableList<Virus> viruses;
	private ReadOnlyIntegerWrapper coldVirusCount;
	private ReadOnlyIntegerWrapper fluVirusCount;
	private ReadOnlyIntegerWrapper poxVirusCount;
	private ReadOnlyIntegerWrapper sarsVirusCount;
	private ObservableList<Cure> cures;
	private ReadOnlyIntegerWrapper coldCureCount;
	private ReadOnlyIntegerWrapper fluCureCount;
	private ReadOnlyIntegerWrapper poxCureCount;
	private ReadOnlyIntegerWrapper sarsCureCount;
	public Inventory() {
		viruses = FXCollections.observableArrayList();
		coldVirusCount = new ReadOnlyIntegerWrapper(0);
		fluVirusCount = new ReadOnlyIntegerWrapper(0);
		poxVirusCount = new ReadOnlyIntegerWrapper(0);
		sarsVirusCount = new ReadOnlyIntegerWrapper(0);
		
		cures = FXCollections.observableArrayList();
		coldCureCount = new ReadOnlyIntegerWrapper(0);
		fluCureCount = new ReadOnlyIntegerWrapper(0);
		poxCureCount = new ReadOnlyIntegerWrapper(0);
		sarsCureCount = new ReadOnlyIntegerWrapper(0);
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
	
	public ReadOnlyIntegerProperty getColdCureCount() {
		return coldCureCount.getReadOnlyProperty();
	}
	
	public ReadOnlyIntegerProperty getFluCureCount() {
		return fluCureCount.getReadOnlyProperty();
	}
	
	public ReadOnlyIntegerProperty getPoxCureCount() {
		return poxCureCount.getReadOnlyProperty();
	}
	
	public ReadOnlyIntegerProperty getSarsCureCount() {
		return sarsCureCount.getReadOnlyProperty();
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
	
	public Boolean hasCureOfType(CURE_TYPE type) {
		Boolean found = false;
		for(Cure c : cures) {
			if(c.getType().equals(type)) {
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
	
	public void buyCure(CURE_TYPE type) {
		Cure c = new CureFactory().createCure(type);
		cures.add(c);
		setCureCount(type, 1);
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
	
	public void useCure(CURE_TYPE type) {
		setCureCount(type, -1);
		
		for(Cure c : cures) {
			if(c.getType().equals(type)) {
				cures.remove(c);
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
	
	private void setCureCount(CURE_TYPE type, Integer amount) {
		switch(type) {
			case COLDCURE:
				coldCureCount.set(coldCureCount.get() + amount);
				break;
			case FLUCURE:
				fluCureCount.set(fluCureCount.get() + amount);
				break;
			case POXCURE:
				poxCureCount.set(poxCureCount.get() + amount);
				break;
			case SARSCURE:
				sarsCureCount.set(sarsCureCount.get() + amount);
				break;
			default:
				break;
		}
	}
}
