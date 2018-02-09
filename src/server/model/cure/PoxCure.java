package server.model.cure;

import server.model.cure.CureFactory.CURE_TYPE;
import server.model.virus.VirusFactory.VIRUS_TYPE;

public class PoxCure extends Cure{
	public PoxCure() {
		super(CURE_TYPE.POXCURE,
				POX_CURE_INFO.RESEARCH_TIME.getVal(), 
				POX_CURE_INFO.COUNTERACT.getCounter(), 
				POX_CURE_INFO.PRICE.getVal()); 
	}
	
	public enum POX_CURE_INFO {
		RESEARCH_TIME(60),
		COUNTERACT(VIRUS_TYPE.POX),
		PRICE(5000);
		
		private Integer val;
		private VIRUS_TYPE counterActedVirus;
		private POX_CURE_INFO(Integer i) {
			val = i;
		}
		private POX_CURE_INFO(VIRUS_TYPE v) {
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
