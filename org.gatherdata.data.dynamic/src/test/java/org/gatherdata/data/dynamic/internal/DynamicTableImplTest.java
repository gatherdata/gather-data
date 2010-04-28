/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.data.dynamic.internal;

import java.util.regex.Matcher;

import org.gatherdata.data.dynamic.model.DynamicTableSchema;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertTrue;

public class DynamicTableImplTest {
	
	@Test
	public void shouldFindWordGroupsIgnoringDashes() {
		Matcher wordMatcher = DynamicTableSchema.WORD_GROUP.matcher("this-is-a-test");
		assertTrue(wordMatcher.find());
		assertThat(wordMatcher.group(), is("this"));
		assertTrue(wordMatcher.find());
		assertThat(wordMatcher.group(), is("is"));
		assertTrue(wordMatcher.find());
		assertThat(wordMatcher.group(), is("a"));
		assertTrue(wordMatcher.find());
		assertThat(wordMatcher.group(), is("test"));
	}
	

	@Test
	public void shouldFindWordsGroupsIgnoringUnderscores() {
		Matcher wordMatcher = DynamicTableSchema.WORD_GROUP.matcher("another_test");
		assertTrue(wordMatcher.find());
		assertThat(wordMatcher.group(), is("another"));
		assertTrue(wordMatcher.find());
		assertThat(wordMatcher.group(), is("test"));
	}
	
	@Test
	public void shouldFindWordGroupsIncludingNumbers() {
		Matcher wordMatcher = DynamicTableSchema.WORD_GROUP.matcher("test-42");
		assertTrue(wordMatcher.find());
		assertThat(wordMatcher.group(), is("test"));
		assertTrue(wordMatcher.find());
		assertThat(wordMatcher.group(), is("42"));
	}
	
	@Test
	public void shouldGenerateClassNameFromNamespacePlusName() {
		DynamicTableSchema testTable = new DynamicTableSchema();
		
		testTable.setNamespace("org.gatherdata.data.dynamic.test");
		testTable.setName("sample-form-12");
		
		assertThat(testTable.getClassName(), is("org.gatherdata.data.dynamic.test.SampleForm12"));
	}
	
	@Test
	public void shouldGenerateTableNameFromName() {
		DynamicTableSchema testTable = new DynamicTableSchema();
		
		testTable.setName("sample-form-12");
		
		assertThat(testTable.getTableName(), is("SAMPLE_FORM_12"));
	}

}
