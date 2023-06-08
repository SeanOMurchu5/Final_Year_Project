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

import com.example.finalyearproject.Activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    String email;
    String name;
    String address;
    String ethAddress;
    String credentials;
    String dob;
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
        EditText addressET = findViewById(R.id.ETAddress);
        EditText ethAdressET = findViewById(R.id.ETaddressETH);
        EditText credentialsET = findViewById(R.id.credentialsET);

        DatabaseReference firebaseUsers = FirebaseDatabase.getInstance().getReference();


        Button enterBTN = findViewById(R.id.SignUpPageBTN);
        enterBTN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                email = emailET.getText().toString();
                password = passwordET.getText().toString();
                password2 = passwordET2.getText().toString();
                address = addressET.getText().toString();
                ethAddress = ethAdressET.getText().toString();
                credentials = credentialsET.getText().toString();


                if(password.equals(password2)){
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        mUser = mAuth.getCurrentUser();
                                        Log.d("MESSAGE","email is "+email);
                                        Log.d("MESSAGE","password is "+password);
                                        String userID= mUser.getUid();
                                        User user = new User(name,email,address,ethAddress,userID,credentials);

                                        firebaseUsers.child("Users").child(userID).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Log.d("MESSAGE","Success at database user creation");

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {

                                            @Override
                                            public void onFailure(@NonNull Exception e){
                                                Log.d("MESSAGE","Failed at database user creation");
                                            }
                                        });
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);

                                    }else {

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