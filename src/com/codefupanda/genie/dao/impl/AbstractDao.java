/*
 * See the file "LICENSE" for the full license governing this code.
 */
package com.codefupanda.genie.dao.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * No meaningful functions here so abstract.
 * @author Shashank
 */
public abstract class AbstractDao {
	
	/** Select Query. */
	protected static final String SELECT_STAR_FROM = "SELECT  * FROM ";
	
	/** WHERE ID =  */
	protected static final String WHERE_ID = " WHERE ID = ";

	protected SQLiteDatabase database;
	protected SQLiteHelper baseDao;
	
	/**
	 * Constructor.
	 * @param context
	 */
	protected AbstractDao(Context context) {
		baseDao = new SQLiteHelper(context);
	}
	
	/**
	 * open database
	 */
	protected void openForWrite() {
		database = baseDao.getWritableDatabase();
	}

	/**
	 * open database
	 */
	protected void openForRead() {
		database = baseDao.getReadableDatabase();
	}
	
	/**
	 * Close database.
	 */
	protected void close() {
		baseDao.close();
	}
}
