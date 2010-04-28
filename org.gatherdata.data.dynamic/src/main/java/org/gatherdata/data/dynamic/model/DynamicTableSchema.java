/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.data.dynamic.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "DYNAMIC_TABLE")
public class DynamicTableSchema {
	
    public final static Pattern WORD_GROUP = Pattern.compile("([\\w&&[^_]]+)");

	@Id
	@Column(name = "ID")
	private long id;
	
	@Basic
	@Column(name = "name")
	private String name;

	@Basic
	@Column(name = "namespace")
	private String namespace;

	@OneToMany (cascade = CascadeType.ALL)
	private Set<DynamicFieldSchema> fields = new HashSet<DynamicFieldSchema>();
	
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getClassName() {
		StringBuffer camelCaseName = new StringBuffer();
		Matcher wordMatcher = WORD_GROUP.matcher(name);
		while (wordMatcher.find()) {
			String word = wordMatcher.group();
			camelCaseName.append(Character.toTitleCase(word.charAt(0)));
			if (word.length() > 1) {
				camelCaseName.append(word.substring(1));
			}
		}
		
		return namespace + "." + camelCaseName.toString();
	}

	public String getTableName() {
		StringBuffer underscoredName = new StringBuffer();
		Matcher wordMatcher = WORD_GROUP.matcher(name.toUpperCase());
		boolean firstGroup = true;
		while (wordMatcher.find()) {
			String word = wordMatcher.group();
			if (!firstGroup) underscoredName.append('_');
			underscoredName.append(word);
			firstGroup = false;
		}
		
		return underscoredName.toString();
	}

	public Set<DynamicFieldSchema> getFields() {
		return fields;
	}

}
