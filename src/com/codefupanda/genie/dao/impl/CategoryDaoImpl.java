package com.codefupanda.genie.dao.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.codefupanda.genie.dao.CategoryDao;
import com.codefupanda.genie.dao.Key;
import com.codefupanda.genie.dao.Table;
import com.codefupanda.genie.entity.Category;

public class CategoryDaoImpl extends AbstractDao implements CategoryDao {

	/**
	 * Constructor.
	 * @param context
	 */
	public CategoryDaoImpl(Context context) {
		super(context);
	}

	@Override
	public void add(Category category) {
		open();
		ContentValues values = new ContentValues();
		values.put(Key.NAME.toString(), category.getName().toUpperCase(Locale.US));
		values.put(Key.USER_CREATED.toString(), category.isUserCreated());
		
		// Inserting Row
		database.insert(Table.CATEGORY.toString(), null, values);
		close();
	}

	@Override
	public Category get(int id) {
		return null;
	}

	@Override
	public List<Category> getAll() {
		List<Category> categories = new LinkedList<Category>();
		String selectQuery = SELECT_STAR_FROM + Table.CATEGORY;

		open();
		Cursor cursor = database.rawQuery(selectQuery, null);
		
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Category category = new Category();
				category.setId(Integer.parseInt(cursor.getString(0)));
				category.setName(cursor.getString(1));
				int userCreated = Integer.parseInt(cursor.getString(2));
				category.setUserCreated(userCreated == 0? false: true);
				categories.add(category);
			} while (cursor.moveToNext());
		}
		close();
		return categories;
	}

	@Override
	public void update(Category object) {
	}

	@Override
	public void delete(int id) {
	}

	@Override
	public List<String> getAllNames() {
		List<String> categories = new LinkedList<String>();
		String selectQuery = SELECT_STAR_FROM + Table.CATEGORY;

		open();
		
		Cursor cursor = database.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				categories.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}

		close();
		return categories;
	}

}
