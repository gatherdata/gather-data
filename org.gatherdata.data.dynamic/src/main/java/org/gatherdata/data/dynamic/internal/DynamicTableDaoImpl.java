/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.data.dynamic.internal;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.spi.PersistenceProvider;

import org.gatherdata.data.dynamic.DynamicTableDao;
import org.gatherdata.data.dynamic.model.DynamicTableSchema;
import org.gatherdata.data.dynamic.model.DynamicTableSchema;

import com.google.inject.Inject;

public class DynamicTableDaoImpl implements DynamicTableDao {

    private final Logger log = Logger.getLogger(DynamicTableDao.class.getName());

    protected String persistenceUnitName = "gather-dynamic-table";

    private EntityManagerFactory emf;

    private EntityManager em;

    @Inject
    private PersistenceProvider persistenceProvider;

    private EntityTransaction currentTransaction;

    public void beginTransaction() {
        EntityTransaction newTransaction = getEntityManager().getTransaction();
        newTransaction.begin();
        this.currentTransaction = newTransaction;
    }
    
    public void rollbackTransaction() {
    	currentTransaction.rollback();
    }

    public void endTransaction() {
        currentTransaction.commit();
    }

    public EntityManager getEntityManager() {
        if (em == null) {
            em = getEntityManagerFactory().createEntityManager();
        }
        return this.em;
    }

    private EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            emf = persistenceProvider.createEntityManagerFactory(persistenceUnitName, null);
        }
        return emf;
    }

	public DynamicTableSchema get(long id) {
		DynamicTableSchema entity = getEntityManager().find(DynamicTableSchema.class, 
                id);

        if (entity == null) {
            String msg = DynamicTableSchema.class + "' object with id '" + id
                    + "' not found...";
            log.warning(msg);
            throw new EntityNotFoundException(msg);
        }

        return entity;
	}

	public DynamicTableSchema save(DynamicTableSchema dt) {
		DynamicTableSchema savedEntity = getEntityManager().merge(dt);
		
        return savedEntity;		
	}

	public DynamicTableSchema getSchema(String named, String inNamespace) {
		DynamicTableSchema foundEntity = null;
		
		return foundEntity;
	}

}
