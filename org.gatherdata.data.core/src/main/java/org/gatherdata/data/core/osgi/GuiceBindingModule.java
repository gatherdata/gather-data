package org.gatherdata.data.core.osgi;

import static org.ops4j.peaberry.Peaberry.service;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;

import org.gatherdata.data.core.internal.FlatFormServiceImpl;
import org.gatherdata.data.core.spi.FlatFormDao;
import org.gatherdata.data.core.spi.FlatFormService;
import org.ops4j.peaberry.Export;

import static org.ops4j.peaberry.util.TypeLiterals.export;

/**
 * The GuiceBindingModule specifies bindings to provided and
 * consumed services for this bundle using Google Guice with
 * Peaberry extensions for OSGi.
 *
 */
public class GuiceBindingModule extends AbstractModule {

	
	@Override 
	protected void configure() {
		// imports
		bind(FlatFormDao.class).toProvider(service(FlatFormDao.class).single());
		
		// exports
		bind(export(FlatFormService.class)).toProvider(service(FlatFormServiceImpl.class).export());
		
	}
}
