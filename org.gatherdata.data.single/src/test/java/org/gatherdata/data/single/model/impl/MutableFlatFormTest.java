/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.data.single.model.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertTrue;

import org.gatherdata.data.single.model.FlatForm;
import org.gatherdata.data.single.model.FlatFormMockFactory;
import org.junit.Test;

public class MutableFlatFormTest {

    @Test
    public void shouldEqualCopyOfOriginal() {
        FlatForm original = FlatFormMockFactory.createMockEntity();
        MutableFlatForm copy = new MutableFlatForm();
        
        assertThat(copy, is(not(original)));
        
        copy.copy(original);
        
        assertThat(copy, is(original));
        
        assertTrue(FlatFormSupport.deepEquals(copy, original));
    }
}
