/* 
 * See the file "LICENSE" for the full license governing this code.
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.codefupanda.genie.constant.Constants;
import com.codefupanda.genie.dao.CategoryDao;
import com.codefupanda.genie.dao.WishDao;
import com.codefupanda.genie.dao.impl.CategoryDaoImpl;
import com.codefupanda.genie.dao.impl.WishDaoImpl;
import com.codefupanda.genie.entity.Category;
import com.codefupanda.genie.entity.Wish;
import com.codefupanda.genie.util.AndroiUiUtil;
import com.codefupanda.genie.util.Util;

/**
 * Activity for Add and Edit functionality.
 * 
 * @author Shashank
 */
@SuppressLint("ValidFragment")
public class AddActivity extends ActionBarActivity {

	private static final String END_DATE_DIALOG = "END_DATE_DIALOG";
	private WishDao wishDao;
	private CategoryDao categoryDao;
	private Calendar endDate;
	private Spinner categories;
	private EditText title;
	private EditText description;
	private Button wishButton;
	private Animation slideUp;
	
	/**
	 * For edit populated with existing wish 
	 * and for add a new wish object is assigned.
	 */
	private Wish wish;
	
	/** Used by edit functionality. */
	private boolean isEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// customize action bar
		AndroiUiUtil.customActionbar(getApplicationContext(), getSupportActionBar());
		
		setContentView(R.layout.activity_add);
		getSupportActionBar().setHomeButtonEnabled(true);

		wishDao = new WishDaoImpl(getApplicationContext());
		categoryDao = new CategoryDaoImpl(getApplicationContext());
		endDate = Calendar.getInstance();

		Bundle extras = getIntent().getExtras();
		
		if(extras != null && extras.getSerializable(Constants.EDIT_WISH) != null) {
			isEdit = true;
			wish = (Wish) extras.getSerializable(Constants.EDIT_WISH);
		} else {
			wish = new Wish();
			wish.setCategory(new Category());
		}
		
		// if edit, set the end date
		if(isEdit) {
			endDate.setTime(wish.getEndDate());
		}
		
		TextView endDateView = (TextView) findViewById(R.id.end_date);
		int year = endDate.get(Calendar.YEAR);
		int month = endDate.get(Calendar.MONTH) + 1; // Month is 0 based, just add 1
		int day = endDate.get(Calendar.DAY_OF_MONTH);
		// populate date
		setCurrentDateForEndDateView(day, month, year, endDateView);

		// populate drop down
		categories = (Spinner) findViewById(R.id.category);
		populateCategories(categories);

		title = (EditText) findViewById(R.id.title);
		description = (EditText) findViewById(R.id.description);
		wishButton = (Button) findViewById(R.id.wishButton);
		
		if(isEdit) {
			categories.setSelection(wish.getCategory().getId());
			title.setText(wish.getTitle());
			description.setText(wish.getDescription());
			wishButton.setText(getResources().getString(R.string.save));
			LinearLayout ll = (LinearLayout) findViewById(R.id.container);
			int childcount = ll.getChildCount();
			for (int i=0; i < childcount; i++){
			      View v = ll.getChildAt(i);
			      v.setVisibility(View.VISIBLE);
			}
		}
		
		wishButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				String titleText = title.getText().toString();
				String descriptionText = description.getText().toString();
				
				// Fields are mandatory
				if (categories.getSelectedItemPosition() == 0
						|| Util.isBlank(descriptionText)
						|| Util.isBlank(titleText)) {
					AndroiUiUtil.toast(getApplicationContext(), getResources()
							.getString(R.string.fields_mendatory));
					return ;
				}
				
				wish.getCategory().setId(categories.getSelectedItemPosition());
				wish.setTitle(titleText);
				wish.setDescription(descriptionText);
				wish.setEndDate(endDate.getTime());
				
				int toastMessage = R.string.add_wish_success;
				
				if (isEdit) {
					toastMessage = R.string.update_wish_success;
					wishDao.update(wish);
				} else {
					wishDao.add(wish);
				}
				AndroiUiUtil.toast(getApplicationContext(), getResources()
						.getString(toastMessage));
				finish();
			}
		});
		
		slideUp = AnimationUtils.loadAnimation(this,
                R.anim.slide_in_buttom);
	}

	private void populateCategories(final Spinner categories) {
		final List<String> list = categoryDao.getAllNames();

		// A Unknown Android bug
		// onItemSelected is called when it is not meant to
		// setting a blank select option
		// and rejecting the value if blank is selected
		list.add(0, "");

		// Option to add a new category
		if (!isEdit) {
			list.add(list.size(),
					getResources().getString(R.string.add_new_category));
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categories.setAdapter(dataAdapter);

		// Do not animate on selection for edit feature.
		if (!isEdit)
		categories.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int position, long arg3) {
				// A unknown issue!
				if (position == 0) {
					return;
				}

				// New category selected
				if (position == list.size() - 1) {
					final Dialog dialog = new Dialog(AddActivity.this);
					View newCategoryView = getLayoutInflater().inflate(
							R.layout.new_category_dialog, null);
					dialog.setContentView(newCategoryView);

					Button cancel = (Button) newCategoryView
							.findViewById(R.id.cancel);
					cancel.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View view) {
							dialog.dismiss();
						}
					});

					final EditText categoryEditText = (EditText) newCategoryView
							.findViewById(R.id.categoryName);
					final EditText whWordEditText = (EditText) newCategoryView
							.findViewById(R.id.whWord);

					Button create = (Button) newCategoryView
							.findViewById(R.id.create);
					create.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							String categoryName = categoryEditText.getText()
									.toString();
							String whWord = whWordEditText.getText().toString();
							if (!Util.isBlank(categoryName)
									&& !Util.isBlank(whWord)) {
								Category category = new Category();
								category.setName(categoryName);
								category.setWhWord(whWord);
								categoryDao.add(category);
								populateCategories(categories);
								AndroiUiUtil.toast(getBaseContext(),
										R.string.create_category_successful);
								dialog.dismiss();
							}

						}
					});
					dialog.setTitle(getResources().getString(
							R.string.action_new));
					dialog.show();
					return;
				}

				TextView textView = (TextView) findViewById(R.id.title_text);
				Category category = categoryDao.get(categories
						.getSelectedItemPosition());
				// Though not expecting a null here!
				if (category != null) {
					textView.setText(category.getWhWord());
				}

				// Slide up the title
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
			}

		});
	}

	/**
	 * display current date
	 * 
	 * @param day
	 */
	private void setCurrentDateForEndDateView(int day, int month, int year,
			TextView endDate) {
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
			// Use the end date as the default date in the picker
			final Calendar c = endDate;
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
			endDateView.setText(Util.getDateString(day, month, year));
		}
	}
}
