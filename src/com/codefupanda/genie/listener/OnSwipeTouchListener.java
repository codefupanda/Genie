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
			return false;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			boolean result = false;
			float diffY = e2.getY() - e1.getY();
			float diffX = e2.getX() - e1.getX();

			if (Math.abs(diffX) > Math.abs(diffY)) {
				if (Math.abs(diffX) > Constants.SWIPE_THRESHOLD
						&& Math.abs(velocityX) > Constants.SWIPE_VELOCITY_THRESHOLD) {
					if (diffX > 0) {
						result = onSwipeRight(view, e1, e2);
					} else {
						result = onSwipeLeft(view, e1, e2);
					}
				}
			} else {
				if (Math.abs(diffY) > Constants.SWIPE_THRESHOLD
						&& Math.abs(velocityY) > Constants.SWIPE_VELOCITY_THRESHOLD) {
					if (diffY > 0) {
						result = onSwipeDown(view, e1, e2);
					} else {
						result = onSwipeUp(view, e1, e2);
					}
				}
			}
			return result;
		}
	}

	/**
	 * Override this function for right swipe.
	 */
	public boolean onSwipeRight(View view, MotionEvent e1, MotionEvent e2) {
		return false;
	}

	/**
	 * Override this function for left swipe.
	 */
	public boolean onSwipeLeft(View view, MotionEvent e1, MotionEvent e2) {
		return false;
	}

	/**
	 * Override this function for up swipe.
	 */
	public boolean onSwipeUp(View view, MotionEvent e1, MotionEvent e2) {
		return false;
	}

	/**
	 * Override this function for down swipe.
	 */
	public boolean onSwipeDown(View view, MotionEvent e1, MotionEvent e2) {
		return false;
	}
}