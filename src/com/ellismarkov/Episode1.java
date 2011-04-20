package com.ellismarkov;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class Episode1 extends Activity {

    //! OpenGL SurfaceView
    public GLSurfaceView mGLSurfaceView;
    public Handler mHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        if (!isOGLES20Compatible()) {
            // C++ Reflex sorry
            mGLSurfaceView = null;
            showOGLES20ErrorDialogBox();
            return;
        }

        // We don't use Layout. But you can.
        // create an OpenGLView
        mGLSurfaceView = new GLSurfaceView(this);
        mGLSurfaceView.setDebugFlags(GLSurfaceView.DEBUG_CHECK_GL_ERROR);
        mGLSurfaceView.setEGLContextClientVersion(2);
        mGLSurfaceView.setRenderer(new GLES20Renderer(this));
        setContentView(mGLSurfaceView);

        mHandler = new Handler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGLSurfaceView!=null) {
            mGLSurfaceView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGLSurfaceView!=null) {
            mGLSurfaceView.onPause();
        }
    }


    /* This method verify that your Phone is compatible with OGLES 2.x */
    private boolean isOGLES20Compatible() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return (info.reqGlEsVersion >= 0x20000);
    }

    /* show an error message */
    private void showOGLES20ErrorDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No Android 2.2 device or No OpenGL ES 2.0 GPU Found! Please buy a real device!!!").setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


}