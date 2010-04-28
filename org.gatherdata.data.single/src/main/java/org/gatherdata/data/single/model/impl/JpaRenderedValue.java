/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.data.single.model.impl;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.gatherdata.data.single.model.FlatForm;
import org.gatherdata.data.single.model.RenderedValue;
import org.joda.time.DateTime;

/**
 * JPA specialized subclass of RenderedValue.
 */
@Entity
@Table(name = "RENDERED_VALUE")
public class JpaRenderedValue implements RenderedValue {

    /**
     * 
     */
    private static final long serialVersionUID = 9152408469704792328L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dbid;

    @ManyToOne(optional = false)
    private JpaFlatForm form;

    @Column(name = "DATE_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dateCreated;

    @Transient
    private DateTime lazyDateCreated;

    @Basic
    private String path;

    @Basic
    private String tag;

    @Basic
    @Column(name = "AS_BOOLEAN")
    private Boolean valueAsBoolean;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "AS_DATETIME")
    private Calendar valueAsDateTimeCalendar;

    @Transient
    private DateTime lazyValueAsDateTime;

    @Basic
    @Column(name = "AS_FLOAT")
    private Float valueAsFloat;

    @Basic
    @Column(name = "AS_INT")
    private Integer valueAsInt;

    @Basic
    @Column(name = "AS_STRING")
    private String valueAsString;

    public JpaRenderedValue() {
        ;
    }

    public int getDbid() {
        return this.dbid;
    }

    public void setDbid(int dbid) {
        this.dbid = dbid;
    }

    public DateTime getDateCreated() {
        if (lazyDateCreated == null) {
            if (dateCreated != null) {
                lazyDateCreated = new DateTime(dateCreated.getTimeInMillis());
            }
        }
        return this.lazyDateCreated;
    }

    public FlatForm getForm() {
        return this.form;
    }

    public String getPath() {
        return this.path;
    }

    public String getTag() {
        return this.tag;
    }

    public Boolean getValueAsBoolean() {
        return this.valueAsBoolean;
    }

    public DateTime getValueAsDateTime() {
        if (lazyValueAsDateTime == null) {
            if (valueAsDateTimeCalendar != null) {
                lazyValueAsDateTime = new DateTime(valueAsDateTimeCalendar.getTimeInMillis());
            }
        }
        return this.lazyValueAsDateTime;
    }

    public Float getValueAsFloat() {
        return this.valueAsFloat;
    }

    public Integer getValueAsInt() {
        return this.valueAsInt;
    }

    public String getValueAsString() {
        return this.valueAsString;
    }

    public void setForm(JpaFlatForm form) {
        this.form = form;
    }

    public void setDateCreated(DateTime dateCreated) {
        this.lazyDateCreated = dateCreated;
        if (dateCreated != null) {
            if (this.dateCreated == null) {
                this.dateCreated = new GregorianCalendar();
            }
            this.dateCreated.setTimeInMillis(dateCreated.getMillis());
        } else {
            this.dateCreated = null;
        }
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setValueAsBoolean(Boolean valueAsBoolean) {
        this.valueAsBoolean = valueAsBoolean;
    }

    public void setValueAsDateTime(DateTime valueAsDateTime) {
        this.lazyValueAsDateTime = valueAsDateTime;
        if (valueAsDateTime != null) {
            if (this.valueAsDateTimeCalendar == null) {
                this.valueAsDateTimeCalendar = new GregorianCalendar();
            }
            this.valueAsDateTimeCalendar.setTimeInMillis(valueAsDateTime.getMillis());
        } else {
            this.valueAsDateTimeCalendar = null;
        }
    }

    public void setValueAsFloat(Float valueAsFloat) {
        this.valueAsFloat = valueAsFloat;
    }

    public void setValueAsInt(Integer valueAsInt) {
        this.valueAsInt = valueAsInt;
    }

    public void setValueAsString(String valueAsString) {
        this.valueAsString = valueAsString;
    }

    public JpaRenderedValue copy(RenderedValue template) {
        if (template != null) {
            setPath(template.getPath());
            setTag(template.getTag());
            setValueAsBoolean(template.getValueAsBoolean());
            setValueAsDateTime(template.getValueAsDateTime());
            setValueAsFloat(template.getValueAsFloat());
            setValueAsInt(template.getValueAsInt());
            setValueAsString(template.getValueAsString());
        }
        return this;
    }

    @Override
    public String toString() {
        return "RenderedValue [path=" + path + ", tag=" + tag + ", valueAsString=" + valueAsString
                + ", valueAsDateTime=" + ((valueAsDateTimeCalendar != null) ? getValueAsDateTime().toString() : "null")
                + ", valueAsBoolean=" + valueAsBoolean
                + ", valueAsFloat=" + valueAsFloat
                + ", valueAsInt=" + valueAsInt + "]";
    }
    
    

}
