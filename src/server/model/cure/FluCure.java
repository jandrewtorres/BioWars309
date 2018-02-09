package server.model.cure;

import server.model.cure.CureFactory.CURE_TYPE;
import server.model.virus.VirusFactory.VIRUS_TYPE;

public class FluCure extends Cure{
	public FluCure() {
		super(CURE_TYPE.FLUCURE,
				FLU_CURE_INFO.RESEARCH_TIME.getVal(), 
				FLU_CURE_INFO.COUNTERACT.getCounter(), 
				FLU_CURE_INFO.PRICE.getVal());
	}
	
	public enum FLU_CURE_INFO {
		RESEARCH_TIME(30),
		COUNTERACT(VIRUS_TYPE.FLU),
		PRICE(1000);
		
		private Integer val;
		private VIRUS_TYPE counterActedVirus;
		private FLU_CURE_INFO(Integer i) {
			val = i;
		}
		private FLU_CURE_INFO(VIRUS_TYPE v) {
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
