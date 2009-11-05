/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.data.core.osgi;

import static com.google.inject.Guice.createInjector;
import static org.ops4j.peaberry.Peaberry.osgiModule;

import org.gatherdata.data.core.spi.FlatFormDao;
import org.gatherdata.data.core.spi.FlatFormService;
import org.ops4j.peaberry.Export;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Inject;

/**
 * The OSGi activator for the Gather Archiver Storage bundle.
 * 
 **/
public class OSGiActivator implements BundleActivator {

	@Inject
	FlatFormDao dao;
	
	@Inject
	Export<FlatFormService> storage;

	/**
	 * Implements BundleActivator.start().
	 * 
	 * Adds itself as a ServiceListener to the OSGi context and registers a
	 * Service.
	 * 
	 * @param bc the OSGi framework context for the bundle.
	 **/
	public void start(BundleContext bc) {

		createInjector(osgiModule(bc), new GuiceBindingModule()).injectMembers(
				this);
	}

	/**
	 * Implements BundleActivator.stop(). Prints a message and removes itself
	 * from the bundle context as a service listener.
	 * 
	 * @param context
	 *            the framework context for the bundle.
	 **/
	public void stop(BundleContext context) {
		;
	}

}
