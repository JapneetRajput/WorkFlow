package com.example.workflow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String Extra_NAME="com.example.workflow.extra.NAME";
    Button login,register;
    EditText pass ;
    AutoCompleteTextView user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.login);
        user = findViewById(R.id.Username);
        pass = findViewById(R.id.editTextPass);
        register = findViewById(R.id.register);
        DbHandler db=new DbHandler(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username= user.getText().toString();
                String password= pass.getText().toString();
                if(username.equals("")||password.equals(""))
                    Toast.makeText(MainActivity.this, "* All fields are mandatory", Toast.LENGTH_SHORT).show();
                else{
                    if(db.checkusername(username))
                        Toast.makeText(MainActivity.this, "User doesn't exist! Please register", Toast.LENGTH_SHORT).show();
                    else if(db.checkuserpass(username,password)){
                        Toast.makeText(MainActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                        intent.putExtra(Extra_NAME,username);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}