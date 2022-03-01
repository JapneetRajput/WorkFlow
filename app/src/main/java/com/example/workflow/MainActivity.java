package com.example.workflow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView titlee,descriptionn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        titlee=findViewById(R.id.titlee);
        descriptionn=findViewById(R.id.descriptionn);

        String title = "Sample title";
        String description = "Sample description";
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            title = extras.getString("title");
            description=extras.getString("description");
        }
        titlee.setText(title);
        descriptionn.setText(description);
    }
}