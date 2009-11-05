/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.forms.dao.el.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.persistence.tools.schemaframework.TableCreator;
import org.gatherdata.core.net.CbidFactory;
import org.gatherdata.core.net.GatherUrnFactory;
import org.gatherdata.forms.core.model.FlatForm;
import org.gatherdata.forms.core.model.GenericFlatForm;
import org.gatherdata.forms.core.model.GenericRenderedValue;
import org.gatherdata.forms.core.model.RenderedValue;
import org.gatherdata.forms.core.model.ValueRenderer;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit tests for the FlatFormsDaoImpl, which relies on the FlatFormsSessionInit 
 * to be working properly. 
 * 
 */
public class FlatFormsDaoImplTest extends AbstractDbTestCase {

	private FlatFormsSessionInit sessionInit;

	private static URI MOCK_NAMESPACE;
	private static Integer renderedValueCounter = new Integer(1);
	
	static {
		try {
			MOCK_NAMESPACE = new URI("http://mock.testing.namespace");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	@Before
	public void reinitDatabaseWithCleanTables() {
		sessionInit = new FlatFormsSessionInit();
		sessionInit.reinitializeDatabase();
	}
	
	public FlatFormsDaoImplTest() {
		super(null, true);
		serverProps = "database.1=mem:gather;dbname.1=gather";
	}

	@Test
    @Ignore
	public void shouldSaveFlatForm() throws URISyntaxException {
		ElFlatFormsDao dao = new ElFlatFormsDao();
		dao.setSession(sessionInit.getSession());
		
		int initialFlatFormsCount = countRecordsOf(FlatForm.class);
		
		GenericRenderedValue aRenderedValue = createMockRenderedValue();
		GenericRenderedValue anotherRenderedValue = createMockRenderedValue();

		GenericFlatForm formToSave = new GenericFlatForm();
		formToSave.addToValues(aRenderedValue);
		formToSave.addToValues(anotherRenderedValue);
		formToSave.setNamespace(MOCK_NAMESPACE);
		
		FlatForm savedForm = dao.save(formToSave);
		assertNotNull(savedForm);

		int resultingFlatFormsCount = countRecordsOf(FlatForm.class);
		
		assertEquals(initialFlatFormsCount + 1, resultingFlatFormsCount);
	}

	@Test
    @Ignore
	public void shouldRetrieveSavedFlatForm() throws URISyntaxException {
		ElFlatFormsDao dao = new ElFlatFormsDao();
		dao.setSession(sessionInit.getSession());
		
		GenericRenderedValue aRenderedValue = createMockRenderedValue();
		GenericRenderedValue anotherRenderedValue = createMockRenderedValue();

		GenericFlatForm formToSave = new GenericFlatForm();
		formToSave.addToValues(aRenderedValue);
		formToSave.addToValues(anotherRenderedValue);
		formToSave.setNamespace(MOCK_NAMESPACE);
		
		FlatForm savedForm = dao.save(formToSave);
		assertNotNull(savedForm);

		FlatForm retrievedForm = dao.retrieveForm(formToSave.getCbid());
		
		assertEquals(savedForm, retrievedForm);
	}

	private GenericRenderedValue createMockRenderedValue() {
		GenericRenderedValue aRenderedValue = new GenericRenderedValue();
		aRenderedValue.setNamespace(MOCK_NAMESPACE);
		aRenderedValue.setPath("mock/attributes");
		aRenderedValue.setTag("heighInInches");
		ValueRenderer.render(renderedValueCounter.toString(), aRenderedValue);
		return aRenderedValue;
	}

	private int countRecordsOf(Class<?> entityClass) {
		return sessionInit.getSession().readAllObjects(entityClass).size();
	}
}
