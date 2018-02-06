package server.model.virus;

import server.model.virus.VirusFactory.VIRUS_TYPE;

public class PoxVirus extends Virus {
	
	public PoxVirus() {
		super(VIRUS_TYPE.POX,
				POX_VIRUS_INFO.RESEARCH_TIME.getVal(), 
				POX_VIRUS_INFO.POP_DECREMENT.getVal(), 
				POX_VIRUS_INFO.PRICE.getVal());
	}
	
	public enum POX_VIRUS_INFO {
		RESEARCH_TIME(120),
		POP_DECREMENT(375),
		PRICE(5000);
		
		private Integer val;
		
		private POX_VIRUS_INFO(Integer i) {
			val = i;
		}
		
		public Integer getVal() {
			return val;
		}
	}
}
