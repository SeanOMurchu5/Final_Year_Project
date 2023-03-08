package com.example.finalyearproject;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class addProduct extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    String productName, productPrice, productDescription, sellerAddress;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        mAuth = FirebaseAuth.getInstance();
        EditText productNameET = findViewById(R.id.ETproductName);
        EditText productPriceET = findViewById(R.id.ETproductPrice);
        EditText productDescriptionET = findViewById(R.id.ETproductDescription);
        EditText sellerAddressET = findViewById(R.id.ETsellerWallet);
        bottomNavigationView = findViewById(R.id.bottonnav);
        bottomNavigationView.setOnItemSelectedListener(item ->{
            switch (item.getItemId()) {
                case R.id.Home:
                    Intent intent = new Intent(addProduct.this, HomeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.Search:
                    intent = new Intent(addProduct.this, HomeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.Sell:
                    intent = new Intent(addProduct.this, addProduct.class);
                    startActivity(intent);
                    break;
                case R.id.Account:
                    intent = new Intent(addProduct.this, AccountActivity.class);
                    startActivity(intent);
                    break;
                case R.id.Payment:
                    intent = new Intent(addProduct.this, PaymentTaker.class);
                    startActivity(intent);
                    break;
            }
            return true;
        });

        Button enterBTN = findViewById(R.id.enterBTN);
        enterBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productName = productNameET.getText().toString();
                productPrice = productPriceET.getText().toString();
                double pp = Double.valueOf(productPrice);
                productDescription = productDescriptionET.getText().toString();
                sellerAddress = sellerAddressET.getText().toString();
                DatabaseReference firebaseUsers = FirebaseDatabase.getInstance().getReference();
                String uniqueId = UUID.randomUUID().toString();


                Product prod = new Product(productName,pp,productDescription,sellerAddress,false);

                firebaseUsers.child("Products").child(uniqueId).setValue(prod).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("MESSAGE", "Success at database task creation");
                        Toast.makeText(getApplicationContext(), "Created new product", Toast.LENGTH_LONG).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MESSAGE", "Failed at database task creation");
                    }
                });
            }
        });
    }
}