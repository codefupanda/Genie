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
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.codefupanda.genie.adapter.ExpandableListAdapter;
import com.codefupanda.genie.dao.CategoryDao;
import com.codefupanda.genie.dao.WishDao;
import com.codefupanda.genie.dao.impl.CategoryDaoImpl;
import com.codefupanda.genie.dao.impl.WishDaoImpl;
import com.codefupanda.genie.entity.Category;
import com.codefupanda.genie.entity.Wish;

/**
 * The main activity.
 * 
 * @author Shashank
 */
public class MainActivity extends ActionBarActivity {

	private static final String TAG = "MainActivity";
	private WishDao wishDao;
	private CategoryDao categoryDao;
	private ExpandableListAdapter expandableListAdapter;
	private ExpandableListView expandableListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		categoryDao = new CategoryDaoImpl(getApplicationContext());
		wishDao = new WishDaoImpl(getApplicationContext());
		expandableListView = (ExpandableListView) findViewById(R.id.expandableCategories);
		
		SharedPreferences prefs = getSharedPreferences(
				"com.codefupanda.genie", MODE_PRIVATE);

		if (prefs.getBoolean("firstrun", true)) {
			categoryDao.add(new Category(1, "Visit", false));
			categoryDao.add(new Category(2, "Read", false));
			categoryDao.add(new Category(3, "Hangout with", false));

			prefs.edit().putBoolean("firstrun", false).commit();
		}
		expandableListView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setMessage(
						expandableListAdapter.getChild(groupPosition,
								childPosition).getDescription())
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				AlertDialog alert = builder.create();
				alert.show();
				return false;
			}
		});
	}

	@Override
	protected void onResume() {
		
		Map<Category, List<Wish>> categoryWiseWishes = wishDao
				.getCategoryWiseWishes();
		expandableListAdapter = new ExpandableListAdapter(this,
				categoryDao.getAll(), categoryWiseWishes);

		expandableListView.setAdapter(expandableListAdapter);
		expandableListView.setGroupIndicator(null);
		expandableListView.setVisibility(View.VISIBLE);
		expandableListView.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.slide_in_right));
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
		} else if (id == R.id.action_new) {
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
