/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.data.single.spi;

import org.gatherdata.commons.spi.BaseStorageDaoTest;
import org.gatherdata.data.single.model.FlatForm;
import org.gatherdata.data.single.model.FlatFormMockFactory;

public abstract class BaseFlatFormDaoTest extends BaseStorageDaoTest<FlatForm, FlatFormDao> {

    @Override
    protected FlatForm createMockEntity() {
        return FlatFormMockFactory.createMockEntity();
    }
}
