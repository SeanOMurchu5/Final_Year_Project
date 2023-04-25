package com.example.finalyearproject.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.finalyearproject.Adapter.CategoryAdapter;
import com.example.finalyearproject.Adapter.PopularAdapter;
import com.example.finalyearproject.Domain.CategoryDomain;
import com.example.finalyearproject.Domain.ProductDomain;
import com.example.finalyearproject.HomeActivity;
import com.example.finalyearproject.Product;
import com.example.finalyearproject.R;
import com.example.finalyearproject.addProduct;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter,adapter2;
    private RecyclerView recyclerViewCategoryList,recyclerViewPopularList;
    DatabaseReference fireDB;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String userID;
    ArrayList<ProductDomain> productList;


    ImageView homeBtn,addProductBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        userID = mUser.getUid();
        setContentView(R.layout.activity_main);
        ArrayList<ProductDomain> list = new ArrayList<>();
        int pid = android.os.Process.myPid();
        String whiteList = "logcat -P '" + pid + "'";
        try {
            Runtime.getRuntime().exec(whiteList).waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        recyclerViewCategory();
        recyclerViewPopular();
        bottomNavigation();
    }

            private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategoryList = findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<CategoryDomain> category = new ArrayList<>();
        category.add(new CategoryDomain("Shoes","cat_1"));
        category.add(new CategoryDomain("Apparel","cat_2"));
        category.add(new CategoryDomain("Electronics","cat_3"));
        category.add(new CategoryDomain("Collectibles","cat_4"));
        category.add(new CategoryDomain("Accessories","cat_5"));

        adapter = new CategoryAdapter(category);
        recyclerViewCategoryList.setAdapter(adapter);

    }
    private void bottomNavigation(){
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout addBtn = findViewById(R.id.addBtn);
        LinearLayout profileBtn = findViewById(R.id.profileBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CartListActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainActivity.class));
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,addProduct.class));

            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));

            }
        });

    }

    private void recyclerViewPopular(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPopularList = findViewById(R.id.popularRecyclerView);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        ArrayList<ProductDomain> popularList = new ArrayList<>();
        popularList.add(new ProductDomain("Jordan 1","jordan1","The Jordan 1 a timeless classic",160,"0x86F0dA0BBB441902eB16f1f7B8021eAA0f2E2740",false,userID));
        popularList.add(new ProductDomain("Nike Airforce 1","airforce1","The Airforce 1 the complete shoe",90,"selleraddress",false,userID));
        popularList.add(new ProductDomain("Adidas Superstars","superstar","The Adidas Superstars an anytime shoe",120,"selleraddress",false,userID));
        popularList.add(new ProductDomain("Yeezy Boost 350 V2","yeezy350v2","The Yeezy Boost 350 V2 a gamechanger",300,"selleraddress",false,userID));
//        category.add(new ProductDomain("Air Jordan XI","jordan1bred","The Air Jordan XI , a ground breaking design",200));
         adapter2 = new PopularAdapter(popularList);
         recyclerViewPopularList.setAdapter(adapter2);
    }


}