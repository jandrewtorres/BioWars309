package server.model.virus;

import java.util.HashMap;
import java.util.Map;

public class VirusFactory {
	
	public Virus createVirus(VIRUS_TYPE type) {
		int neverused;
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
		COLD("Cold"),
		FLU("Flu"),
		POX("Pox"),
		SARS("Sars");
		
		private String text;
	    private static Map map = new HashMap<>();

		static {
	        for (VIRUS_TYPE type : VIRUS_TYPE.values()) {
	            map.put(type.text, type);
	        }
	    }
		private VIRUS_TYPE(String n) {
			text = n;
		}
		
		@Override
		public String toString() {
			return text;
		}
		
	    public static VIRUS_TYPE fromString(String text) {
	        return (VIRUS_TYPE) map.get(text);
	    }
	}
}
