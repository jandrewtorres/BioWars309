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
	
	public void buyVirus(VIRUS_TYPE type) {
		Virus v = new VirusFactory().createVirus(type);
		viruses.add(v);
		switch(type) {
			case COLD:
				coldVirusCount.set(coldVirusCount.get() + 1);
				break;
			case FLU:
				fluVirusCount.set(fluVirusCount.get() + 1);
				break;
			case POX:
				poxVirusCount.set(poxVirusCount.get() + 1);
				break;
			case SARS:
				sarsVirusCount.set(sarsVirusCount.get() + 1);
				break;
			default:
				break;
		}
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
}
