/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.data.single.model.impl;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;

import org.apache.commons.lang.ObjectUtils;
import org.gatherdata.commons.model.UniqueEntity;
import org.gatherdata.commons.model.impl.MutableEntity;
import org.gatherdata.data.single.model.FlatForm;
import org.gatherdata.data.single.model.RenderedValue;
import org.joda.time.DateTime;

/**
 * Generic concrete implementation of the RenderedValue interface.
 */

public class MutableRenderedValue implements RenderedValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3989477987923437193L;

	private FlatForm form;
	private String path;
	private String tag;
	private DateTime valueAsDateTime;
	private Float valueAsFloat;
	private Integer valueAsInt;
	private String valueAsString;
	private Boolean valueAsBoolean;

	public MutableRenderedValue() {
	}

	public FlatForm getForm() {
		return this.form;
	}

	public String getPath() {
		return this.path;
	}

	public String getTag() {
		return this.tag;
	}

	public DateTime getValueAsDateTime() {
		return this.valueAsDateTime;
	}

	public Float getValueAsFloat() {
		return this.valueAsFloat;
	}

	public Integer getValueAsInt() {
		return this.valueAsInt;
	}

	public String getValueAsString() {
		return this.valueAsString;
	}

	public Boolean getValueAsBoolean() {
		return this.valueAsBoolean;
	}

	public void setForm(FlatForm form) {
		this.form = form;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setValueAsDateTime(DateTime valueAsDateTime) {
		this.valueAsDateTime = valueAsDateTime;
	}

	public void setValueAsFloat(Float valueAsFloat) {
		this.valueAsFloat = valueAsFloat;
	}

	public void setValueAsInt(Integer valueAsInt) {
		this.valueAsInt = valueAsInt;
	}

	public void setValueAsString(String valueAsString) {
		this.valueAsString = valueAsString;
	}

	public void setValueAsBoolean(Boolean valueAsBoolean) {
		this.valueAsBoolean = valueAsBoolean;
	}

	
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        result = prime * result + ((tag == null) ? 0 : tag.hashCode());
        result = prime * result + ((valueAsBoolean == null) ? 0 : valueAsBoolean.hashCode());
        result = prime * result + ((valueAsDateTime == null) ? 0 : valueAsDateTime.hashCode());
        result = prime * result + ((valueAsFloat == null) ? 0 : valueAsFloat.hashCode());
        result = prime * result + ((valueAsInt == null) ? 0 : valueAsInt.hashCode());
        result = prime * result + ((valueAsString == null) ? 0 : valueAsString.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MutableRenderedValue other = (MutableRenderedValue) obj;
        if (path == null) {
            if (other.path != null)
                return false;
        } else if (!path.equals(other.path))
            return false;
        if (tag == null) {
            if (other.tag != null)
                return false;
        } else if (!tag.equals(other.tag))
            return false;
        if (valueAsBoolean == null) {
            if (other.valueAsBoolean != null)
                return false;
        } else if (!valueAsBoolean.equals(other.valueAsBoolean))
            return false;
        if (valueAsDateTime == null) {
            if (other.valueAsDateTime != null)
                return false;
        } else if (!valueAsDateTime.equals(other.valueAsDateTime))
            return false;
        if (valueAsFloat == null) {
            if (other.valueAsFloat != null)
                return false;
        } else if (!valueAsFloat.equals(other.valueAsFloat))
            return false;
        if (valueAsInt == null) {
            if (other.valueAsInt != null)
                return false;
        } else if (!valueAsInt.equals(other.valueAsInt))
            return false;
        if (valueAsString == null) {
            if (other.valueAsString != null)
                return false;
        } else if (!valueAsString.equals(other.valueAsString))
            return false;
        return true;
    }

    public RenderedValue copy(RenderedValue template) {
		if (template != null) {
			setPath(template.getPath());
			setTag(template.getTag());
			setValueAsBoolean(template.getValueAsBoolean());
			setValueAsDateTime(template.getValueAsDateTime());
			setValueAsFloat(template.getValueAsFloat());
			setValueAsInt(template.getValueAsInt());
			setValueAsString(template.getValueAsString());
		}
		return this;
	}

	public RenderedValue update(RenderedValue template) {
		if (template != null) {
			String templatePath = template.getPath();
			if (templatePath != null) {
				setPath(templatePath);
			}
			String templateTag = template.getTag();
			if (templateTag != null) {
				setTag(templateTag);
			}
			Boolean templateValueAsBoolean = template.getValueAsBoolean();
			if (templateValueAsBoolean != null) {
				setValueAsBoolean(templateValueAsBoolean);
			}
			DateTime templateValueAsDateTime = template.getValueAsDateTime();
			if (templateValueAsDateTime != null) {
				setValueAsDateTime(templateValueAsDateTime);
			}
			Float templateValueAsFloat = template.getValueAsFloat();
			if (templateValueAsFloat != null) {
				setValueAsFloat(templateValueAsFloat);
			}
			Integer templateValueAsInt = template.getValueAsInt();
			if (templateValueAsInt != null) {
				setValueAsInt(templateValueAsInt);
			}
			String templateValueAsString = template.getValueAsString();
			if (templateValueAsString != null) {
				setValueAsString(templateValueAsString);
			}
		}
		return this;
	}

}
