/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.data.core.model;

import java.net.URI;

import org.gatherdata.commons.model.UniqueEntity;
import org.joda.time.DateTime;

/**
 * A RenderedValue presents multiple possible renderings of a value.
 */

public interface RenderedValue {

	public FlatForm getForm();

	public String getPath();

	public String getTag();
	
	public DateTime getValueAsDateTime();

	public Float getValueAsFloat();

	public Integer getValueAsInt();

	public String getValueAsString();
    
    public Boolean getValueAsBoolean();

}
