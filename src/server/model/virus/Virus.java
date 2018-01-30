package server.model.virus;

public abstract class Virus {
	protected Integer researchTime;
	protected Integer decrementToPopulation;
	protected Integer price;
	
	public Virus(Integer researchTime, Integer decrementToPopulation, Integer price) {
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
}
