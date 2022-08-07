package com.example.myloginapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        TextView LoginName = findViewById(R.id.loginname);

        LoginName.setText("Welcome"+"\t"+getIntent().getStringExtra("UserName"));
    }
}