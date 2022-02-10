package com.example.workflow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NoticeAdmin extends AppCompatActivity {

    TextInputEditText noticeTitle,noticeDesc;
    Button addNotice;
    FirebaseUser user;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_admin);
        getSupportActionBar().hide();
        noticeTitle=findViewById(R.id.noticeTitle);
        noticeDesc=findViewById(R.id.noticeDescription);
        addNotice=findViewById(R.id.addNotice);
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid=user.getUid();
        db = FirebaseFirestore.getInstance();
        addNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = noticeTitle.getText().toString();
                String desc = noticeDesc.getText().toString();
                Map<String,Object> notices = new HashMap<>();
                notices.put("title",title);
                notices.put("description",desc);

                db.collection("Notices").document(uid).set(notices).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(NoticeAdmin.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(NoticeAdmin.this,NoticeActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(NoticeAdmin.this, "Update failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}