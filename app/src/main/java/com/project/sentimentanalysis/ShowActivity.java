package com.project.sentimentanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity {

    TextView txt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        txt2 = (TextView) findViewById(R.id.text1);

        String id = getIntent().getStringExtra("sentiment");

        txt2.setText(id);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}