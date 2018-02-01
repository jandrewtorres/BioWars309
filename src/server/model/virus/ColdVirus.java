package server.model.virus;

public class ColdVirus extends Virus {
	
	public ColdVirus() {
		super(COLD_VIRUS_INFO.RESEARCH_TIME.getVal(), 
				COLD_VIRUS_INFO.POP_DECREMENT.getVal(), 
				COLD_VIRUS_INFO.PRICE.getVal());
	}
	
	public enum COLD_VIRUS_INFO {
		RESEARCH_TIME(10),
		POP_DECREMENT(15),
		PRICE(500);
		
		private Integer val;
		
		private COLD_VIRUS_INFO(Integer i) {
			val = i;
		}
		
		public Integer getVal() {
			return val;
		}
	}
}
