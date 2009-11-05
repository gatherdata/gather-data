/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.data.core.model.impl;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gatherdata.commons.util.GatherDateTimeParser;
import org.joda.time.DateTime;

public class ValueRenderer {

    static final Pattern FLOAT_VALUE_PATTERN = Pattern.compile("\\D*(\\d*\\.\\d*).*");

    static final Pattern INT_VALUE_PATTERN = Pattern.compile("\\D*(\\d*).*");

    public static void render(String value, MutableRenderedValue intoRendering) {
        intoRendering.setValueAsString(value);

        DateTime valueAsDateTime = renderAsDateTime(value);
        if (valueAsDateTime != null) {
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
                valueAsInt = valueAsFloat.intValue();
                intoRendering.setValueAsInt(valueAsFloat.intValue());
            }
        } catch (NumberFormatException nfe) {
            ;
        }

        intoRendering.setValueAsBoolean((Boolean.parseBoolean(value) || (valueAsInt != 0)));
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
