/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.data.dao.jpa.osgi;

import static org.ops4j.peaberry.Peaberry.service;

import org.gatherdata.data.core.spi.FlatFormDao;
import org.gatherdata.data.dao.jpa.internal.JpaFlatFormDao;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import static org.ops4j.peaberry.util.TypeLiterals.export;

public class GuiceBindingModule extends AbstractModule {

    public void configure() {
        // local binds
        bind(javax.persistence.spi.PersistenceProvider.class).to(org.eclipse.persistence.jpa.osgi.PersistenceProvider.class);
        
        // exports
        bind(export(FlatFormDao.class)).toProvider(service(JpaFlatFormDao.class).export());

    }

}
