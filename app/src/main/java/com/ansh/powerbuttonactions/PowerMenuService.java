package com.ansh.powerbuttonactions;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

public class PowerMenuService extends AccessibilityService {

    public static boolean volumeDownButtonScreenOff = false ;
    public static boolean volumeUpButtonScreenOff = false ;


    private void Action(String actionName) {
        if(actionName.equals("showPowerMenu"))
            performGlobalAction(6);
        else if(actionName.equals("lockScreen"))
            performGlobalAction(8);
    }
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
    }

    public void onInterrupt() {
    }

    public int onStartCommand(Intent intent, int i, int i2) {

        String actionName=intent.getStringExtra(Dashboard.MESSAGE);
        Action(actionName);
        return START_REDELIVER_INTENT;
    }
    @Override
    protected boolean onKeyEvent(KeyEvent event) {

        int action = event.getAction();
        int keyCode = event.getKeyCode();
        // the service listens for both pressing and releasing the key
        // so the below code executes twice, i.e. you would encounter two Toasts
        // in order to avoid this, we wrap the code inside an if statement
        // which executes only when the key is released
        if (action == KeyEvent.ACTION_UP && volumeDownButtonScreenOff)
            if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                performGlobalAction(8);
            }
        if (action == KeyEvent.ACTION_UP && volumeUpButtonScreenOff) {
             if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
                performGlobalAction(8);
            }
        }
        return super.onKeyEvent(event);

    }

}
