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

package com.codefupanda.genie.util;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
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
	 * @param applicationContext context
	 * @param message the message to toast
	 */
	public static void toast(Context applicationContext, String message) {
		Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show();
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
