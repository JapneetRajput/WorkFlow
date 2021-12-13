package com.example.workflow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    public static final String Extra_UNAME="com.example.workflow.extra.UNAME";
    TextView textView,profile,leave,noticeBoard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textView = findViewById(R.id.textView3);
        leave = findViewById(R.id.leave);
        profile = findViewById(R.id.profile);
        noticeBoard = findViewById(R.id.notice);
        Intent intent=getIntent();
        String s=intent.getStringExtra(MainActivity.Extra_NAME);
        DbHandler db=new DbHandler(this);
        StringBuffer fnm=new StringBuffer(db.ret_Fname(s));
        textView.setText("Welcome "+fnm.toString());
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Profile.class);
                intent.putExtra(Extra_UNAME,s);
                startActivity(intent);
            }
        });
        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Leave.class);
                startActivity(intent);
            }
        });
        noticeBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Notice.class);
                startActivity(intent);
            }
        });
    }
}