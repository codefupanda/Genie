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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.codefupanda.genie.dao.WishDao;
import com.codefupanda.genie.dao.impl.WishDaoImpl;
import com.codefupanda.genie.entity.Category;
import com.codefupanda.genie.entity.Wish;
import com.codefupanda.genie.util.AndroiUiUtil;

/**
 * @author Shashank
 * 
 */
public class AddActivity extends ActionBarActivity {

	protected static final int DATE_DIALOG_ID = 0;
	private WishDao wishDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		getSupportActionBar().setHomeButtonEnabled(true);

		wishDao = new WishDaoImpl(getApplicationContext());

		// populate date
		setCurrentDateForEndDateView();

		// populate drop down
		final Spinner categories = (Spinner) findViewById(R.id.category);
		populateCategories(categories);

		final EditText title = (EditText) findViewById(R.id.title);
		final EditText description = (EditText) findViewById(R.id.description);
		// final DatePicker endDate = (DatePicker)
		// findViewById(R.id.endDatePicker);

		Button wish = (Button) findViewById(R.id.wishButton);
		wish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Wish newWish = new Wish();
				newWish.setCategory(new Category((int) categories
						.getSelectedItemId()));
				newWish.setTitle(title.getText().toString());
				newWish.setDescription(description.getText().toString());
				// newWish.setEndDate(Util.getDateFromDatePicket(endDate));
				wishDao.add(newWish);
				AndroiUiUtil
						.toast(getApplicationContext(), "Added a new entry");
				finish();
			}
		});
	}

	private void populateCategories(Spinner categories) {
		List<String> list = new ArrayList<String>();
		list.add("list 1");
		list.add("list 2");
		list.add("list 3");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categories.setAdapter(dataAdapter);
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
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH); // Month is 0 based, just add 1
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		switch (id) {
		case DATE_DIALOG_ID:
			// set date picker as current date
			return new DatePickerDialog(this, datePickerListener, year, month,
					day);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker arg0, int year, int month, int day) {
			TextView endDate = (TextView) findViewById(R.id.end_date);
			endDate.setText(new StringBuilder().append(month + 1)
					   .append("-").append(day).append("-").append(year)
					   .append(" "));
		}
	};
}
