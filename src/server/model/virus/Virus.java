package server.model.virus;

import server.model.virus.VirusFactory.VIRUS_TYPE;

public abstract class Virus {
	protected Integer researchTime;
	protected Integer decrementToPopulation;
	protected Integer price;
	protected VIRUS_TYPE type;
	
	public Virus(VIRUS_TYPE type, Integer researchTime, Integer decrementToPopulation, Integer price) {
		this.type = type;
		this.researchTime = researchTime;
		this.decrementToPopulation = decrementToPopulation;
		this.price = price;
	}
	
	public Integer getResearchTime() {
		return researchTime;
	}
	
	public Integer getDecrementToPopulation() {
		return decrementToPopulation;
	}
	
	public Integer getPrice() {
		return price;
	}
	
	public VIRUS_TYPE getType() {
		return type;
	}
}
