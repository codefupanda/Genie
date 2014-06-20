/*
 * See the file "LICENSE" for the full license governing this code.
 */
package com.codefupanda.genie.dao;

import java.util.List;

/**
 * 
 * @author Shashank
 */
public interface GenericDao<T> {
	
	/**
	 * 
	 * @param wish
	 */
	public void add(T object);
	
	/**
	 * 
	 * @param id
	 */
	public T get(int id);
	
	/**
	 * 
	 */
	public List<T> getAll();
	
	/**
	 * 
	 * @param id
	 */
	public void update(T object);
	
	/**
	 * @param id
	 */
	public void delete(int id); 
}
