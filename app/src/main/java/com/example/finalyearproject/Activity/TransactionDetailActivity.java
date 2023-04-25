package com.example.finalyearproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalyearproject.Adapter.ProductAdapter;
import com.example.finalyearproject.Domain.ProductDomain;
import com.example.finalyearproject.R;
import com.example.finalyearproject.Transaction;

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
        String picurl = productDomain.getPic();
        int drawableResourceId = this.getResources().getIdentifier(picurl,"drawable",this.getPackageName());
        pic.setImageResource(drawableResourceId);




    }
}