package com.example.workflow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    TextView e_id,fname,em,username,position;
    Button logout;
    FloatingActionButton addEmployee;
    FirebaseUser user;
    DatabaseReference usersReference;
    String pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();
        e_id=findViewById(R.id.id);
        fname=findViewById(R.id.fname);
        em=findViewById(R.id.em);
        username=findViewById(R.id.user);
        logout=findViewById(R.id.logout);
        position=findViewById(R.id.position);
        addEmployee=findViewById(R.id.add);

        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        usersReference = FirebaseDatabase.getInstance().getReference().child("Users");

        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String first_name = snapshot.child(uid).child("firstName").getValue(String.class);
                String last_name = snapshot.child(uid).child("lastName").getValue(String.class);
                String Email = snapshot.child(uid).child("email").getValue(String.class);
                String userName = snapshot.child(uid).child("username").getValue(String.class);
                pos = snapshot.child(uid).child("position").getValue(String.class);
                if(pos.equals("Admin")){
                    addEmployee.setVisibility(View.VISIBLE);
                }
                fname.setText(first_name + " " + last_name);
                em.setText(Email);
                username.setText(userName);
                position.setText(pos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(ProfileActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
                finish();
            }
        });
        addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,RegisterActivity.class));
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ProfileActivity.this,HomeActivity.class));
        finish();
    }
}