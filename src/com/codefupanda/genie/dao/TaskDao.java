/*
 * See the file "LICENSE" for the full license governing this code.
 */
package com.codefupanda.genie.dao;

import java.util.List;

import com.codefupanda.genie.entity.Task;

/**
 * @author Shashank
 * 
 */
public interface TaskDao extends GenericDao<Task> {

	/* All methods from Generic DAO */

	public List<Task> getAllTasksForWish(int wishId);
}
