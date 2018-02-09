package server.model.cure;

import java.util.HashMap;
import java.util.Map;

public class CureFactory {

	public Cure createCure(CURE_TYPE type) {
		switch(type) {
			case COLDCURE:
				return new ColdCure();
			case FLUCURE:
				return new FluCure();
			case POXCURE:
				return new PoxCure();
			case SARSCURE:
				return new SarsCure();
			default:
				return null;
			}
		}
		
		public enum CURE_TYPE {
			COLDCURE("ColdCure"),
			FLUCURE("FluCure"),
			POXCURE("PoxCure"),
			SARSCURE("SarsCure");
			
			private String text;
		    private static Map map = new HashMap<>();

			static {
		        for (CURE_TYPE type : CURE_TYPE.values()) {
		            map.put(type.text, type);
		        }
		    }
			private CURE_TYPE(String n) {
				text = n;
			}
			
			@Override
			public String toString() {
				return text;
			}
			
		    public static CURE_TYPE fromString(String text) {
		        return (CURE_TYPE) map.get(text);
		    }
		}
	
}
