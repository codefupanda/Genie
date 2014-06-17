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

import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.codefupanda.genie.dao.CategoryDao;
import com.codefupanda.genie.dao.WishDao;
import com.codefupanda.genie.dao.impl.CategoryDaoImpl;
import com.codefupanda.genie.dao.impl.WishDaoImpl;
import com.codefupanda.genie.entity.Category;
import com.codefupanda.genie.entity.Wish;
import com.codefupanda.genie.util.AndroiUiUtil;

/**
 * 
 * @author Shashank
 */
@SuppressLint("ValidFragment")
public class AddActivity extends ActionBarActivity {

	private static final String END_DATE_DIALOG = "END_DATE_DIALOG";
	protected static final int DATE_DIALOG_ID = 0;
	private WishDao wishDao;
	private CategoryDao categoryDao;
	private Calendar endDate;
	private Spinner categories;
	private EditText title;
	private EditText description;
	private Button wishButton;
	private Animation slideUp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		getSupportActionBar().setHomeButtonEnabled(true);

		wishDao = new WishDaoImpl(getApplicationContext());
		categoryDao = new CategoryDaoImpl(getApplicationContext());
		endDate = Calendar.getInstance();
		
		// populate date
		setCurrentDateForEndDateView();

		// populate drop down
		categories = (Spinner) findViewById(R.id.category);
		populateCategories(categories);

		title = (EditText) findViewById(R.id.title);
		description = (EditText) findViewById(R.id.description);

		wishButton = (Button) findViewById(R.id.wishButton);
		wishButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Wish newWish = new Wish();
				Category category = new Category();
				// selected item position starts with 0
				// whereas DB index starts with 1
				category.setId(categories.getSelectedItemPosition() + 1);
				newWish.setCategory(category);
				newWish.setTitle(title.getText().toString());
				newWish.setDescription(description.getText().toString());
				newWish.setEndDate(endDate.getTime());
				wishDao.add(newWish);
				AndroiUiUtil
						.toast(getApplicationContext(), "Added a new entry");
				finish();
			}
		});
		
		slideUp = AnimationUtils.loadAnimation(this,
                R.anim.slide_in_buttom);
	}

	@Override
	protected void onResume() {
		TextView titleTextView = (TextView) findViewById(R.id.category_text);
		titleTextView.setVisibility(View.VISIBLE);
		titleTextView.startAnimation(slideUp);
		categories.setVisibility(View.VISIBLE);
		categories.startAnimation(slideUp);
		super.onResume();
	}
	
	private void populateCategories(final Spinner categories) {
		List<String> list = categoryDao.getAllNames();

		// A Unknown Android bug
		// onItemSelected is called when it is not meant to
		// Setting a blank select option
		list.add(0, "");
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
			.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categories.setAdapter(dataAdapter);
		
		categories.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// A unknown issue!
				if(position == 0) {
					return;
				}
				// Slide up the title
				TextView textView = (TextView) findViewById(R.id.title_text);
				makeVisibleWithAnimation(textView);
				makeVisibleWithAnimation(title);
				
				// slide up description 
				textView = (TextView) findViewById(R.id.description_text);
				makeVisibleWithAnimation(textView);
				makeVisibleWithAnimation(description);
				
				// slide up remaining things
				textView = (TextView) findViewById(R.id.end_date_text);
				makeVisibleWithAnimation(textView);
				
				textView = (TextView) findViewById(R.id.end_date);
				makeVisibleWithAnimation(textView);
				
				makeVisibleWithAnimation(wishButton);
			}

			private void makeVisibleWithAnimation(View view) {
				view.setVisibility(View.VISIBLE);
				view.startAnimation(slideUp);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				//TODO: DO NOTHING?!
			}
			
		});
	}

	/** display current date */
	private void setCurrentDateForEndDateView() {
		TextView endDate = (TextView) findViewById(R.id.end_date);
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1; // Month is 0 based, just add 1
		int day = c.get(Calendar.DAY_OF_MONTH);

		// set current date into textview
		endDate.setText(new StringBuilder().append(month).append("-")
				.append(day).append("-").append(year).append(" "));

		endDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentManager fm = getSupportFragmentManager();
				DialogFragment dialog = new DatePickerFragment();
				dialog.show(fm, END_DATE_DIALOG);
			}
		});
	}

	class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		@Override
		public void onDateSet(DatePicker view, int year, int month, int day) {
			endDate.set(Calendar.YEAR, year);
			endDate.set(Calendar.MONTH, month);
			endDate.set(Calendar.DAY_OF_MONTH, day);
			TextView endDateView = (TextView) findViewById(R.id.end_date);
			endDateView.setText(new StringBuilder().append(month + 1)
					   .append("-").append(day).append("-").append(year)
					   .append(" "));
		}
	}
}
