/*
 * See the file "LICENSE" for the full license governing this code.
 */
package com.codefupanda.genie.dao;

/**
 * List of database table keys ordered alphabetically.
 *    
 * @author Shashank
 */
public enum Key {
	CATEGORY ("category"),
	DESCRIPTION ("description"),
	END_DATE ("end_date"), 
	ID ("id"),
	NAME("name"),
	TITLE ("title"),
	USER_CREATED ("user_created"),
	WT_WORD ("wh_word");
	
	private String name;
	
	/**
	 * Constructor.
	 * @param name column name in DB
	 */
	Key(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
