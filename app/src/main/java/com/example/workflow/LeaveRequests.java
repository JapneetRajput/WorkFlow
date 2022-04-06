package com.example.workflow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LeaveRequests extends AppCompatActivity {
    String pos,dep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_requests);
        Bundle b=getIntent().getExtras();
        pos=b.getString("pos");
        dep=b.getString("dep");
    }
}