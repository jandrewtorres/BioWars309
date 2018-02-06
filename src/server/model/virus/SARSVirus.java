package server.model.virus;

import server.model.virus.VirusFactory.VIRUS_TYPE;

public class SARSVirus extends Virus {

	public SARSVirus() {
		super(VIRUS_TYPE.SARS,
				SARS_VIRUS_INFO.RESEARCH_TIME.getVal(), 
				SARS_VIRUS_INFO.POP_DECREMENT.getVal(), 
				SARS_VIRUS_INFO.PRICE.getVal());
	}
	
	public enum SARS_VIRUS_INFO {
		RESEARCH_TIME(320),
		POP_DECREMENT(1000),
		PRICE(50000);
		
		private Integer val;
		
		private SARS_VIRUS_INFO(Integer i) {
			val = i;
		}
		
		public Integer getVal() {
			return val;
		}
	}
}
