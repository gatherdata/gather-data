package org.gatherdata.data.command.internal;

import java.util.Dictionary;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.felix.shell.Command;
import org.ops4j.peaberry.Export;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import static org.ops4j.peaberry.Peaberry.osgiModule;
import static com.google.inject.Guice.createInjector;

import com.google.inject.Inject;


/**
 * Extension of the default OSGi bundle activator
 */
public final class OSGiActivator
    implements BundleActivator
{
    private static final Logger log = Logger.getLogger(OSGiActivator.class.getName());
    
    @Inject
    Export<Command> archiveCommand;
    
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
    }
}

