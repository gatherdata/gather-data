/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.data.dao.jpa.internal;

import org.hibernate.ejb.HibernatePersistence;

import com.google.inject.AbstractModule;

public class JpaTestingModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(javax.persistence.spi.PersistenceProvider.class).to(HibernatePersistence.class);
    }

}
