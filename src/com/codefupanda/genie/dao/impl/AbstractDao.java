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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * No meaningful functions here so abstract.
 * @author Shashank
 */
public abstract class AbstractDao {
	
	/** Select Query. */
	protected static final String SELECT_STAR_FROM = "SELECT  * FROM ";
	
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
	protected void open() {
		database = baseDao.getWritableDatabase();
	}

	/**
	 * Close database.
	 */
	protected void close() {
		baseDao.close();
	}
}
