package com.example.helloworldtrial;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.File;
import java.io.IOException;

/**
 * Camera preview
 */
@SuppressLint("NewApi")
public class CameraPreview extends SurfaceView {
  private Camera _camera;

  public CameraPreview(Context context) {
    super(context);

    getHolder().addCallback(new SurfaceHolderCallback());
  }

  public Camera getCamera() {
    return _camera;
  }

  public CameraPreview(Context context, AttributeSet attrs) {
    super(context, attrs);

    getHolder().addCallback(new SurfaceHolderCallback());
  }

  class SurfaceHolderCallback implements SurfaceHolder.Callback {
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
      if (null != _camera) {

        try {
          // This camera parameter is set to fix a bug in XE12 that garbles the preview
          Camera.Parameters params = _camera.getParameters();
          params.setPreviewFpsRange(30000, 30000);
          _camera.setParameters(params);

          // Start the preview
          _camera.setPreviewDisplay(holder);
          _camera.startPreview();
        }
        catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
      _camera = Camera.open();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
      // Stop the preview and release the camera
      _camera.stopPreview();
      _camera.release();
      android.util.Log.d("CameraActivity", "surfaceDestroyed.");
    }
  }
}
