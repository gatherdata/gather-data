/**
 * 
 */
package org.gatherdata.data.core.internal;

import java.io.Serializable;
import java.net.URI;
import java.util.GregorianCalendar;
import java.util.List;

import org.gatherdata.data.core.model.FlatForm;
import org.gatherdata.data.core.model.impl.MutableFlatForm;
import org.gatherdata.data.core.spi.FlatFormDao;
import org.gatherdata.data.core.spi.FlatFormService;

import com.google.inject.Inject;

/**
 * Standard implementation of the FlatFormStorage, uses any available
 * FlatFormDao implementations injected by Guice.
 *
 */
public class FlatFormServiceImpl implements FlatFormService {

	FlatFormDao flatFormDao;

	@Inject
	FlatFormDao dao;

	public boolean exists(URI uid) {
	    return dao.exists(uid);
	}

	public FlatForm get(URI uid) {
		FlatForm foundEntity = null;
	    try {
    	    dao.beginTransaction();
    	    foundEntity = dao.get(uid);
	    } finally {
	        dao.endTransaction();
	    }
	    return foundEntity;
	    
	}

	public Iterable<FlatForm> getAll() {
	    dao.beginTransaction();
	    Iterable<FlatForm> allArchives = (Iterable<FlatForm>) dao.getAll();
	    dao.endTransaction();
		return allArchives;
	}

	public void remove(URI uid) {
		dao.remove(uid);
	}

	public FlatForm save(FlatForm instance) {
	    dao.beginTransaction();
		FlatForm savedInstance = dao.save(instance);
		dao.endTransaction();
		return savedInstance;
	}

	public void update(FlatForm instance) {
        URI uidToUpdate = instance.getUid();
        if (uidToUpdate != null) {
        	FlatForm currentEntity = get(uidToUpdate);
            MutableFlatForm transferEntity = new MutableFlatForm();
            transferEntity.copy(currentEntity);
            transferEntity.update(instance);
            dao.save(transferEntity);
        }		
	}


}
