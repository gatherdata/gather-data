/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.data.core.internal;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.verify;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.gatherdata.commons.net.GatherUrnFactory;
import org.gatherdata.commons.spi.BaseStorageServiceTest;
import org.gatherdata.data.core.model.FlatForm;
import org.gatherdata.data.core.model.FlatFormMockFactory;
import org.gatherdata.data.core.model.RenderedValue;
import org.gatherdata.data.core.model.impl.MutableFlatForm;
import org.gatherdata.data.core.model.impl.MutableRenderedValue;
import org.gatherdata.data.core.spi.FlatFormDao;
import org.gatherdata.data.core.spi.FlatFormService;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit testing for the behavior of FlatFormStorageImpl.
 *
 */
public class FlatFormServiceImplTest extends BaseStorageServiceTest<FlatForm, FlatFormDao, FlatFormServiceImpl> {

    @Override
    protected FlatFormDao createMockDao() {
        return createMock(FlatFormDao.class);
    }

    @Override
    protected FlatFormServiceImpl createStorageServiceImpl() {
        return new FlatFormServiceImpl();
    }

    @Override
    protected void injectDaoIntoService(FlatFormDao dao, FlatFormServiceImpl service) {
        service.dao = dao;
    }

    @Override
    protected FlatForm createMockEntity() {
        return FlatFormMockFactory.createMockEntity();
    }

    @Test
    public void shouldRunUnitTestsInEclipse() {
        assertTrue(true);
    }

}
