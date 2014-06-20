/*
 * See the file "LICENSE" for the full license governing this code.
 */
package com.codefupanda.genie.dao;

import java.util.List;
import java.util.Map;

import com.codefupanda.genie.entity.Category;
import com.codefupanda.genie.entity.Wish;

/**
 * @author Shashank
 *
 */
public interface WishDao extends GenericDao<Wish>{

	/* All methods from Generic DAO */
	
	/**
	 * A category wise list of all the wishes.
	 * 
	 * @return wishes mapped to category
	 */
	Map<Category, List<Wish>> getCategoryWiseWishes();
}
