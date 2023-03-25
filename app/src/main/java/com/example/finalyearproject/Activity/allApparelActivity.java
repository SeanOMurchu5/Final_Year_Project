package com.example.finalyearproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.finalyearproject.Adapter.ProductAdapter;
import com.example.finalyearproject.Product;
import com.example.finalyearproject.R;

import java.util.ArrayList;

public class allApparelActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView apparelRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_apparel);
        recyclerViewApparel();
    }

    private void recyclerViewApparel() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        apparelRecyclerView = findViewById(R.id.apparelRecyclerView);
        apparelRecyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Adidas Hoodie",200,"description","sellerAddress",false,"1","adidashoodie"));
        products.add(new Product("Adidas T-Shirt",200,"description","sellerAddress",false,"1","adidastee"));
        products.add(new Product("Nike T-Shirt",200,"description","sellerAddress",false,"1","niketee"));


        adapter = new ProductAdapter(products);
        apparelRecyclerView.setAdapter(adapter);
    }
}