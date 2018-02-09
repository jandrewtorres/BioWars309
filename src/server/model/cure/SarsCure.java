package server.model.cure;

import server.model.cure.CureFactory.CURE_TYPE;
import server.model.virus.VirusFactory.VIRUS_TYPE;

public class SarsCure extends Cure{
	public SarsCure() {
		super(CURE_TYPE.SARSCURE,
				SARS_CURE_INFO.RESEARCH_TIME.getVal(), 
				SARS_CURE_INFO.COUNTERACT.getCounter(), 
				SARS_CURE_INFO.PRICE.getVal());
	}
	
	public enum SARS_CURE_INFO {
		RESEARCH_TIME(120),
		COUNTERACT(VIRUS_TYPE.SARS),
		PRICE(50000);
		
		private Integer val;
		private VIRUS_TYPE counterActedVirus;
		private SARS_CURE_INFO(Integer i) {
			val = i;
		}
		private SARS_CURE_INFO(VIRUS_TYPE v) {
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
