/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.data.dao.jpa.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static org.gatherdata.commons.junit.ContainsAll.containsAll;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

import java.net.URI;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.spi.PersistenceProvider;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.gatherdata.data.core.model.FlatForm;
import org.gatherdata.data.core.model.RenderedValue;
import org.gatherdata.data.core.spi.BaseFlatFormDaoTest;
import org.gatherdata.data.core.spi.FlatFormDao;
import org.gatherdata.data.dao.jpa.model.JpaFlatForm;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class JpaFlatFormDaoTest extends BaseFlatFormDaoTest {

    int mockPlainTextCount = 0;
    
    @Inject
    private PersistenceProvider persistenceProvider;

    private EntityTransaction tx;

    @Override
    protected FlatFormDao createStorageDaoImpl() {
        JpaFlatFormDao dao = new JpaFlatFormDao();
        dao.persistenceUnitName = "flatform-test";
        
        // guice up the instance
        Injector injector = Guice.createInjector(new JpaTestingModule());
        injector.injectMembers(dao);
        injector.injectMembers(this);
        
        return dao;
    }


    @Override
    protected void beginTransaction() {
        EntityManager em = ((JpaFlatFormDao) dao).getEntityManager();
        tx = em.getTransaction();
        tx.begin();
    }

    @Override
    protected void endTransaction() {
        tx.commit();
    }
    
    @Override
    protected void rollbackTransaction() {
    	tx.rollback();
    }
    
    /**
     * Must have at least one test here to convince Eclipse that
     * there are tests to run.
     */
    @Test
    public void shouldRunParentTestsInEclipse() {
        assertTrue(true);
    }
    
    /**
     * Sanity-check for the test. 
     */
    @Test
    public void shouldHavePersistenceProviderInjected() {
        assertThat(persistenceProvider, notNullValue());
    }
        
    @Test 
    public void shouldProduceUniqueIdsForMockEntities() {
    	final int EXPECTED_NUMBER_OF_ENTITIES = 10000;
    	Set<URI> uniqueURI = new HashSet<URI>();
    	Set<Integer> uniqueHashcodes = new HashSet<Integer>();
    	Set<FlatForm> uniqueEntities = new HashSet<FlatForm>();
    	for (int i=0; i<EXPECTED_NUMBER_OF_ENTITIES; i++) {
    		FlatForm mocked = createMockEntity();
    		assertTrue("duplicate URI #" + (i+1) + " " + mocked, uniqueURI.add(mocked.getUid()));
    		assertTrue("duplicate hashcode #" + (i+1) + " " + mocked, uniqueHashcodes.add(mocked.hashCode()));
    		assertTrue("duplicate entity #" + (i+1) + " " + mocked, uniqueEntities.add(mocked));
    	}
    	assertThat(uniqueURI.size(), equalTo(EXPECTED_NUMBER_OF_ENTITIES));
    }
    
    @Test
    public void shouldGetAllSavedEntities() {
        final int EXPECTED_NUMBER_OF_ENTITIES = new Random().nextInt(100);
        List<FlatForm> entitiesToSave = new ArrayList<FlatForm>();
        
        beginTransaction();
        FlatForm previousForm = null;
        FlatForm previousMock = null;
        for (int i=0; i< EXPECTED_NUMBER_OF_ENTITIES; i++) {
        	FlatForm entityToSave = createMockEntity();
            entitiesToSave.add(entityToSave);
            FlatForm savedEntity = dao.save(entityToSave);
            
            int daoCount = dao.getCount();
            if (i<daoCount) {
            	previousForm = savedEntity;
            	previousMock = entityToSave;
            } else {
            	System.err.println("Duplicate at #" + (i+1));
            	printEntity("mocked", entityToSave);
            	printEntity("previous mocked", previousMock);
            	printEntity("saved", savedEntity);
            	printEntity("previous saved", previousForm);
            	break;
            }
        }
        endTransaction();
        
        assertThat(dao.getCount(), equalTo(EXPECTED_NUMBER_OF_ENTITIES));
        
        beginTransaction();
        Iterable<FlatForm> allEntitiesList = (Iterable<FlatForm>) dao.getAll();
        assertThat(allEntitiesList, containsAll(entitiesToSave));
        endTransaction();
    }

	private void printEntity(String title, FlatForm entity) {
		System.out.println("Entity \"" + title + "\"");
		System.out.println(ObjectUtils.toString(entity.getUid()) + "; " +
				ObjectUtils.toString(entity.getNamespace()) + "; " +
				ObjectUtils.toString(entity.getDateCreated()));
		for (RenderedValue rv : entity.getValues()) {
			System.out.println("\t" + "; " +
					rv.getPath() + "; " +
					rv.getTag() + "; " +
					rv.getValueAsString());
		}
	}

    
}
