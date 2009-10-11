package org.gatherdata.data.core.spi;

import org.gatherdata.commons.spi.BaseStorageDaoTest;
import org.gatherdata.data.core.model.FlatForm;
import org.gatherdata.data.core.model.FlatFormMockFactory;

public abstract class BaseFlatFormDaoTest extends BaseStorageDaoTest<FlatForm, FlatFormDao> {

    @Override
    protected FlatForm createMockEntity() {
        return FlatFormMockFactory.createMockEntity();
    }
}
