package com.example.finalyearproject;

import static android.widget.Toast.LENGTH_LONG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        String email = null;
        String password = null;

        Button LoginInBTN = findViewById(R.id.loginButton);
        LoginInBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>(){
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task){
                                if(task.isSuccessful()){

                                    Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast toast = Toast.makeText(LoginActivity.this, "Login failed", LENGTH_LONG);
                                    toast.show();
                                }
                            }
                        });

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        Button SignUpBTN = findViewById(R.id.signUpBTN);
        SignUpBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);


            }
        });


    }


}