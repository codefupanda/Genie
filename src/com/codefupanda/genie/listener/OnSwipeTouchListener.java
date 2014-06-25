/* 
 * See the file "LICENSE" for the full license governing this code.
 */
package com.codefupanda.genie.listener;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.codefupanda.genie.constant.Constants;

/**
 * Swipe Listener.
 * 
 * @author Shashank
 */
public class OnSwipeTouchListener implements OnTouchListener {

	private final GestureDetector gestureDetector;
	private View view;
	
	public OnSwipeTouchListener(Context context) {
		gestureDetector = new GestureDetector(context, new GestureListener());
	}

	@Override
	public boolean onTouch(final View view, final MotionEvent motionEvent) {
		this.view = view;
		return gestureDetector.onTouchEvent(motionEvent);
	}

	private final class GestureListener extends SimpleOnGestureListener {

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			float diffY = e2.getY() - e1.getY();
			float diffX = e2.getX() - e1.getX();

			if (Math.abs(diffX) > Math.abs(diffY)) {
				if (Math.abs(diffX) > Constants.SWIPE_THRESHOLD
						&& Math.abs(velocityX) > Constants.SWIPE_VELOCITY_THRESHOLD) {
					if (diffX > 0) {
						onSwipeRight(view, e1);
					} else {
						onSwipeLeft(view);
					}
				}
			} else {
				if (Math.abs(diffY) > Constants.SWIPE_THRESHOLD
						&& Math.abs(velocityY) > Constants.SWIPE_VELOCITY_THRESHOLD) {
					if (diffY > 0) {
						onSwipeDown(view);
					} else {
						onSwipeUp(view);
					}
				}
			}
			return false;
		}
	}

	/**
	 * Override this function for right swipe.
	 */
	public void onSwipeRight(View view, MotionEvent e1) {}

	/**
	 * Override this function for left swipe.
	 */
	public void onSwipeLeft(View view) {}

	/**
	 * Override this function for up swipe.
	 */
	public void onSwipeUp(View view) {}

	/**
	 * Override this function for down swipe.
	 */
	public void onSwipeDown(View view) {}
}