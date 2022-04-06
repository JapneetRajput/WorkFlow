package com.example.workflow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Map;

public class LeaveA extends AppCompatActivity {
    TextView tv1,tv2;
    String pos,dep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave2);
        Bundle b=getIntent().getExtras();
        pos=b.getString("pos");
        dep=b.getString("dep");
        tv1=findViewById(R.id.textView);
        tv2=findViewById(R.id.textView4);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LeaveActivity.class);
                intent.putExtra("pos",pos);
                intent.putExtra("dep",dep);
                startActivity(intent);
                finish();
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LeaveRequests.class);
                intent.putExtra("pos",pos);
                intent.putExtra("dep",dep);
                startActivity(intent);
            }
        });
    }
}