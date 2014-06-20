/*
 * See the file "LICENSE" for the full license governing this code.
 */
package com.codefupanda.genie;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Activity for settings screen. 
 *  
 * @author Shashank
 */
public class SettingsActivity extends ActionBarActivity {
	
	/**
	 * Populates the sms text and notification timing.
	 * On click of save button, saves settings.
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
    
}
