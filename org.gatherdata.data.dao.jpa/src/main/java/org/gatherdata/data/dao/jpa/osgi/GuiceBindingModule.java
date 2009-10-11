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
