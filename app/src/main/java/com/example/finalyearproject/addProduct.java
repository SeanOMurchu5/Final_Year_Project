package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

public class addProduct extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    String productName, productPrice, productDescription, sellerAddress;
    BottomNavigationView bottomNavigationView;
    private ImageView imageView;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage;
    StorageReference storageReference;
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

        Button chooseImageBTN = findViewById(R.id.chooseImageBTN);
        imageView = (ImageView) findViewById(R.id.imageView);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        chooseImageBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
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


                Product prod = new Product(productName,pp,productDescription,sellerAddress,false,uniqueId);
                uploadImage(uniqueId);

                firebaseUsers.child("Products").child(uniqueId).setValue(prod).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("MESSAGE", "Success at database product creation");
                        Toast.makeText(getApplicationContext(), "Created new product", Toast.LENGTH_LONG).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MESSAGE", "Failed at database product creation");
                    }
                });
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage(String uniqueID) {
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = storageReference.child("images/"+ uniqueID);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(addProduct.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(addProduct.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
}