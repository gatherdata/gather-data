package org.gatherdata.data.core.model.impl;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gatherdata.commons.util.GatherDateTimeParser;
import org.joda.time.DateTime;

public class ValueRenderer {

    static final Pattern FLOAT_VALUE_PATTERN = Pattern.compile("\\D*(\\d*\\.\\d*).*");

    static final Pattern INT_VALUE_PATTERN = Pattern.compile("\\D*(\\d*).*");

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
            Matcher floatValue = FLOAT_VALUE_PATTERN.matcher(value);
            if (floatValue.matches()) {
                valueAsFloat = Float.parseFloat(floatValue.group(1));
                intoRendering.setValueAsFloat(valueAsFloat);
            }
        } catch (NumberFormatException nfe) {
            ;
        }

        Integer valueAsInt = 0;
        try {
            Matcher intValue = INT_VALUE_PATTERN.matcher(value);
            if (intValue.matches()) {
                valueAsInt = Integer.parseInt(intValue.group(1));
                intoRendering.setValueAsInt(valueAsInt);
                if (intoRendering.getValueAsFloat() == null) {
                    intoRendering.setValueAsFloat(valueAsInt.floatValue());
                }
            } else if (valueAsFloat != null) {
                intoRendering.setValueAsInt(valueAsFloat.intValue());
            }
        } catch (NumberFormatException nfe) {
            ;
        }

        intoRendering.setValueAsBoolean((Boolean.parseBoolean(value) || (valueAsFloat != 0)));
    }

    public static DateTime renderAsDateTime(String value) {
        DateTime renderedDateTime = null;

        try {
            renderedDateTime = GatherDateTimeParser.parseDateTime(value);
        } catch (Exception e) {
            ;
        }

        return renderedDateTime;
    }
}
