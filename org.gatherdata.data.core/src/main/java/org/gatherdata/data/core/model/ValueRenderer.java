package org.gatherdata.data.core.model;

import java.util.logging.Logger;

import org.gatherdata.commons.util.GatherDateTimeParser;
import org.gatherdata.data.core.model.impl.MutableRenderedValue;
import org.joda.time.DateTime;

public class ValueRenderer {

    private static Logger log = Logger.getLogger(ValueRenderer.class.getName());
	public static void render(String value, MutableRenderedValue intoRendering) {
		intoRendering.setValueAsString(value);
		
		log.info("render(" + value + ")");
		DateTime valueAsDateTime = renderAsDateTime(value);
		if (valueAsDateTime != null) {
		    log.info("parsed datetime: " + valueAsDateTime);
			intoRendering.setValueAsDateTime(valueAsDateTime);
		} 
		
		Float valueAsFloat = 0.0f;
		try {
			valueAsFloat = Float.parseFloat(value);
			intoRendering.setValueAsFloat(valueAsFloat);
		} catch (NumberFormatException nfe) {;}

        Integer valueAsInt = 0;
        try {
            valueAsInt = Integer.parseInt(value);
			intoRendering.setValueAsInt(valueAsInt);
		} catch (NumberFormatException nfe) {;}
		
		intoRendering.setValueAsBoolean((Boolean.parseBoolean(value) || (valueAsFloat != 0)));
	}

	public static DateTime renderAsDateTime(String value) {
		DateTime renderedDateTime = null;
		
		try {
		    renderedDateTime = GatherDateTimeParser.parseDateTime(value);
		} catch (Exception e) { ; }
		
		return renderedDateTime;
	}
}
