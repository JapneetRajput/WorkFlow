package com.example.workflow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText emailET,firstNameET,lastNameET, passwordET1, passwordET2,usernameET;
    TextView loginNow;
    Button registerBTN;

    DatabaseReference usersReference;

    FirebaseUser user;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        emailET= findViewById(R.id.email);
        firstNameET=findViewById(R.id.firstName);
        lastNameET=findViewById(R.id.lastName);
        usernameET=findViewById(R.id.username);
        passwordET1=findViewById(R.id.password1);
        passwordET2=findViewById(R.id.password2);
        registerBTN=findViewById(R.id.register);
        loginNow=findViewById(R.id.loginNow);

        auth = FirebaseAuth.getInstance();

        usersReference = FirebaseDatabase.getInstance().getReference().child("Users");
        loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = emailET.getText().toString();
                String txt_lastName = lastNameET.getText().toString();
                String txt_firstName = firstNameET.getText().toString();
                String txt_username = usernameET.getText().toString();
                String txt_password1 = passwordET1.getText().toString();
                String txt_password2 = passwordET2.getText().toString();
                if(txt_lastName.isEmpty()||txt_username.isEmpty()||txt_firstName.isEmpty()||txt_email.isEmpty() || txt_password1.isEmpty() || txt_password2.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "All fields are mandatory!", Toast.LENGTH_SHORT).show();
                }
                else if(txt_password1.length()<6){
                    Toast.makeText(RegisterActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (txt_password1.equals(txt_password2)) {
                        registerUser(txt_firstName, txt_lastName,txt_username,txt_email,txt_password1);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void registerUser(String FirstName, String LastName,String Username,String Email, String password) {
        auth.createUserWithEmailAndPassword(Email,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    createDatabaseValues(FirstName,LastName,Username,Email);
                    startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createDatabaseValues(String FirstName, String LastName, String Username, String Email) {
        user = auth.getCurrentUser();

        dataExtract userr = new dataExtract(FirstName,LastName,Username,Email);
        usersReference.child(user.getUid()).setValue(userr);
    }

}