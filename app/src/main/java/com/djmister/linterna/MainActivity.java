 package com.djmister.linterna;

import android.hardware.Camera;
import android.os.Bundle;


import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;

import java.security.Policy;


 public class MainActivity extends AppCompatActivity {
     private static final String WAKE_LOCK= "Linterna";
    private Camera camera;
     private PowerManager.WakeLock wakeLock;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Encender el flash
        camera=Camera.open();
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
        camera.startPreview();
        //Adquirir el wake_lock
        PowerManager powerManager = (PowerManager)getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,WAKE_LOCK);
        wakeLock.setReferenceCounted(false);
        if (!wakeLock.isHeld())
        {
            wakeLock.acquire();
        }

}

    @Override
    public void onBackPressed()
   {
       super.onBackPressed();
       //Apagar el flash
       camera.stopPreview();
       camera.release();
       //Soltar el wake_lock
       wakeLock.release();
   }



    }