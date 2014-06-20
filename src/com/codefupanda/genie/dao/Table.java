/*
 * See the file "LICENSE" for the full license governing this code.
 */
package com.codefupanda.genie.dao;

/**
 * @author Shashank
 *
 */
public enum Table {
	CATEGORY ("category"),
	WISHES ("WISHES");
	
	private final String name;
	
	Table(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
