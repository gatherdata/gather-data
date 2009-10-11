package org.gatherdata.data.core.model;

import java.io.Serializable;
import java.net.URI;
import java.util.Calendar;
import java.util.List;

import org.gatherdata.commons.model.UniqueEntity;

/**
 * A FlatForm stores all form values without any hierarchy.
 */

public interface FlatForm extends UniqueEntity {

	public URI getNamespace();

	public List<? extends RenderedValue> getValues();

}
