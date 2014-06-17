/*
 * Copyright (C) Shashank Kulkarni - Shashank.physics AT gmail DOT com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.codefupanda.genie.dao.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.codefupanda.genie.dao.Key;
import com.codefupanda.genie.dao.Table;
import com.codefupanda.genie.dao.WishDao;
import com.codefupanda.genie.entity.Category;
import com.codefupanda.genie.entity.Wish;

/**
 * A abstract database handler.
 * 
 * @author Shashank
 */
public class WishDaoImpl extends AbstractDao implements WishDao {

	public WishDaoImpl(Context context) {
		super(context);
	}

	@Override
	public void add(Wish wish) {
		open();
		ContentValues values = new ContentValues();
		values.put(Key.CATEGORY.toString(), wish.getCategory().getId());
		values.put(Key.TITLE.toString(), wish.getTitle());
		values.put(Key.DESCRIPTION.toString(), wish.getDescription());
		if(wish.getEndDate() != null) { 
			values.put(Key.END_DATE.toString(), wish.getEndDate().getTime());
		}
		// Inserting Row
		database.insert(Table.WISHES.toString(), null, values);
		close();
	}

	@Override
	public Wish get(int id) {
		return null;
	}

	@Override
	public List<Wish> getAll() {
		List<Wish> wishes = new LinkedList<Wish>();
		String selectQuery = "SELECT  * FROM " + Table.WISHES;

		open();
		Cursor cursor = database.rawQuery(selectQuery, null);
		
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Wish wish = new Wish();
				wish.setId(Integer.parseInt(cursor.getString(0)));
				wish.setCategory(new Category(Integer.parseInt(cursor.getString(1)), "Category name", false));
				wish.setTitle(cursor.getString(2));
				wish.setDescription(cursor.getString(3));
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(cursor.getLong(4));
				wish.setEndDate(calendar.getTime());
				wishes.add(wish);
			} while (cursor.moveToNext());
		}
		close();
		return wishes;
	}

	@Override
	public void update(Wish object) {
	}

	@Override
	public void delete(int id) {
	}

	@Override
	public Map<Category, List<Wish>> getCategoryWiseWishes() {
		Map<Category, List<Wish>> categoryWiseWishes = new HashMap<Category, List<Wish>>();
		
		for(Wish wish: getAll()) {
			// if the category list does not exists
			if(!categoryWiseWishes.containsKey(wish.getCategory())) {
				// add a new blank list
				categoryWiseWishes.put(wish.getCategory(), new LinkedList<Wish>());
			}
			// add wish to the list
			categoryWiseWishes.get(wish.getCategory()).add(wish);
		}
		
		return categoryWiseWishes;
	}

}