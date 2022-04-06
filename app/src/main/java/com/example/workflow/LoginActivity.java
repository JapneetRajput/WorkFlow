package com.example.workflow;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.scwang.wave.MultiWaveHeader;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText emailET,passwordET;
    Button login;

    FirebaseAuth auth;
    Window window;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //STATUS BAR COLOR:
        if (Build.VERSION.SDK_INT >= 21) {
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }

        MultiWaveHeader waveFooter;
        waveFooter = findViewById(R.id.wave_footer);
//        waveFooter = findViewById(R.id.wave_footer);

        waveFooter.setVelocity(2);
        waveFooter.setProgress(1);
        waveFooter.isRunning();
        waveFooter.setGradientAngle(45);
        waveFooter.setStartColor(Color.parseColor("#E0F1F8"));
        waveFooter.setCloseColor(Color.parseColor("#A569BD"));

//        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        getSupportActionBar().hide();
        login=findViewById(R.id.login);
        emailET=findViewById(R.id.email);
        passwordET=findViewById(R.id.password);

        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = emailET.getText().toString();
                String txt_password = passwordET.getText().toString();
                if(txt_email.isEmpty() || txt_password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                }
                else {
                    loginUser(txt_email, txt_password);
                }
            }
        });
    }
    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Credentials don't match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            finish();
        }
    }
}