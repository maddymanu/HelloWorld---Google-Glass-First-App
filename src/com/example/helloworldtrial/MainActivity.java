package com.example.helloworldtrial;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.glass.app.Card;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

public class MainActivity extends Activity {
	private CameraPreview cameraPreview;
	private Camera camera;

	private GestureDetector mGestureDetector;
	private static final int TAKE_PICTURE_REQUEST = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
//		Card card = new Card(getApplicationContext());
//		card.setText("wqdwq");
//		card.setFootnote("well hello there");
//		View view = card.toView();
//		//setContentView(view);
		
		cameraPreview = new CameraPreview(this);
	    setContentView(cameraPreview);
	
		mGestureDetector = createGestureDetector(this);
	}
	
	private GestureDetector createGestureDetector(Context context) {
	    GestureDetector gestureDetector = new GestureDetector(context);
	        //Create a base listener for generic gestures
	        gestureDetector.setBaseListener( new GestureDetector.BaseListener() {
	            @Override
	            public boolean onGesture(Gesture gesture) {
	                if (gesture == Gesture.TAP) {
	                	
	                	
	                	Card card = new Card(getApplicationContext());
	            		card.setText("Tapped!");
	            		card.setFootnote("well hello there");
	            		View view = card.toView();
	            		setContentView(view);
	            	
	            		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	            	    startActivityForResult(intent, TAKE_PICTURE_REQUEST);
	            		
	                    return true;
	                } else if (gesture == Gesture.TWO_TAP) {
	                    // do something on two finger tap
	                    return true;
	                } else if (gesture == Gesture.SWIPE_RIGHT) {
	                    // do something on right (forward) swipe
	                    return true;
	                } else if (gesture == Gesture.SWIPE_LEFT) {
	                    // do something on left (backwards) swipe
	                    return true;
	                }
	                return false;
	            }
	        });
	        gestureDetector.setFingerListener(new GestureDetector.FingerListener() {
	            @Override
	            public void onFingerCountChanged(int previousCount, int currentCount) {
	              // do something on finger count changes
	            }
	        });
	        gestureDetector.setScrollListener(new GestureDetector.ScrollListener() {
	            @Override
	            public boolean onScroll(float displacement, float delta, float velocity) {
					return false;
	                // do something on scrolling
	            }
	        });
	        return gestureDetector;
	    }

	    /*
	     * Send generic motion events to the gesture detector
	     */
	    @Override
	    public boolean onGenericMotionEvent(MotionEvent event) {
	        if (mGestureDetector != null) {
	            return mGestureDetector.onMotionEvent(event);
	        }
	        return false;
	    }




}


