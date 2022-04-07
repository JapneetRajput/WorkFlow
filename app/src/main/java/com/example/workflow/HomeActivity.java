package com.example.workflow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.Lottie;
import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {
    TextView projectsTV,profileTV,leaveTV,noticeBoardTV,employeeListTV,attendanceTV;
    LottieAnimationView profile,leave,noticeBoard,projects;
    DatabaseReference dbRoot= FirebaseDatabase.getInstance().getReference();
    String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
    String department,pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        leave = findViewById(R.id.leave);
        profile = findViewById(R.id.profile);
        noticeBoard = findViewById(R.id.notice);
        projects = findViewById(R.id.projects);
        leaveTV = findViewById(R.id.leave_text);
        profileTV = findViewById(R.id.profile_text);
        noticeBoardTV = findViewById(R.id.notice_text);
        projectsTV = findViewById(R.id.ongoing_text);
        employeeListTV = findViewById(R.id.employeeList);
        attendanceTV = findViewById(R.id.attendance);
        dbRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
//                    count = snapshot.child("count").getValue(Integer.class);
                    pos=snapshot.child("Users").child(uid).child("position").getValue(String.class);
                    department=snapshot.child("Users").child(uid).child("department").getValue(String.class);
//                    department=snapshot.child("Users").child(uid).child("department").getValue(String.class);
//                    if(pos.equals("Admin")){
//                        OpenDialog.setVisibility(View.VISIBLE);
//                    }
                    if(pos.equals("Admin")){
                        employeeListTV.setVisibility(View.VISIBLE);
                    }
                    else{
                        employeeListTV.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        attendanceTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Attendance.class));
                finish();
            }
        });
        employeeListTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),EmployeeList.class));
                finish();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                finish();
            }
        });
        projects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ProjectActivity.class));
                finish();
            }
        });
        profileTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                finish();
            }
        });
        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,LeaveA.class);
                intent.putExtra("pos",pos);
                intent.putExtra("dep",department);
                startActivity(intent);
                finish();
            }
        });
        leaveTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LeaveA.class);
                intent.putExtra("pos",pos);
                intent.putExtra("dep",department);
                startActivity(intent);
                finish();
            }
        });
        noticeBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NoticeActivity.class));
                finish();
            }
        });
        noticeBoardTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NoticeActivity.class));
                finish();
            }
        });
        projectsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ProjectActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(HomeActivity.this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No",null)
                .show();
    }
}