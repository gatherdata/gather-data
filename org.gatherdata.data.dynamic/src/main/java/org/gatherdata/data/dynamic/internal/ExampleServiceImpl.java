/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.data.dynamic.internal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.eclipse.persistence.config.PersistenceUnitProperties.*;

import javax.persistence.*;
import javax.persistence.metamodel.EntityType;

import org.eclipse.persistence.dynamic.*;
import org.eclipse.persistence.jpa.dynamic.JPADynamicTypeBuilder;
import org.eclipse.persistence.jpa.osgi.PersistenceProvider;
import org.eclipse.persistence.logging.SessionLog;
import org.eclipse.persistence.sequencing.TableSequence;
import org.eclipse.persistence.sessions.DatabaseLogin;
import org.eclipse.persistence.sessions.DatabaseSession;
import org.eclipse.persistence.sessions.Project;
import org.eclipse.persistence.sessions.Session;
import org.eclipse.persistence.sessions.UnitOfWork;
import org.eclipse.persistence.sessions.factories.SessionManager;
import org.gatherdata.data.dynamic.model.DynamicFieldSchema;
import org.gatherdata.data.dynamic.model.DynamicTableSchema;
import org.gatherdata.data.dynamic.DynamicTableService;
import org.ops4j.peaberry.Export;

/**
 * Internal implementation of our example OSGi service
 */
public final class ExampleServiceImpl {

	public static final String PACKAGE_PREFIX = ExampleServiceImpl.class
			.getPackage().getName();

	public static DatabaseSession createSession(ClassLoader bundleClassLoader) {
		return (DatabaseSession) SessionManager.getManager().getSession(
				"dynamo", bundleClassLoader);
	}

	private DynamicTableService tableService;

	public void tryItOut(ClassLoader bundleClassLoader,
			DynamicTableService tableService) {
		try {
			this.tableService = tableService;
			long schemaId = createDynamicSchema();
			DynamicTableSchema dt = loadDynamicSchema(schemaId);

			DatabaseSession session = null;
			DynamicHelper dynamicHelper = null;

			session = createSession(bundleClassLoader);
			dynamicHelper = new DynamicHelper(session);
			DynamicClassLoader dcl = new DynamicClassLoader(bundleClassLoader);
			Class<?> empClass = dcl.createDynamicClass(dt.getClassName());
			DynamicTypeBuilder typeBuilder = new DynamicTypeBuilder(empClass,
					null, dt.getTableName());
			typeBuilder.setPrimaryKeyFields("ID");
			typeBuilder.configureSequencing(new TableSequence(), dt
					.getTableName()
					+ "_ID", "ID");
			typeBuilder.addDirectMapping("id", long.class, "ID");

			for (DynamicFieldSchema df : dt.getFields()) {
				//typeBuilder.addDirectMapping(df.getFieldName(), getFieldType(), getColumnName());
			}
			
			dynamicHelper.addTypes(true, false, typeBuilder.getType());
			DynamicType empType = new DynamicHelper(session)
					.getType("Employee");

			// Populate table with a single Employee
			DynamicEntity e1 = empType.newDynamicEntity();
			e1.set("firstName", "Mike");
			e1.set("lastName", "Norman");
			UnitOfWork uow = session.acquireUnitOfWork();
			uow.registerNewObject(e1);
			uow.commit();
			session.getIdentityMapAccessor().initializeAllIdentityMaps();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	private long createDynamicSchema() {
		DynamicTableSchema dt = new DynamicTableSchema();
		dt.setName("employee");
		dt.setNamespace(PACKAGE_PREFIX);
		
		Set<DynamicFieldSchema> dfs = dt.getFields();
		
		{
//
//			typeBuilder.addDirectMapping("firstName", String.class, "F_NAME");
//			typeBuilder.addDirectMapping("lastName", String.class, "L_NAME");
		}
		dt = tableService.save(dt);
		return dt.getId();
	}

	private DynamicTableSchema loadDynamicSchema(long schemaId) {
		return tableService.get(schemaId);
	}
}
