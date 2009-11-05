/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.data.core.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import org.gatherdata.data.core.model.impl.MutableFlatForm;
import org.gatherdata.data.core.model.impl.MutableRenderedValue;
import org.gatherdata.data.core.model.impl.ValueRenderer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class FlatFormMockFactory {
    static int mockPlainTextCount = 0;
    static Random rnd = new Random(new DateTime().getMillis());
    static DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTime();

    public static FlatForm createMockEntity() {
        MutableFlatForm mockEntity = new MutableFlatForm();
        mockEntity.setDateCreated(new DateTime());
        URI mockNamespace = null;
        try {
            mockNamespace = new URI("http://gatherdata.org/mock/data");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mockEntity.setNamespace(mockNamespace);

        MutableRenderedValue mockStringValue = new MutableRenderedValue();
        mockStringValue.setForm(mockEntity);
        mockStringValue.setPath("/mock/string");
        mockStringValue.setTag("string");
        ValueRenderer.render("mock #" + mockPlainTextCount++, mockStringValue);
        mockEntity.add(mockStringValue);

        MutableRenderedValue mockFloatValue = new MutableRenderedValue();
        mockFloatValue.setForm(mockEntity);
        mockFloatValue.setPath("/mock/float");
        mockFloatValue.setTag("float");
        ValueRenderer.render(Float.toString(rnd.nextFloat()), mockFloatValue);
        mockEntity.add(mockFloatValue);

        MutableRenderedValue mockIntValue = new MutableRenderedValue();
        mockIntValue.setForm(mockEntity);
        mockIntValue.setPath("/mock/int");
        mockIntValue.setTag("int");
        ValueRenderer.render(Integer.toString(rnd.nextInt()), mockIntValue);
        mockEntity.add(mockIntValue);

        MutableRenderedValue mockDateValue = new MutableRenderedValue();
        mockDateValue.setForm(mockEntity);
        mockDateValue.setPath("/mock/date");
        mockDateValue.setTag("date");
        ValueRenderer.render(dateTimeFormatter.print(new DateTime()), mockDateValue);
        mockEntity.add(mockDateValue);

        mockEntity.selfIdentify();

        return mockEntity;
    }

}
