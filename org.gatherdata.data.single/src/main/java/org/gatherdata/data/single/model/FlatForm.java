/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.data.single.model;

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
