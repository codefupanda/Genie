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

import java.util.List;
import java.util.Map;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import com.codefupanda.genie.adapter.ExpandableListAdapter;
import com.codefupanda.genie.constant.Constants;
import com.codefupanda.genie.dao.CategoryDao;
import com.codefupanda.genie.dao.WishDao;
import com.codefupanda.genie.dao.impl.CategoryDaoImpl;
import com.codefupanda.genie.dao.impl.WishDaoImpl;
import com.codefupanda.genie.entity.Category;
import com.codefupanda.genie.entity.Wish;
import com.codefupanda.genie.util.AndroiUiUtil;

/**
 * The main activity.
 * 
 * @author Shashank
 */
public class MainActivity extends ActionBarActivity {

	private WishDao wishDao;
	private CategoryDao categoryDao;
	private ExpandableListAdapter expandableListAdapter;
	private ExpandableListView expandableListView;

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		supportRequestWindowFeature(Window.FEATURE_ACTION_BAR);
		getSupportActionBar().hide();
		
		AndroiUiUtil.customActionbar(getApplicationContext(), getSupportActionBar());
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
				final Dialog dialog = new Dialog(MainActivity.this);
				View view = getLayoutInflater().inflate(R.layout.description_view_dialog, null);
				dialog.setContentView(view);
				
				Wish wish = expandableListAdapter.getChild(groupPosition,
						childPosition);
				dialog.setTitle(wish.getTitle());
				
				TextView description = (TextView) view.findViewById(R.id.description);
				description.setText(wish.getDescription());
				
				Button okButton = (Button) view.findViewById(R.id.ok);
				okButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						dialog.dismiss();
					}
				});
				dialog.show();
				return false;
			}
		});
	}

	/**
	 * 
	 * 
	 * Flash is disabled when main activity is called after the welcome screen.
	 */
	@Override
	protected void onResume() {
		Bundle extras = getIntent().getExtras();
		
		if(extras != null && !extras.getBoolean(Constants.SHOW_FLASH)) {
			showHomeScreen();
		} else
			new Handler().postDelayed(new Runnable() {
				public void run() {
					View logo = findViewById(R.id.logo);
					logo.setVisibility(View.INVISIBLE);
					getSupportActionBar().show();
					showHomeScreen();
				}
			}, Constants.WELCOME_SCREEN_LENGTH);
		super.onResume();
	}

	private void showHomeScreen() {
		Map<Category, List<Wish>> categoryWiseWishes = wishDao
				.getCategoryWiseWishes();
		expandableListAdapter = new ExpandableListAdapter(this,
				categoryDao.getAll(), categoryWiseWishes);
		expandableListView.setAdapter(expandableListAdapter);
		expandableListView.setGroupIndicator(null);
		expandableListView.setVisibility(View.VISIBLE);
		expandableListView.startAnimation(AnimationUtils.loadAnimation(this,
				R.anim.slide_in_right));
		for (int i = 0; i < categoryWiseWishes.keySet().size(); i++) {
			expandableListView.expandGroup(i);
		}
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
}
