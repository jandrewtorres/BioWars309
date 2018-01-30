package server.model.virus;

public class VirusFactory {
	
	public Virus createVirus(VIRUS_TYPE type) {
		switch(type) {
			case COLD:
				return new ColdVirus();
			case FLU:
				return new FluVirus();
			case POX:
				return new PoxVirus();
			case SARS:
				return new SARSVirus();
			default:
				return null;
		}
	}
	
	public enum VIRUS_TYPE {
		COLD,
		FLU,
		POX,
		SARS;
	}
}
