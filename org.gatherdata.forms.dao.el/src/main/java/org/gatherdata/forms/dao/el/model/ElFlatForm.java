package org.gatherdata.forms.dao.el.model;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.gatherdata.forms.core.model.FlatForm;
import org.gatherdata.forms.core.model.RenderedValue;

/**
 * EclipseLink specialized subclass of the FlatForm.
 * 
 */

public class ElFlatForm implements FlatForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8313084518428651473L;

	private int id;

    private URI cbid;

    private Calendar dateCreated;

    private URI namespace;

    private List<ElRenderedValue> elValues;

	public ElFlatForm(FlatForm formToSave) {
	    
    }

    public URI getCbid() {
        return this.cbid;
    }
    
    public void setCbid(URI cbid) {
        this.cbid = cbid;
    }

    public Calendar getDateCreated() {
        return this.dateCreated;
    }

    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    public URI getNamespace() {
        return this.namespace;
    }
    
    public void setNamespace(URI namespace) {
        this.namespace = namespace;
    }

    public List<? extends RenderedValue> getValues() {
        return this.elValues;
    }

}
