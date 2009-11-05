/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.forms.dao.el.internal;

import java.io.Serializable;
import java.net.URI;
import java.util.GregorianCalendar;
import java.util.List;

import org.eclipse.persistence.exceptions.DatabaseException;
import org.eclipse.persistence.expressions.Expression;
import org.eclipse.persistence.expressions.ExpressionBuilder;
import org.eclipse.persistence.sessions.DatabaseSession;
import org.eclipse.persistence.sessions.Session;
import org.gatherdata.core.net.CbidFactory;
import org.gatherdata.forms.core.model.FlatForm;
import org.gatherdata.forms.core.model.FlatFormUriFactory;
import org.gatherdata.forms.core.model.RenderedValue;
import org.gatherdata.forms.core.spi.FlatFormDao;
import org.gatherdata.forms.dao.el.model.ElFlatForm;

public class ElFlatFormsDao implements FlatFormDao {
	
	private Session elSession;
	
	public void setSession(Session sessionToUse) {
		this.elSession = sessionToUse;
	}
	
	/* (non-Javadoc)
	 * @see org.gatherdata.forms.dao.el.internal.FlatFormDao#save(org.gatherdata.forms.core.model.FlatForm)
	 */
	public FlatForm save(FlatForm formToSave) {
	    ElFlatForm saveableForm = null;
	    if (formToSave instanceof ElFlatForm) {
	        saveableForm = (ElFlatForm)formToSave;
	    } else {
	        saveableForm = new ElFlatForm(formToSave);
	    }
		DatabaseSession dbSession = createDbSession();
		dbSession.login();
		FlatForm savedForm = null;
		try {
		    saveableForm.setDateCreated((GregorianCalendar) GregorianCalendar.getInstance());
			if (saveableForm.getCbid() == null) {
			    saveableForm.setCbid(FlatFormUriFactory.createUriFor(formToSave));
			}
			savedForm = (FlatForm) dbSession.writeObject(formToSave);
		} catch (DatabaseException dbe) {
			dbe.printStackTrace();
		}
		dbSession.logout();
		return savedForm;
	}

	/* (non-Javadoc)
	 * @see org.gatherdata.forms.dao.el.internal.FlatFormDao#getForm(java.net.URI)
	 */
	public FlatForm retrieveForm(URI cbidOfForm) {
		DatabaseSession dbSession = createDbSession();
		dbSession.login();
		ExpressionBuilder expBuilder = new ExpressionBuilder();
		Expression formWithMatchingCbid = expBuilder.get("cbid").equal(cbidOfForm);
		FlatForm retrievedForm = (FlatForm)dbSession.readObject(FlatForm.class, formWithMatchingCbid);
		dbSession.logout();
		return retrievedForm;
	}
	
	private DatabaseSession createDbSession() {
		return elSession.getProject().createDatabaseSession();
	}

    public FlatForm adapt(Serializable contentToAdapt) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean envelopeExists(URI uidOfEnvelope) {
        // TODO Auto-generated method stub
        return false;
    }

    public List<URI> getAllContentUids() {
        // TODO Auto-generated method stub
        return null;
    }

    public List<? extends FlatForm> getAllContents() {
        // TODO Auto-generated method stub
        return null;
    }

    public FlatForm getContent(URI uidOfEnvelope) {
        // TODO Auto-generated method stub
        return null;
    }

    public void removeContent(URI uidOfEnvelope) {
        // TODO Auto-generated method stub
        
    }

    public Serializable saveAdapted(Serializable proxiedContentToSave) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean contentExists(URI uidOfEnvelope) {
        // TODO Auto-generated method stub
        return false;
    }
}
