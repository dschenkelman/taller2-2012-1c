package styling;

import models.EntityType;

public class Styler {

	public static String getFillColor(EntityType type) throws Exception {
		switch (type) 
		{
			case Domain:
				return StyleConstants.DOMAIN_COLOR;
			case None:
				return StyleConstants.NONE_COLOR;
			case Programmed:
				return StyleConstants.PROGRAMMED_COLOR;
			case Thing:
				return StyleConstants.THING_COLOR;
			case Historic:
				return StyleConstants.HISTORIC_COLOR;
		}
		
		throw new Exception();
	}

	public static String getEdgeExitStyle(double exitX, double exitY) {
		return String.format("exitX=%s;exitY=%s", exitX, exitY);
	}

}
