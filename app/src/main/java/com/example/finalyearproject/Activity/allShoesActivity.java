package com.example.finalyearproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.finalyearproject.Adapter.ProductAdapter;
import com.example.finalyearproject.Product;
import com.example.finalyearproject.R;

import java.util.ArrayList;

public class allShoesActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView shoesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_shoes);
        recyclerViewShoes();

    }

    private void recyclerViewShoes() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        shoesRecyclerView = findViewById(R.id.shoesRecyclerView);
        shoesRecyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Jordan 1",200,"description","sellerAddress",false,"1","jordan1"));
        products.add(new Product("Airforce 1",200,"description","sellerAddress",false,"1","airforce1"));
        products.add(new Product("Adidas superstars",200,"description","sellerAddress",false,"1","superstar"));
        products.add(new Product("Yeezy 350 V2",200,"description","sellerAddress",false,"1","yeezy350v2"));
        products.add(new Product("Jordan 1",200,"description","sellerAddress",false,"1","jordan1"));


        adapter = new ProductAdapter(products);
        shoesRecyclerView.setAdapter(adapter);
    }
}