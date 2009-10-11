package org.gatherdata.data.command.internal;

import java.util.Properties;

import org.gatherdata.data.core.spi.FlatFormService;
import org.osgi.framework.Constants;

import com.google.inject.AbstractModule;
import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.Attributes.properties;
import static org.ops4j.peaberry.util.TypeLiterals.export;
import static org.ops4j.peaberry.util.TypeLiterals.iterable;

public class GuiceBindingModule extends AbstractModule {

    @Override
    protected void configure() {
        // import all ArchiverService
        bind(FlatFormService.class).toProvider(service(FlatFormService.class).single());
        
        // export the CamelCommandImpl
        Properties ccAttrs = new Properties();
        ccAttrs.put(Constants.SERVICE_RANKING, new Long(100));
        bind(export(org.apache.felix.shell.Command.class))
            .toProvider(service(new FlatFormCommandImpl())
                .attributes(properties(ccAttrs))
                .export());

    }
    
}
