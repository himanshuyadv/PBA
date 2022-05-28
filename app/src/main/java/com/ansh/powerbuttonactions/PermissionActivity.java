package com.ansh.powerbuttonactions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class PermissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        getSupportActionBar().hide();


        final MaterialButton btnEnablePermissions = findViewById(R.id.btnEnablePermission);
        final MaterialButton quit = findViewById(R.id.quit_permission_activity);
        final TextView tvAskForPermissions = findViewById(R.id.textView);

        CheckBox check = findViewById(R.id.permission_checkbox);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    btnEnablePermissions.setVisibility(View.VISIBLE);
                    quit.setVisibility(View.GONE);
                }
                if(!isChecked){
                    quit.setVisibility(View.VISIBLE);
                    btnEnablePermissions.setVisibility(View.GONE);

                }else{
                    quit.setVisibility(View.GONE);
                }

            }
        });

        btnEnablePermissions.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
                finish();
            }
        });
        quit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}