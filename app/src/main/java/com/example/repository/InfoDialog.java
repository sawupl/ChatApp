package com.example.repository;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

public class InfoDialog extends Dialog {

        public Activity activity;
        Button exit;

        public InfoDialog(Activity a) {
                super(a);
                this.activity = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                setContentView(R.layout.dialog_info);
                exit=findViewById(R.id.exitDialog);
                exit.setOnClickListener(view -> {
                        dismiss();
                });
        }
}