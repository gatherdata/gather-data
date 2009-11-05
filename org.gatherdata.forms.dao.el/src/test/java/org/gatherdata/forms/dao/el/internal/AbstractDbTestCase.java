/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.forms.dao.el.internal;

import static org.junit.Assert.*;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hsqldb.Server;
import org.junit.After;
import org.junit.Before;

public abstract class AbstractDbTestCase {
	
	// If not using the "test" database, set this in 
	// the testcase sub-class constructor. 
    String  serverProps;

    //  change the url to reflect your preferred db location and name
    String  url;
    String  user     = "sa";
    String  password = "";
    Server  server;
    boolean isNetwork = true;

    public AbstractDbTestCase(String connectionUrl, boolean network) {

        this.isNetwork = network;
        this.url       = connectionUrl;
    }

	@Before
	public void setUp() throws Exception {
        if (isNetwork) {
            if (url == null) {
                url = "jdbc:hsqldb:mem:memdbid";
            }

            server = new Server();
            
            server.putPropertiesFromString(serverProps);

            server.setDatabaseName(0, "test");
            server.setDatabasePath(0, "mem:test;sql.enforce_strict_size=true");
            server.setLogWriter(new PrintWriter(System.out));
            server.setErrWriter(new PrintWriter(System.err));
            server.start();
        } else {
            if (url == null) {
                url = "jdbc:hsqldb:file:test;sql.enforce_strict_size=true";
            }
        }

        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(this + ".setUp() error: " + e.getMessage());
        }

	}

	@After
	public void tearDown() throws Exception {
        if (isNetwork) {
            server.stop();

            server = null;
        }
	}
	
	/** 
	 * Creates and returns a new Connection to the in-memory HSQLDB database.
	 * 
	 * @return
	 * @throws SQLException
	 */
	Connection newConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

}
