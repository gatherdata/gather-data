/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.data.core.model.impl;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.gatherdata.commons.model.impl.MutableEntity;
import org.gatherdata.commons.net.CbidFactory;
import org.gatherdata.data.core.model.FlatForm;
import org.gatherdata.data.core.model.RenderedValue;
import org.joda.time.DateTime;

/**
 * A FlatForm stores all form values without any hierarchy.
 */

public class MutableFlatForm extends MutableEntity implements FlatForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8313084518428651473L;

	private URI namespace;
	private List<RenderedValue> values;

	public MutableFlatForm() {
		this.values = new ArrayList<RenderedValue>();
	}

	public void addToValues(MutableRenderedValue aRenderedValue) {
		this.values.add(aRenderedValue);
		aRenderedValue.setForm(this);
	}

	public URI getNamespace() {
		return this.namespace;
	}

	public List<? extends RenderedValue> getValues() {
		return this.values;
	}

	public void removeFromValues(MutableRenderedValue aRenderedValue) {
		this.values.remove(aRenderedValue);
		aRenderedValue.setForm(null);
	}

	public void setNamespace(URI namespace) {
		this.namespace = namespace;
	}

	public void add(RenderedValue value) {
		this.values.add(value);
	}

    public FlatForm copy(FlatForm template) {
        if (template != null) {
            super.copy(template);
            setNamespace(template.getNamespace());
            for (RenderedValue templateValue : template.getValues()) {
            	add(new MutableRenderedValue().copy(templateValue));
            }
        }
        return this;
    }

    public FlatForm update(FlatForm template) {
        if (template != null) {
            super.update(template);
            
        }
        return this;
    }
	
	@Override
    public URI selfIdentify() {
        if (getDateCreated() == null) {
            setDateCreated(new DateTime());
        }
        if (this.getUid() == null) {
        	StringBuffer formContent = new StringBuffer();
        	formContent.append(FlatForm.class.getSimpleName());
        	formContent.append(getDateCreated());
        	for (RenderedValue rv : getValues()) {
        		formContent.append(rv.toString());
        	}
            URI selfId = CbidFactory.createCbid(formContent.toString());
            setUid(selfId);
        }
        return uid;
	}
	
    @Override
    public String toString() {
        return "FlatForm [uid=" + getUid() + ", dateCreated=" + getDateCreated()
                + ", namespace=" + getNamespace() + "]";
    }

}
