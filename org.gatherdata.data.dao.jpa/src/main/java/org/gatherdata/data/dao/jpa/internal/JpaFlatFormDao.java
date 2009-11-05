/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
/**
 * 
 */
package org.gatherdata.data.dao.jpa.internal;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.spi.PersistenceProvider;

import org.gatherdata.data.core.model.FlatForm;
import org.gatherdata.data.core.model.RenderedValue;
import org.gatherdata.data.core.spi.FlatFormDao;
import org.gatherdata.data.dao.jpa.model.JpaFlatForm;

import com.google.inject.Inject;

/**
 * JPA based implementation of FlatFormDao.
 *
 */
public class JpaFlatFormDao implements FlatFormDao {

    private final Logger log = Logger.getLogger(JpaFlatFormDao.class.getName());


    protected String persistenceUnitName = "flatform-hsql-server";

    private EntityManagerFactory emf;

    private EntityManager em;

    @Inject
    private PersistenceProvider persistenceProvider;

    private EntityTransaction currentTransaction;

    public JpaFlatFormDao() {
        ;
    }

    public void beginTransaction() {
        EntityTransaction newTransaction = getEntityManager().getTransaction();
        newTransaction.begin();
        this.currentTransaction = newTransaction;
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
    public boolean exists(URI entityUid) {
        Query q = getEntityManager()
                .createQuery("SELECT COUNT(entity) FROM JpaFlatForm entity WHERE entity.uidAsString = :entityUid");
        q.setParameter("entityUid", entityUid.toASCIIString());

        Long result = (Long) q.getSingleResult();
        
        System.out.println("exists: query [" + q + "] returned " + result);
        return (result != 0);
    }

    public FlatForm get(URI entityUid) {
        JpaFlatForm entity = getEntityManager().find(JpaFlatForm.class, 
                entityUid.toASCIIString());

        if (entity == null) {
            String msg = "Uh oh, '" + JpaFlatForm.class + "' object with id '" + entityUid
                    + "' not found...";
            log.warning(msg);
            throw new EntityNotFoundException(msg);
        }

        return entity;
    }

    @SuppressWarnings("unchecked")
    public List<FlatForm> getAll() {
        return getEntityManager().createQuery("select entity from JpaFlatForm entity").getResultList();
    }

    public int getCount() {
        Query q = em.createQuery("SELECT COUNT(entity) FROM JpaFlatForm entity");
        Number result = (Number) q.getSingleResult();
        return result.intValue();
    }
    
    public void remove(URI archiveIdentifiedBy) {
        beginTransaction();
        FlatForm entityToRemove = get(archiveIdentifiedBy);
        getEntityManager().remove(entityToRemove);
        endTransaction();
    }

    public FlatForm save(FlatForm entityToSave) {
        FlatForm savedEntity = null;
        if (entityToSave != null) {
            JpaFlatForm saveableEntity = new JpaFlatForm().copy(entityToSave);
            saveableEntity.selfIdentify();

            savedEntity = getEntityManager().merge(saveableEntity);

        }
        return savedEntity;
    }

}
