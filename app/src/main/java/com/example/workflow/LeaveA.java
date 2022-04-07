package com.example.workflow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class LeaveA extends AppCompatActivity {
    TextView tv1,tv2;
    DatabaseReference dbRoot= FirebaseDatabase.getInstance().getReference();
    String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
    String department,pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave2);
//        Bundle b=getIntent().getExtras();
//        pos = getIntent().getStringExtra("pos");
//        dep=b.getString("dep");
        tv1=findViewById(R.id.textView);
        tv2=findViewById(R.id.textView4);
        dbRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
//                    count = snapshot.child("count").getValue(Integer.class);
                    pos=snapshot.child("Users").child(uid).child("position").getValue(String.class);
                    department=snapshot.child("Users").child(uid).child("department").getValue(String.class);
//                    department=snapshot.child("Users").child(uid).child("department").getValue(String.class);
                    if(pos.equals("Admin")){
                        tv2.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LeaveActivity.class);
                intent.putExtra("pos",pos);
                intent.putExtra("dep",department);
                startActivity(intent);
                finish();
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LeaveRequests.class);
                intent.putExtra("pos",pos);
                intent.putExtra("dep",department);
                startActivity(intent);
            }
        });
    }
}