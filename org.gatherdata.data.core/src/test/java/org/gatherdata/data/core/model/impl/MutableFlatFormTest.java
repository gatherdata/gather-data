package org.gatherdata.data.core.model.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertTrue;

import org.gatherdata.data.core.model.FlatForm;
import org.gatherdata.data.core.model.FlatFormMockFactory;
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
