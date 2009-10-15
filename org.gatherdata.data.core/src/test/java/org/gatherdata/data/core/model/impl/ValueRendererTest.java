package org.gatherdata.data.core.model.impl;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertTrue;

public class ValueRendererTest {

    @Test
    public void shouldRenderPlainWords() {
        MutableRenderedValue intoRendering = new MutableRenderedValue();
        final String EXPECTED_VALUE = "foo";
        
        ValueRenderer.render(EXPECTED_VALUE, intoRendering);
        
        assertThat(intoRendering.getValueAsString(), is(EXPECTED_VALUE));
    }

    @Test
    public void shouldRenderFloat() {
        MutableRenderedValue intoRendering = new MutableRenderedValue();
        final Float EXPECTED_VALUE = (Float) 4.2f;
        
        ValueRenderer.render(EXPECTED_VALUE.toString(), intoRendering);
        
        assertThat(intoRendering.getValueAsFloat(), is(EXPECTED_VALUE));
    }

    @Test
    public void shouldRenderInteger() {
        MutableRenderedValue intoRendering = new MutableRenderedValue();
        final Integer EXPECTED_VALUE = (Integer) 42;
        
        ValueRenderer.render(EXPECTED_VALUE.toString(), intoRendering);
        
        assertThat(intoRendering.getValueAsInt(), is(EXPECTED_VALUE));
    }

    @Test
    public void shouldTruncateFloatToRenderAsInteger() {
        MutableRenderedValue intoRendering = new MutableRenderedValue();
        final Float INITIAL_FLOAT_VALUE = (Float) 41.8f;
        final Integer EXPECTED_VALUE = (Integer) 41;
        
        ValueRenderer.render(INITIAL_FLOAT_VALUE.toString(), intoRendering);
        
        assertThat(intoRendering.getValueAsInt(), is(EXPECTED_VALUE));
    }

    @Test
    public void shouldRenderIntegerValueAsFloat() {
        MutableRenderedValue intoRendering = new MutableRenderedValue();
        final Integer INITIAL_VALUE = (Integer) 42;
        final Float EXPECTED_VALUE = (Float) 42.0f;
        
        ValueRenderer.render(INITIAL_VALUE.toString(), intoRendering);
        
        assertThat(intoRendering.getValueAsFloat(), is(EXPECTED_VALUE));
    }
    
    @Test
    public void shouldRenderIntegerValueBuriedInText() {
        MutableRenderedValue intoRendering = new MutableRenderedValue();
        final String INITIAL_VALUE = "Answer42Life";
        final Integer EXPECTED_VALUE = (Integer) 42;
        
        ValueRenderer.render(INITIAL_VALUE.toString(), intoRendering);
        
        assertThat(intoRendering.getValueAsInt(), is(EXPECTED_VALUE));
    }
    
    @Test
    public void shouldRenderTrueForNonZeroIntValue() {
        MutableRenderedValue intoRendering = new MutableRenderedValue();
        final Integer INITIAL_VALUE = (Integer) 42;
        final Boolean EXPECTED_VALUE = (Boolean) true;
        
        ValueRenderer.render(INITIAL_VALUE.toString(), intoRendering);
        
        assertThat(intoRendering.getValueAsBoolean(), is(EXPECTED_VALUE));
    }

    @Test
    public void shouldRenderFalseForZeroIntValue() {
        MutableRenderedValue intoRendering = new MutableRenderedValue();
        final Integer INITIAL_VALUE = (Integer) 0;
        final Boolean EXPECTED_VALUE = (Boolean) false;
        
        ValueRenderer.render(INITIAL_VALUE.toString(), intoRendering);
        
        assertThat(intoRendering.getValueAsBoolean(), is(EXPECTED_VALUE));
    }
}
