package org.xjiop.oneplusaoddoubletapmod;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.os.PowerManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

public class KeyService extends AccessibilityService {

    private final String TAG = "KeyService";

    private long CLICK_DELAY;

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

        boolean result = false;

        if(event.getKeyCode() == KeyEvent.KEYCODE_F4)
            result = doubleClick();

        return result;
    }

    private boolean doubleClick() {

        boolean result;

        long thisTime = System.currentTimeMillis();
        if ((thisTime - CLICK_DELAY) < 250) {

            CLICK_DELAY = -1;
            result = true;

            PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if(powerManager != null) {
                PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, getPackageName() + ":double_tap");
                wakeLock.acquire(500L);
                wakeLock.release();
            }
        }
        else {
            CLICK_DELAY = thisTime;
            result = false;
        }

        //Log.d(TAG, "doubleClick: " + result);

        return result;
    }
}
