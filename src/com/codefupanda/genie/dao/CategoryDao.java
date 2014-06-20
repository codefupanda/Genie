/*
 * See the file "LICENSE" for the full license governing this code.
 */
package com.codefupanda.genie.dao;

import java.util.List;

import com.codefupanda.genie.entity.Category;

/**
 * 
 * @author Shashank
 */
public interface CategoryDao extends GenericDao<Category> {
	
	/** 
	 * Get all category names.
	 */
	public List<String> getAllNames();
}
