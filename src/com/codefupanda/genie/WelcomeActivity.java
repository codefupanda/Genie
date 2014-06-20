/*
 * See the file "LICENSE" for the full license governing this code.
 */
package com.codefupanda.genie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;

import com.codefupanda.genie.constant.Constants;

/**
 * Activity for welcoming user when first run. 
 *  
 * @author Shashank
 */
public class WelcomeActivity extends Activity {

	/** Text fade IN/OUT delay */
	private static final int ANIMATION_DELAY = 3000;
	
	/**
	 * Fade out the welcome message and show the instruction.
	 * then move the user to settings screen.
	 *  
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		final TextView welcome = (TextView) findViewById(R.id.animatedWelcome);

		final Animation out = new AlphaAnimation(1.0f, 0.0f);
		out.setDuration(ANIMATION_DELAY);

		welcome.startAnimation(out);

		final Animation in = new AlphaAnimation(0.0f, 1.0f);
		in.setDuration(ANIMATION_DELAY);

		// Once the Welcome text disappears,
		// show the preference message
		out.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationEnd(Animation animation) {
				welcome.setText(getResources().getString(R.string.genie_intro));
				welcome.startAnimation(in);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationStart(Animation animation) {
			}
		});

		// done with animation
		// show Main (Home) screen
		in.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationEnd(Animation animation) {
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra(Constants.SHOW_FLASH, Boolean.FALSE);
				getApplicationContext().startActivity(intent);
				finish();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationStart(Animation animation) {
			}
		});
	}
}
