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
import android.database.sqlite.SQLiteOpenHelper;

import com.codefupanda.genie.dao.Key;
import com.codefupanda.genie.dao.Table;

/**
 * 
 * @author Shashank
 */
public class SQLiteHelper extends SQLiteOpenHelper {
	
	/** Database Version. */
    private static final int DATABASE_VERSION = 1;
 
    /** Database Name. */
    private static final String DATABASE_NAME = "GENIE";
 
    /** Create Wishes table. */
    private static final String CREATE_WISHES_TABLE = 
    		"Create table " + Table.WISHES + " ( " + 
    		Key.ID  + " INTEGER PRIMARY KEY, " + 
    		Key.CATEGORY  + " INTEGER, " + 
    		Key.TITLE  + " TEXT, " + 
    		Key.DESCRIPTION  + " TEXT, " + 
    		Key.END_DATE  + " INTEGER, " +
    		"FOREIGN KEY(" + Key.CATEGORY + ") REFERENCES " + Table.CATEGORY + " ( " + Key.ID + " ) " +
    		")";

	private static final String CREATE_CATEGORY_TABLE = 
			"CREATE TABLE " + Table.CATEGORY + "( " +
			Key.ID + " INTEGER PRIMARY KEY, " +
			Key.NAME + " TEXT, " +
			Key.USER_CREATED + " INTEGER default 0 " +
			")";

	/**
	 * Constructor. 
	 * @param context
	 */
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    /** 
     * Creating Tables
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
    	database.execSQL(CREATE_CATEGORY_TABLE);
        database.execSQL(CREATE_WISHES_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Table.WISHES);
        db.execSQL("DROP TABLE IF EXISTS " + Table.CATEGORY);
        
        // Create tables again
        onCreate(db);
    }
}
