package server.model.cure;

import server.model.cure.CureFactory.CURE_TYPE;
import server.model.virus.VirusFactory.VIRUS_TYPE;

public class ColdCure extends Cure{
	
	public ColdCure() {
		super(CURE_TYPE.COLDCURE,
				COLD_CURE_INFO.RESEARCH_TIME.getVal(), 
				COLD_CURE_INFO.COUNTERACT.getCounter(), 
				COLD_CURE_INFO.PRICE.getVal());
	}
	
	public enum COLD_CURE_INFO {
		RESEARCH_TIME(10),
		COUNTERACT(VIRUS_TYPE.COLD),
		PRICE(500);
		
		private Integer val;
		private VIRUS_TYPE counterActedVirus;
		
		private COLD_CURE_INFO(Integer i) {
			val = i;
		}
		
		private COLD_CURE_INFO(VIRUS_TYPE v) {
			counterActedVirus = v;
		}
		
		public Integer getVal() {
			return val;
		}
		
		public VIRUS_TYPE getCounter() {
			return counterActedVirus;
		}
	}
}
