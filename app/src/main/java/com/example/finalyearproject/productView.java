package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class productView extends AppCompatActivity {
Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        Bundle extras = getIntent().getExtras();
        product = (Product) extras.get("Product");
        String prodPrice = String.valueOf(product.getProductPrice());

        TextView tvProductName = findViewById(R.id.productName);
        tvProductName.setText(product.getProductName());

        TextView tvProductPrice = findViewById(R.id.productPrice);
        tvProductPrice.setText(prodPrice);

        TextView tvProductDescription = findViewById(R.id.productDescription);
        tvProductPrice.setText(product.getDescription());
    }
}