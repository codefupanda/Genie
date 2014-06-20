/*
 * See the file "LICENSE" for the full license governing this code.
 */
package com.codefupanda.genie.util;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codefupanda.genie.R;

/**
 * @author Shashank
 *
 */
public class AndroiUiUtil {

	/**
	 * Toast info.
	 * 
	 * @param context context
	 * @param message the message to toast
	 */
	public static void toast(Context context, String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		toast.getView().setBackgroundColor(context.getResources().getColor(R.color.white_opaque));
		TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
		v.setShadowLayer(0, 0, 0, R.color.white_opaque);
		v.setTextColor(context.getResources().getColor(R.color.blue));
		toast.show();
	}

	/**
	 * Create method with String resource ID.
	 * 
	 * @param baseContext
	 * @param createCategorySuccessful
	 */
	public static void toast(Context context, int createCategorySuccessful) {
		toast(context, context.getResources().getString(createCategorySuccessful));
	}

	/**
	 * Common code for customizing the action bar.
	 * 
	 * @param context context
	 * @param supportActionBar actionBar
	 */
	public static void customActionbar(Context context, ActionBar supportActionBar) {
		supportActionBar.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.white_opaque)));
	}

}
