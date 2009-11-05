/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.data.core.transform.xml;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

import org.gatherdata.data.core.model.FlatForm;
import org.gatherdata.data.core.model.impl.MutableFlatForm;
import org.gatherdata.data.core.model.impl.MutableRenderedValue;
import org.gatherdata.data.core.model.impl.ValueRenderer;
import org.gatherdata.data.core.model.RenderedValue;

/**
 * Transforms XML content into a FlatForm.
 * 
 */
public class XmlToFlatFormTransform extends DefaultHandler {
	
	private MutableFlatForm currentForm = null;
	private StringBuffer textBuffer;
	private Stack<String> xpath = new Stack<String>();

	public FlatForm transform(String xml) {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		try {
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(new ByteArrayInputStream(xml
					.getBytes("UTF-8")), this);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		currentForm.selfIdentify();
		return currentForm;
	}

	public void startDocument() throws SAXException {
		xpath.clear();
		currentForm = new MutableFlatForm();
	}

	public void endDocument() throws SAXException {
		
	}

	public void startElement(String namespaceURI, String sName, // simple name
			String qName, // qualified name
			Attributes attrs) throws SAXException {
		if (currentForm.getNamespace() == null) {
			try {
                currentForm.setNamespace(new URI(namespaceURI));
            } catch (URISyntaxException e) {
                e.printStackTrace();
                currentForm.setNamespace(null);
            }
		}
		
		xpath.push(sName);
		if (attrs != null) {
			for (int i = 0; i < attrs.getLength(); i++) {
				String aName = attrs.getLocalName(i); // Attr name
				if ("".equals(aName))
					aName = attrs.getQName(i);
				renderValue(namespaceURI, aName, attrs.getValue(i));
			}
		}                                                   
	}

	private void renderValue(String namespace, String tag, String valueAsString) {
		MutableRenderedValue aRenderedValue = new MutableRenderedValue();
		aRenderedValue.setPath(getPathAsString());
		aRenderedValue.setTag(tag);
		ValueRenderer.render(valueAsString, aRenderedValue);
		
		currentForm.addToValues(aRenderedValue);
	}

	public void endElement(String namespaceURI, String sName, // simple name
			String qName // qualified name
	) throws SAXException {

		xpath.pop();
		
		String valueAsString = getText();
		if (!"".equals(valueAsString)) {
			renderValue(namespaceURI, sName, valueAsString);
		}
	}

	public void characters(char buf[], int offset, int len) throws SAXException {
		String s = new String(buf, offset, len);
		if (textBuffer == null) {
			textBuffer = new StringBuffer(s);
		} else {
			textBuffer.append(s);
		}
	}

	private String getText() throws SAXException {
		if (textBuffer == null)
			return "";
		String s = "" + textBuffer;
		textBuffer = null;
		return s.trim();
	}
	
	private String getPathAsString() {
		StringBuffer pathAsString = new StringBuffer();
		Iterator<String> it = xpath.iterator();
		while (it.hasNext()) {
			String s = it.next();
			pathAsString.append(s);
			if (it.hasNext()) {
				pathAsString.append('/');
			}
		}
		return pathAsString.toString();
	}
	
}
