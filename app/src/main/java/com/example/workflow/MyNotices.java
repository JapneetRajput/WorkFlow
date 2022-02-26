package com.example.workflow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyNotices extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notices);
        getSupportActionBar().hide();
        bottomNavigationView = findViewById(R.id.bottomNavigationViewMyNotices);
        bottomNavigationView.setSelectedItemId(R.id.myNotices);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.allNotices:
                    startActivity(new Intent(MyNotices.this,NoticeActivity.class));
                    finish();
                    break;
                case R.id.myNotices:
                    startActivity(new Intent(MyNotices.this, MyNotices.class));
                    finish();
                    break;
                case R.id.starred:
                    startActivity(new Intent(MyNotices.this,Starred.class));
                    finish();
                    break;
            }

            return true;
        });
    }
}