package com.example.finalyearproject;

import static android.widget.Toast.LENGTH_LONG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    String email;
    String password;
    String password2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        EditText emailET = findViewById(R.id.editTextTextEmailAddress);
        EditText passwordET = findViewById(R.id.enterPasswordET);
        EditText passwordET2 = findViewById(R.id.enterPasswordET2);



        Button enterBTN = findViewById(R.id.SignUpPageBTN);
        enterBTN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                email = emailET.getText().toString();
                password = passwordET.getText().toString();
                password2 = passwordET2.getText().toString();

                if(password.equals(password2)){
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        mUser = mAuth.getCurrentUser();
                                        Log.d("MESSAGE","email is "+email);
                                        Log.d("MESSAGE","password is "+password);

                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        startActivity(intent);
                                    } else {

                                        Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Toast toast = Toast.makeText(SignUpActivity.this, "Sign up failed", LENGTH_LONG);
                    toast.show();
                }

            }
        });



    }
}