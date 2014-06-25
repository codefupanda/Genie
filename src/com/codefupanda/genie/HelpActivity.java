/*
 * See the file "LICENSE" for the full license governing this code.
 */
package com.codefupanda.genie;

import com.codefupanda.genie.util.AndroiUiUtil;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.widget.TextView;

/**
 * The help activity. 
 *  
 * @author Shashank
 */
public class HelpActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		AndroiUiUtil.customActionbar(this, getSupportActionBar());
		
		TextView helpText = (TextView) findViewById(R.id.helpText);
		helpText.setText(Html.fromHtml(getResources().getString(R.string.helpText)));
	}
}
