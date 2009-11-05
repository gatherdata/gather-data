/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.forms.xform.schema;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.Hashtable;

import org.javarosa.core.model.Constants;
import org.javarosa.core.model.FormDef;
import org.javarosa.core.model.IFormElement;
import org.javarosa.core.model.QuestionDef;
import org.javarosa.core.model.instance.DataModelTree;
import org.javarosa.core.model.instance.TreeElement;
import org.javarosa.core.model.instance.TreeReference;
import org.kxml2.io.KXmlSerializer;
import org.kxml2.kdom.Document;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

public class XFormInstanceSchema {
    private Hashtable choiceTypeMapping;

    private Document schemaDoc = null;
    
    public XFormInstanceSchema(FormDef extractedFrom) {
        schemaDoc = generateInstanceSchema(extractedFrom);
    }
    
    public Document getDocument() {
        return schemaDoc;
    }
    
    public String toText() {
        KXmlSerializer serializer = new KXmlSerializer();
        StringWriter printer = new StringWriter();
        try {
            serializer.setOutput(printer);
            schemaDoc.write(serializer);
            serializer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return printer.toString();
    }
    
    private Document generateInstanceSchema (FormDef f) {
        init();
        
        Element schema = new Element();
        schema.setName("schema");
        schema.setNamespace("http://www.w3.org/2001/XMLSchema");
        schema.setPrefix("", "http://www.w3.org/2001/XMLSchema");
        schema.setAttribute(null, "targetNamespace", f.getDataModel().schema);
        schema.setAttribute(null, "elementFormDefault", "qualified");

        processSelectChoices(schema, f, f.getDataModel());
        schema.addChild(Node.ELEMENT, schemizeInstance(f.getDataModel().getRoot()));
                
        Document schemaXML = new Document();
        schemaXML.addChild(Node.ELEMENT, schema);
        
        return schemaXML;
    }
    
    private void init () {
        choiceTypeMapping = new Hashtable();
    }
    
    private Element schemizeInstance (TreeElement node) {
        String name = node.getName();
        boolean terminal = node.isLeaf();
        boolean repeatable = node.repeatable;
        
        if (repeatable && node.getMult() != TreeReference.INDEX_TEMPLATE) {
            return null;
        }
        
        Element e = new Element();
        e.setName("element");
        e.setAttribute(null, "name", name);
        e.setAttribute(null, "minOccurs", "0"); //technically only needed if node has a 'relevant' attribute bound to it, but no easy way to tell
        if (repeatable) {
            e.setAttribute(null, "maxOccurs", "unbounded");
        }
        
        if (!terminal) {
            Element ct = new Element();
            ct.setName("complexType");
            e.addChild(Node.ELEMENT, ct);
            
            Element seq = new Element();
            seq.setName("sequence");
            ct.addChild(Node.ELEMENT, seq);
            
            for (int i = 0; i < node.getNumChildren(); i++) {
                Element child = schemizeInstance((TreeElement)node.getChildren().elementAt(i));
                if (child != null) {
                    seq.addChild(Node.ELEMENT, child);
                }
            }
        } else {
            String type;
            
            switch (node.dataType) {
            case Constants.DATATYPE_NULL:
            case Constants.DATATYPE_TEXT:
                type = "string";
                break;
            case Constants.DATATYPE_INTEGER: type = "integer"; break;
            case Constants.DATATYPE_DECIMAL: type = "decimal"; break;
            case Constants.DATATYPE_DATE: type = "date"; break;
            case Constants.DATATYPE_DATE_TIME: type = "dateTime"; break;
            case Constants.DATATYPE_TIME: type = "time"; break;
            case Constants.DATATYPE_CHOICE:
            case Constants.DATATYPE_CHOICE_LIST:
                type = (String)choiceTypeMapping.get(node);
                if (type == null) {
                    System.err.println("can't find choices for select-type question [" + node.getName() + "]");
                }
                break;
            default:
                type = null;
                System.err.println("unrecognized type [" + node.dataType + ";" + node.getName() + "]");
                break;
            }
            
            if (type != null) {
                e.setAttribute(null, "type", type);
            }
        }
        
        return e;
    }
    
    private void processSelectChoices (Element e, IFormElement fe, DataModelTree model) {
        if (fe instanceof QuestionDef) {
            QuestionDef q = (QuestionDef)fe;
            int controlType = q.getControlType();
            TreeReference ref = (TreeReference)q.getBind().getReference();
            
            if (controlType == Constants.CONTROL_SELECT_ONE || controlType == Constants.CONTROL_SELECT_MULTI) {
                String choiceTypeName = getChoiceTypeName(ref);
                writeChoices(e, choiceTypeName, q.getSelectItemIDs().elements());
                
                if (controlType == Constants.CONTROL_SELECT_MULTI) {
                    writeListType(e, choiceTypeName);
                }
                
                choiceTypeMapping.put(model.getTemplatePath(ref),
                        (controlType == Constants.CONTROL_SELECT_MULTI ? "list." : "") + choiceTypeName);
            }
        } else {
            for (int i = 0; i < fe.getChildren().size(); i++) {
                processSelectChoices(e, fe.getChild(i), model);
            }
        }
    }
    
    private String getChoiceTypeName (TreeReference ref) {
        return ref.toString(false).replace('/', '_');
    }
    
    private void writeChoices (Element e, String typeName, Enumeration choices) {
        Element st = new Element();
        st.setName("simpleType");
        st.setAttribute(null, "name", typeName);
        e.addChild(Node.ELEMENT, st);
        
        Element restr = new Element();
        restr.setName("restriction");
        restr.setAttribute(null, "base", "string");
        st.addChild(Node.ELEMENT, restr);
        
        while (choices.hasMoreElements()) {
            String value = (String)choices.nextElement();
            
            Element choice = new Element();
            choice.setName("enumeration");
            choice.setAttribute(null, "value", value);
            restr.addChild(Node.ELEMENT, choice);
        }
    }
    
    private void writeListType (Element e, String typeName) {
        Element st = new Element();
        st.setName("simpleType");
        st.setAttribute(null, "name", "list." + typeName);
        e.addChild(Node.ELEMENT, st);
        
        Element list = new Element();
        list.setName("list");
        list.setAttribute(null, "itemType", typeName);
        st.addChild(Node.ELEMENT, list);
    }

}
