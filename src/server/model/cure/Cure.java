package server.model.cure;

import server.model.cure.CureFactory.CURE_TYPE;
import server.model.virus.VirusFactory.VIRUS_TYPE;

public abstract class Cure {
	protected Integer researchTime;
	protected VIRUS_TYPE counterAct;
	protected Integer price;
	protected CURE_TYPE type;
	
	public Cure(CURE_TYPE type, Integer researchTime, VIRUS_TYPE counterAct, Integer price) {
		this.type = type;
		this.researchTime = researchTime;
		this.counterAct = counterAct;
		this.price = price;
	}
	
	public Integer getResearchTime() {
		return researchTime;
	}
	
	public VIRUS_TYPE getCounterActedVirus() {
		return counterAct;
	}
	
	public Integer getPrice() {
		return price;
	}
	
	public CURE_TYPE getType() {
		return type;
	}
}

