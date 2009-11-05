/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.data.command.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.felix.shell.Command;
import org.gatherdata.commons.net.CbidFactory;
import org.gatherdata.data.core.model.FlatForm;
import org.gatherdata.data.core.model.RenderedValue;
import org.gatherdata.data.core.model.impl.MutableFlatForm;
import org.gatherdata.data.core.model.impl.MutableRenderedValue;
import org.gatherdata.data.core.model.impl.ValueRenderer;
import org.gatherdata.data.core.spi.FlatFormService;
import org.gatherdata.data.core.transform.xml.XmlToFlatFormTransform;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.google.inject.Inject;

public class FlatFormCommandImpl implements Command {

    public static final String COMMAND_NAME = "flat";

    private final Pattern commandPattern = Pattern.compile("^(\\w+)\\s*(\\w+)\\s*(.*)");

    private final Pattern subCommandPattern = Pattern.compile("^(\\S+)\\s+(.*)");

    @Inject
    FlatFormService flatFormService;

    XmlToFlatFormTransform xmlTransformer = new XmlToFlatFormTransform();

    int mockPlainTextCount = 0;
    Random rnd = new Random(new DateTime().getMillis());
    DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTime();

    public void execute(String argString, PrintStream out, PrintStream err) {
        Matcher argMatcher = commandPattern.matcher(argString);
        if (argMatcher.matches()) {
            String subCommand = argMatcher.group(2);
            String subArguments = argMatcher.group(3);

            if (flatFormService == null) {
                err.println("FlatFormService not available");
                return;
            }

            if ("help".equals(subCommand)) {
                out.println("subcommands: list, values, mock, load");
                out.println("\tlist - show saved archives");
                out.println("\tvalues <uid> - show the rendered values from a form");
                out.println("\tmock - generate and save mock data");
                out.println("\tload - load and transform an xml file");
            } else if ("mock".equals(subCommand)) {
                FlatForm mockEntity = createMockEntity();

                flatFormService.save(mockEntity);
            } else if ("list".equals(subCommand)) {
                for (FlatForm savedEntity : flatFormService.getAll()) {
                    out.println(savedEntity);
                }
            } else if ("values".equals(subCommand)) {
                URI requestedUid = null;
                try {
                    requestedUid = new URI(subArguments);
                    FlatForm requestedEntity = flatFormService.get(requestedUid);
                    if (requestedEntity != null) {
                        List<? extends RenderedValue> values = requestedEntity.getValues();
                        if (values != null) {
                            for (RenderedValue value : values) {
                                System.out.println("\t" + value);
                            }
                        } else {
                            System.out.println("No metadata for " + requestedUid);
                        }
                    } else {
                        err.println("Requested form not found.");
                    }
                } catch (URISyntaxException e) {
                    err.println("Bad form uid: " + subArguments);
                }
            } else if ("load".equals(subCommand)) {
                String filename = subArguments;
                File fileToLoad = new File(filename);
                if (fileToLoad.exists()) {
                    String fileContents = readFile(fileToLoad);
                    FlatForm transformedForm = xmlTransformer.transform(fileContents);
                    flatFormService.save(transformedForm);
                } else {
                    err.println(filename + "not found. (CWD=" + System.getProperty("user.dir"));
                }
            } else {
                err.println("Don't know how to respond to " + subCommand);
            }
        } else {
            err.println("Sorry, can't parse:" + argString);
        }
    }

    private String readFile(File fileToLoad) {
        StringBuilder contents = new StringBuilder();

        try {
            BufferedReader input = new BufferedReader(new FileReader(fileToLoad));
            try {
                String line = null;
                while ((line = input.readLine()) != null) {
                    contents.append(line);
                    contents.append(System.getProperty("line.separator"));
                }
            } finally {
                input.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return contents.toString();

    }

    private FlatForm createMockEntity() {
        MutableFlatForm mockEntity = new MutableFlatForm();
        mockEntity.setDateCreated(new DateTime());
        URI mockNamespace = null;
        try {
            mockNamespace = new URI("http://gatherdata.org/mock/data");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mockEntity.setNamespace(mockNamespace);

        MutableRenderedValue mockStringValue = new MutableRenderedValue();
        mockStringValue.setForm(mockEntity);
        mockStringValue.setPath("/mock/string");
        mockStringValue.setTag("string");
        ValueRenderer.render("mock #" + mockPlainTextCount++, mockStringValue);
        mockEntity.add(mockStringValue);

        MutableRenderedValue mockFloatValue = new MutableRenderedValue();
        mockFloatValue.setForm(mockEntity);
        mockFloatValue.setPath("/mock/float");
        mockFloatValue.setTag("float");
        ValueRenderer.render(Float.toString(rnd.nextFloat()), mockFloatValue);
        mockEntity.add(mockFloatValue);

        MutableRenderedValue mockIntValue = new MutableRenderedValue();
        mockIntValue.setForm(mockEntity);
        mockIntValue.setPath("/mock/int");
        mockIntValue.setTag("int");
        ValueRenderer.render(Integer.toString(rnd.nextInt()), mockIntValue);
        mockEntity.add(mockIntValue);

        MutableRenderedValue mockDateValue = new MutableRenderedValue();
        mockDateValue.setForm(mockEntity);
        mockDateValue.setPath("/mock/date");
        mockDateValue.setTag("date");
        ValueRenderer.render(dateTimeFormatter.print(new DateTime()), mockDateValue);
        mockEntity.add(mockDateValue);

        MutableRenderedValue mockBooleanValue = new MutableRenderedValue();
        mockBooleanValue.setForm(mockEntity);
        mockBooleanValue.setPath("/mock/boolean");
        mockBooleanValue.setTag("boolean");
        ValueRenderer.render(Boolean.toString(rnd.nextBoolean()), mockBooleanValue);
        mockEntity.add(mockBooleanValue);

        mockEntity.selfIdentify();

        return mockEntity;
    }

    public String getName() {
        return COMMAND_NAME;
    }

    public String getShortDescription() {
        return "interacts with the FlatFormService";
    }

    public String getUsage() {
        return "flat <sub-command>";
    }

}
