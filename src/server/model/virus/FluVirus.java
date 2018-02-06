package server.model.virus;

import server.model.virus.VirusFactory.VIRUS_TYPE;

public class FluVirus extends Virus {
	
	public FluVirus() {
		super(VIRUS_TYPE.FLU,
				FLU_VIRUS_INFO.RESEARCH_TIME.getVal(), 
				FLU_VIRUS_INFO.POP_DECREMENT.getVal(), 
				FLU_VIRUS_INFO.PRICE.getVal());
	}
	
	public enum FLU_VIRUS_INFO {
		RESEARCH_TIME(60),
		POP_DECREMENT(30),
		PRICE(1000);
		
		private Integer val;
		
		private FLU_VIRUS_INFO(Integer i) {
			val = i;
		}
		
		public Integer getVal() {
			return val;
		}
	}
}
