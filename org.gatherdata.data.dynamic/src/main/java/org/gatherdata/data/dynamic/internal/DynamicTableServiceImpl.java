/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.data.dynamic.internal;

import org.gatherdata.data.dynamic.DynamicTableDao;
import org.gatherdata.data.dynamic.model.DynamicTableSchema;
import org.gatherdata.data.dynamic.DynamicTableService;

import com.google.inject.Inject;

public class DynamicTableServiceImpl implements DynamicTableService {
	
	@Inject
	DynamicTableDao dao;

	public DynamicTableSchema get(long id) {
		return dao.get(id);
	}

	public DynamicTableSchema getSchema(String named, String inNamespace) {
		// TODO Auto-generated method stub
		return null;
	}

	public DynamicTableSchema save(DynamicTableSchema dt) {
		DynamicTableSchema savedEntity = null;
		dao.beginTransaction();
		savedEntity = dao.save(dt);
		dao.endTransaction();
		return savedEntity;
	}

}
