/* 
 * See the file "LICENSE" for the full license governing this code.
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
		ContentValues values = populateWishDetails(wish);
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
				//TODO: QUERY REMAINING DETAILS
				wish.setCategory(new Category(Integer.parseInt(cursor.getString(1)), null, null, false));
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
	public void update(Wish wish) {
		String whereClause  = " id = " + wish.getId();
		ContentValues values = populateWishDetails(wish);
		
		// Update row
		open();
		database.update(Table.WISHES.toString(), values, whereClause, null);
		close();
	}

	@Override
	public void delete(int id) {
		String whereClause = " id = " + id;
		open();
		database.delete(Table.WISHES.toString(), whereClause, null);
		close();
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
	
	/**
	 * Copy wish details into content values.
	 * 
	 * @param wish
	 * @return ContentValues with values populated
	 */
	private ContentValues populateWishDetails(Wish wish) {
		ContentValues values = new ContentValues();
		values.put(Key.CATEGORY.toString(), wish.getCategory().getId());
		values.put(Key.TITLE.toString(), wish.getTitle());
		values.put(Key.DESCRIPTION.toString(), wish.getDescription());
		values.put(Key.END_DATE.toString(), wish.getEndDate().getTime());
		return values;
	}

}