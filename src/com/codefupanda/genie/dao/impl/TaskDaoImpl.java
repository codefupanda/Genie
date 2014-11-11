package com.codefupanda.genie.dao.impl;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.codefupanda.genie.dao.Key;
import com.codefupanda.genie.dao.Table;
import com.codefupanda.genie.dao.TaskDao;
import com.codefupanda.genie.entity.Task;
import com.codefupanda.genie.entity.Wish;

public class TaskDaoImpl extends AbstractDao implements TaskDao {

	public TaskDaoImpl(Context context) {
		super(context);
	}

	@Override
	public void add(Task task) {
		openForWrite();
		ContentValues values = populateTaskDetails(task);
		database.insert(Table.TASK.toString(), null, values);
		close();
	}

	@Override
	public Task get(int id) {
		String query = SELECT_STAR_FROM + Table.TASK + " where id = " + id;
		return getTasksForQuery(query).get(0);
	}

	@Override
	public List<Task> getAll() {
		String query = SELECT_STAR_FROM + Table.TASK;
		return getTasksForQuery(query);
	}

	@Override
	public void update(Task task) {
		String whereClause  = " id = " + task.getId();
		ContentValues values = populateTaskDetails(task);
		
		// Update row
		openForWrite();
		database.update(Table.TASK.toString(), values, whereClause, null);
		close();
	}

	@Override
	public void delete(int id) {
		String whereClause = " id = " + id;
		openForWrite();
		database.delete(Table.TASK.toString(), whereClause, null);
		close();
	
	}

	@Override
	public List<Task> getAllTasksForWish(int wishId) {
		String query = SELECT_STAR_FROM + Table.TASK + " where wish = " + wishId;
		return getTasksForQuery(query);
	}

	private ContentValues populateTaskDetails(Task task) {
		ContentValues values = new ContentValues();
		values.put(Key.WISH.toString(), task.getWish().getId());
		values.put(Key.TITLE.toString(), task.getTitle());
		values.put(Key.DESCRIPTION.toString(), task.getDescription());
		values.put(Key.COMPLETION.toString(), task.getCompletion());
		return values;
	}

	private List<Task> getTasksForQuery(String query) {
		List<Task> tasks = new LinkedList<Task>();
		openForRead();
		Cursor cursor = database.rawQuery(query, null);
		
		while(cursor.moveToNext()) {
			Task task = new Task();
			task.setId(cursor.getInt(0));
			task.setWish(new Wish(cursor.getInt(1), null, null, null, null));
			task.setTitle(cursor.getString(2));
			task.setDescription(cursor.getString(3));
			task.setCompletion(cursor.getInt(4));
			tasks.add(task);
		}
		close();
		return tasks;
	}
}
