package org.xjiop.oneplusaoddoubletapmod;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.os.PowerManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

public class KeyService extends AccessibilityService {

    private final String TAG = "DBG | KeyService";

    private long CLICK_DELAY;
    private PowerManager powerManager;
    private PowerManager.WakeLock wakeLock;

    @Override
    public void onCreate() {
        super.onCreate();

        powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if(powerManager != null) {
            wakeLock = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, getPackageName() + ":double_tap");
        }
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        //Log.d(TAG, "onAccessibilityEvent: " + event);
    }

    @Override
    public void onInterrupt() {
        //Log.d(TAG, "onInterrupt");
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        //Log.d(TAG, "onKeyEvent: " + event);
        //Log.d(TAG, "isScreenOn: " + powerManager.isInteractive());

        boolean result = false;
        boolean isScreenOn = powerManager != null && powerManager.isInteractive();

        if(!isScreenOn && event.getKeyCode() == KeyEvent.KEYCODE_F4)
            result = doubleClick();

        return result || super.onKeyEvent(event);
    }

    private boolean doubleClick() {

        boolean result = false;

        long thisTime = System.currentTimeMillis();
        if ((thisTime - CLICK_DELAY) < 250) {
            //Log.d(TAG, "doubleClick");

            CLICK_DELAY = -1;
            result = true;

            if(wakeLock != null) {
                wakeLock.acquire(500L);
                wakeLock.release();
            }
        }
        else {
            CLICK_DELAY = thisTime;
        }

        return result;
    }
}
