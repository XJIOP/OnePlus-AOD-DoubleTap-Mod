package org.xjiop.oneplusaoddoubletapmod;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.os.PowerManager;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

public class KeyService extends AccessibilityService {

    private long CLICK_DELAY;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {}

    @Override
    public void onInterrupt() {}

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        return doubleClick();
    }

    private boolean doubleClick() {

        boolean result;

        long thisTime = System.currentTimeMillis();
        if ((thisTime - CLICK_DELAY) < 250) {

            CLICK_DELAY = -1;
            result = true;

            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK |PowerManager.ACQUIRE_CAUSES_WAKEUP, getPackageName()+":double_tap");
            wl.acquire(500L);
            wl.release();
        }
        else {
            CLICK_DELAY = thisTime;
            result = false;
        }

        return result;
    }
}
