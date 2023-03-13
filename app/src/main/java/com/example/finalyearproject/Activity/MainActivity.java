package com.example.finalyearproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.finalyearproject.Adapter.CategoryAdapter;
import com.example.finalyearproject.Adapter.PopularAdapter;
import com.example.finalyearproject.Domain.CategoryDomain;
import com.example.finalyearproject.Domain.ProductDomain;
import com.example.finalyearproject.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter,adapter2;
    private RecyclerView recyclerViewCategoryList,recyclerViewPopularList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewCategory();
        recylcerViewPopular();
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

    private void recylcerViewPopular(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPopularList = findViewById(R.id.popularRecyclerView);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        ArrayList<ProductDomain> popularList = new ArrayList<>();
        popularList.add(new ProductDomain("Jordan 1","jordan1","The Jordan 1 a timeless classic",160));
        popularList.add(new ProductDomain("Nike Airforce 1","airforce1","The Airforce 1 the complete shoe",90));
        popularList.add(new ProductDomain("Adidas Superstars","superstar","The Adidas Superstars an anytime shoe",120));
        popularList.add(new ProductDomain("Yeezy Boost 350 V2","yeezy350v2","The Yeezy Boost 350 V2 a gamechanger",300));
//        category.add(new ProductDomain("Air Jordan XI","jordan1bred","The Air Jordan XI , a ground breaking design",200));

         adapter2 = new PopularAdapter(popularList);
         recyclerViewPopularList.setAdapter(adapter2);
    }
}