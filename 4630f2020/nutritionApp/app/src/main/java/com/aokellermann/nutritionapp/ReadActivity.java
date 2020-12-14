package com.aokellermann.nutritionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ReadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        TextView details = (TextView) findViewById(R.id.foodDetails);
        Intent intent = getIntent();
        details.setText(intent.getStringExtra("INFO"));
    }
}