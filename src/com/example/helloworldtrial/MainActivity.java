package com.example.helloworldtrial;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileObserver;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.google.android.glass.app.Card;
import com.google.android.glass.media.CameraManager;
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
		
		Card card2 = new Card(this);
		card2.setText("Con imagen de fondo");
		card2.setImageLayout(Card.ImageLayout.FULL);
		card2.addImage(R.drawable.ic_launcher);
		View view = card2.toView();
		
		//cameraPreview = new CameraPreview(this);
	    setContentView(view);
	
		mGestureDetector = createGestureDetector(this);
	}
	
	private GestureDetector createGestureDetector(Context context) {
	    GestureDetector gestureDetector = new GestureDetector(context);
	        //Create a base listener for generic gestures
	        gestureDetector.setBaseListener( new GestureDetector.BaseListener() {
	            @Override
	            public boolean onGesture(Gesture gesture) {
	                if (gesture == Gesture.TAP) {
	                	
//	                	
//	                	Card card = new Card(getApplicationContext());
//	            		card.setText("Tapped!");
//	            		card.setFootnote("well hello there");
//	            		View view = card.toView();
//	            		setContentView(view);
	            	
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
	    
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        if (requestCode == TAKE_PICTURE_REQUEST && resultCode == RESULT_OK) {
	            String picturePath = data.getStringExtra(
	                    CameraManager.EXTRA_PICTURE_FILE_PATH);
	            processPictureWhenReady(picturePath);
	        }

	        super.onActivityResult(requestCode, resultCode, data);
	    }
	    
	    private void processPictureWhenReady(final String picturePath) {
	        final File pictureFile = new File(picturePath);

	        if (pictureFile.exists()) {
	        	
	        	//need to shorten the bitmap over here
	        	
	        	Log.d("SITTING", "Picture exists and its path is: URIII -- " + Uri.fromFile(pictureFile));
	        	Card pic = new Card(this);
	        	pic.setImageLayout(Card.ImageLayout.FULL);
	        	//pic.addImage(R.drawable.trial);
	        	
//	        	View view = pic.toView();
//        		setContentView(view);
        		
        		//starting shortening the bitmap overhere
        		int targetWidth = 640;
    			int targetHeight = 360;
    			
    			BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
    			bmpOptions.inJustDecodeBounds = true;
    			
        		
    			BitmapFactory.decodeFile(picturePath, bmpOptions);
        		
    			int currentHeight = bmpOptions.outHeight;
    			int currentWidth = bmpOptions.outWidth;

    			int sampleSize = 1;
    			
    			if (currentHeight>targetHeight || currentWidth>targetWidth)
    			{
    				if (currentWidth>currentHeight)
    					sampleSize = Math.round((float)currentHeight/(float)targetHeight);
    				else
    					sampleSize = Math.round((float)currentWidth/(float)targetWidth);
    			}

    			bmpOptions.inSampleSize = sampleSize;

    			bmpOptions.inJustDecodeBounds = false;
    			
    			Bitmap bit2 = BitmapFactory.decodeFile(picturePath, bmpOptions);
    			
    			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    			bit2.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
    			String path = Images.Media.insertImage(this.getContentResolver(), bit2, "Title",null);
    			
    			
    			ImageView imageView = new ImageView(this);
    			imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath, bmpOptions));
    			
    			//pic.addImage(Uri.parse(path));
    			//View view = pic.toView();
        		setContentView(imageView);
        		
        		
	        	
	        } else {
	            // The file does not exist yet. Before starting the file observer, you
	            // can update your UI to let the user know that the application is
	            // waiting for the picture (for example, by displaying the thumbnail
	            // image and a progress indicator).

	            final File parentDirectory = pictureFile.getParentFile();
	            FileObserver observer = new FileObserver(parentDirectory.getPath()) {
	                // Protect against additional pending events after CLOSE_WRITE is
	                // handled.
	                private boolean isFileWritten;

	                @Override
	                public void onEvent(int event, String path) {
	                    if (!isFileWritten) {
	                        // For safety, make sure that the file that was created in
	                        // the directory is actually the one that we're expecting.
	                        File affectedFile = new File(parentDirectory, path);
	                        isFileWritten = (event == FileObserver.CLOSE_WRITE
	                                && affectedFile.equals(pictureFile));

	                        if (isFileWritten) {
	                            stopWatching();

	                            // Now that the file is ready, recursively call
	                            // processPictureWhenReady again (on the UI thread).
	                            runOnUiThread(new Runnable() {
	                                @Override
	                                public void run() {
	                                    processPictureWhenReady(picturePath);
	                                }
	                            });
	                        }
	                    }
	                }
	            };
	            observer.startWatching();
	        }
	    }




}


