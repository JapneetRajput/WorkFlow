package com.example.workflow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {
    TextView e_id,fname,lname,em,user;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        e_id=findViewById(R.id.id);
        fname=findViewById(R.id.fname);
        lname=findViewById(R.id.lname);
        em=findViewById(R.id.em);
        user=findViewById(R.id.user);
        button=findViewById(R.id.button);
        Intent intent=getIntent();
        String username=intent.getStringExtra(HomeActivity.Extra_UNAME);
        DbHandler db=new DbHandler(this);
        user.setText(username);
        fname.setText(db.ret_Fname(username));
        lname.setText(db.ret_Lname(username));
        e_id.setText(String.valueOf(db.ret_id(username)));
        em.setText(db.ret_em(username));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Profile.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
}