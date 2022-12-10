package com.example.repository.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.repository.screens.dialog.InfoDialog;
import com.example.repository.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onMyButtonClick(View view) {
        InfoDialog dfp=new InfoDialog(this);
        dfp.show();
    }
}