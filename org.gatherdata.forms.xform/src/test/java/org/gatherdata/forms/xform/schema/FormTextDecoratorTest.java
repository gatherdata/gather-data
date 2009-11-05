/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.forms.xform.schema;

import static org.junit.Assert.*;

import org.javarosa.core.model.FormDef;
import org.javarosa.xform.util.XFormUtils;
import org.junit.Test;

public class FormTextDecoratorTest {

    @Test
    public void shouldProducePlainText() {
        FormDef f = XFormUtils.getFormFromInputStream(getClass().getResourceAsStream("/xforms/idsr-case_draft.xhtml"));
        FormTextDecorator decorator = new FormTextDecorator(f);
        System.out.println(decorator.toText());
        assertTrue(true);
    }
}
