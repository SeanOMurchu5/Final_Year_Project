package com.example.finalyearproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.Adapter.ProductAdapter;
import com.example.finalyearproject.Domain.ProductDomain;
import com.example.finalyearproject.R;
import com.example.finalyearproject.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class TransactionDetailActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProductAdapter adapter;
    TextView Cost,receiver, senderAddress,title,description;
    ImageView pic;
    private ArrayList list;
    Transaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);
        list = new ArrayList<ProductDomain>();

        Cost = findViewById(R.id.totalTV);
        receiver = findViewById(R.id.receivertv);
        senderAddress = findViewById(R.id.senderAddresstv);
        title = findViewById(R.id.titletext);
        description = findViewById(R.id.descriptiontext);
        pic = findViewById(R.id.productImageView);
        getBundle();
    }

    private void getBundle() {
        transaction= (Transaction) getIntent().getSerializableExtra("transaction");


        Cost.setText(String.valueOf(transaction.getAmount()));
        receiver.setText(String.valueOf(transaction.getReceiverAddress()));
        senderAddress.setText(transaction.getSenderAddress());
        ProductDomain productDomain = transaction.getProduct();
        title.setText(productDomain.getTitle());
        description.setText(productDomain.getDescription());
        StorageReference ref = FirebaseStorage.getInstance().getReference("images/"+productDomain.getUniqueId()+".jpg");

        //int drawableResourceId = this.getResources().getIdentifier(object.getPic(),"drawable",this.getPackageName());
        Glide.with(this)
                .load(ref)
                .into(pic);




    }
}