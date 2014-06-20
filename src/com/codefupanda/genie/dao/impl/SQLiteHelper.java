/*
 * See the file "LICENSE" for the full license governing this code.
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
			Key.WT_WORD + " TEXT, " +
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
