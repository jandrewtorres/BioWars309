package server.model.virus;

public class PoxVirus extends Virus {
	
	public PoxVirus() {
		super(POX_VIRUS_INFO.RESEARCH_TIME.getVal(), 
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
