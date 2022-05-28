package com.ansh.powerbuttonactions;



import android.app.Activity;
import android.app.Dialog;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;



public class Dashboard extends Activity {

    public static final String MESSAGE = "Message";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SWITCH1 = "switch1";
    public static final String SWITCH2 = "switch2";
    private Switch switch1;
    private Switch switch2;
    private Button btnPowerMenu;
    private Button btnLockScreen;
    private Button btnVolumeUp;
    private Button btnVolumeDown;
    private Boolean switch1CheckedState ;
    private Boolean switch2CheckedState ;
    private ImageView infoButton ;

            // Action Names values
            public static final String ShowPowerMenu = "showPowerMenu";
            public static final String LockScreen = "lockScreen";



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Button Declarations

            btnPowerMenu = findViewById(R.id.open_power_menu);
            btnLockScreen = findViewById(R.id.btn_lock_screen);
            btnVolumeUp = findViewById(R.id.btn_volume_up);
            btnVolumeDown = findViewById(R.id.btn_volume_down);
            switch1 = findViewById(R.id.switch1);
            switch2 = findViewById(R.id.switch2);
            infoButton = findViewById(R.id.btn_info);


        btnPowerMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, PowerMenuService.class);
                intent.putExtra(MESSAGE, ShowPowerMenu);
                startService(intent);
            }
        });
        btnLockScreen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, PowerMenuService.class);
                intent.putExtra(MESSAGE, LockScreen);
                startService(intent);

            }
        });
        btnVolumeUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              simulateKey(24);

            }
        });
        btnVolumeDown.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
           simulateKey(25);

            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog();

            }
        });

        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 if (isChecked){
                    PowerMenuService.volumeDownButtonScreenOff= true;
               }
                else{
                    PowerMenuService.volumeDownButtonScreenOff = false;
                }
            }
        });
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    PowerMenuService.volumeUpButtonScreenOff= true;
                }
                else{
                    PowerMenuService.volumeUpButtonScreenOff = false;
                }
            }
        });

    }
    public static void simulateKey(final int KeyCode) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyCode);
                } catch (Exception e) {
                    Log.e("Exception when sendKeyDownUpSync", e.toString());
                }
            }

        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       saveData();

    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
        updateViews();
    }
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SWITCH1,switch1.isChecked());
        editor.putBoolean(SWITCH2,switch2.isChecked());
        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        switch1CheckedState = sharedPreferences.getBoolean(SWITCH1,false);
        switch2CheckedState = sharedPreferences.getBoolean(SWITCH2,false);
    }
    public void updateViews(){
        switch1.setChecked(switch1CheckedState);
        switch2.setChecked(switch2CheckedState);
    }
    private void showDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.show();

         ImageView telegramLink = dialog.findViewById(R.id.iv_telegramlink);
        telegramLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse("https://t.me/+fhHQ4duXQC4yNmY1");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        ImageView closeButton = dialog.findViewById(R.id.iv_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               dialog.dismiss();
            }
        });
    }
}