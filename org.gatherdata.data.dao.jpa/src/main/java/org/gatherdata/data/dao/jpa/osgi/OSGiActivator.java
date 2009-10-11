package org.gatherdata.data.dao.jpa.osgi;

import java.util.Dictionary;
import java.util.Properties;

import org.gatherdata.data.core.spi.FlatFormDao;
import org.gatherdata.data.dao.jpa.internal.JpaFlatFormDao;
import org.ops4j.peaberry.Export;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Inject;

import static com.google.inject.Guice.createInjector;
import static org.ops4j.peaberry.Peaberry.osgiModule;

/**
 * Extension of the default OSGi bundle activator
 */
public final class OSGiActivator
    implements BundleActivator
{
    @Inject
    Export<FlatFormDao> dao;
    
    /**
     * Called whenever the OSGi framework starts our bundle
     */
    public void start( BundleContext bc )
        throws Exception
    {
        createInjector(osgiModule(bc), new GuiceBindingModule()).injectMembers(this);

    }

    /**
     * Called whenever the OSGi framework stops our bundle
     */
    public void stop( BundleContext bc )
        throws Exception
    {
    	;
    }
}

