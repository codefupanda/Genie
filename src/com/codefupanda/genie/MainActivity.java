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

package com.codefupanda.genie;

import java.lang.reflect.Method;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.codefupanda.genie.dao.WishDao;
import com.codefupanda.genie.dao.impl.WishDaoImpl;
import com.codefupanda.genie.entity.Wish;

/**
 * The main activity.
 * 
 * @author Shashank
 */
public class MainActivity extends ActionBarActivity {

	private static final String TAG = "MainActivity";
	private WishDao wishDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		wishDao = new WishDaoImpl(getApplicationContext());
	}

	
	@Override
	protected void onResume() {
		TextView textView = (TextView) findViewById(R.id.textView1);
		String text = "";
		List<Wish> wishes = wishDao.getAll();

		for (Wish wish : wishes) {
			text += wish;
		}
		textView.setText(text);
		super.onResume();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if(id == R.id.action_new) {
			startActivity(new Intent(getApplicationContext(), AddActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (NoSuchMethodException e) {
					Log.e(TAG, "onMenuOpened", e);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}
}
