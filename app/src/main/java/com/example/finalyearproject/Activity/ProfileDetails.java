package com.example.finalyearproject.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.finalyearproject.R;
import com.example.finalyearproject.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileDetails extends AppCompatActivity {
    DatabaseReference fireDB;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    EditText name, address, payment, password, email;
    Button submitBtn;
    private String userID;
    User realUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        fireDB = FirebaseDatabase.getInstance().getReference("users");

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        assert mUser != null;
        userID = mUser.getUid();

        name = findViewById(R.id.profileNameET);
        address = findViewById(R.id.addressET);
        email = findViewById(R.id.emailET);
        payment = findViewById(R.id.paymentET);
        password = findViewById(R.id.passwordET);
        submitBtn = findViewById(R.id.submitBtn);

        getUser();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!password.getText().equals("")){
                    mUser.updatePassword(String.valueOf(password));
                }
                fireDB.child(userID).setValue(realUser);

            }
        });

    }

    public void getUser(){

        fireDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                if(user.getUniqueId().equalsIgnoreCase(userID)){
                    realUser = user;
                    name.setText(user.getName());
                    address.setText(user.getEthAddress());
                    email.setText(user.getEmail());


                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}