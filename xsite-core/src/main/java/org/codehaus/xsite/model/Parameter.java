/*
 * Copyright (C) 2013 XSite Committers.
 * All rights reserved.
 *
 * Created on 01.05.2013 by Joerg Schaible
 */
package org.codehaus.xsite.model;

/**
 * A parameter for the web site.
 * 
 * @author J&ouml;rg Schaible
 */
public class Parameter {
	
	private final String name;
	private String value;
	
	public Parameter(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}