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
