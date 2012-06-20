package styling;

import models.AttributeType;
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
	
	public static String getAttributeConnectorStyle(AttributeType type, boolean isKey){
		if (type == AttributeType.calculated){
			return StyleConstants.CALCULATED_ATTRIBUTE_LINK_STYLE;
		}
		
		return StyleConstants.ATTRIBUTE_LINK_STYLE;
	}

}
