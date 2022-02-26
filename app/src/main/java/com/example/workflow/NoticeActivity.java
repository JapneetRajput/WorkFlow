package com.example.workflow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class NoticeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterNotices adapterNotices;
    ArrayList<NoticeList> list;
    BottomNavigationView bottomNavigationView;
    FirebaseFirestore db;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        getSupportActionBar().hide();
        bottomNavigationView = findViewById(R.id.bottomNavigationViewNotices);
        bottomNavigationView.setSelectedItemId(R.id.allNotices);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching data");
        progressDialog.show();

        recyclerView=findViewById(R.id.noticesRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<NoticeList>();
        adapterNotices = new AdapterNotices(this,list);
        db=FirebaseFirestore.getInstance();

        recyclerView.setAdapter(adapterNotices);

        EventChangeListener();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.allNotices:
                    startActivity(new Intent(NoticeActivity.this,NoticeActivity.class));
                    finish();
                    break;
                case R.id.myNotices:
                    startActivity(new Intent(NoticeActivity.this, MyNotices.class));
                    finish();
                    break;
                case R.id.starred:
                    startActivity(new Intent(NoticeActivity.this,Starred.class));
                    finish();
                    break;
            }

            return true;
        });

    }

    private void EventChangeListener() {

        db.collection("Notices")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null){
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Toast.makeText(NoticeActivity.this, "Snapshot error", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            for(DocumentChange dc : value.getDocumentChanges()){

                                if(progressDialog.isShowing())
                                    progressDialog.dismiss();

                                if(dc.getType() == DocumentChange.Type.ADDED){
                                    list.add(dc.getDocument().toObject(NoticeList.class));
                                }

                                adapterNotices.notifyDataSetChanged();
                            }
                        }
                    }
                });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(NoticeActivity.this,HomeActivity.class));
        finish();
    }
}