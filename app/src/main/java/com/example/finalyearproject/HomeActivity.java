package com.example.finalyearproject;

import static android.widget.Toast.LENGTH_LONG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private MyAdapter mAdapter;
    RecyclerView mRecyclerView;
    ArrayList<Product> myDataset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference("Products");

        mRecyclerView = (RecyclerView) findViewById(R.id.productRecycler);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        fireDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                    Product productObj = taskSnapshot.getValue(Product.class);

                        myDataset.add(productObj);
                        mAdapter.notifyItemInserted(myDataset.size());


                    }

                }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.v("DBError","Cancel Access DB");
            }
        });





        bottomNavigationView = findViewById(R.id.bottonnav);
        bottomNavigationView.setOnItemSelectedListener(item ->{
            switch (item.getItemId()) {
                case R.id.Home:
                    Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.Search:
                    intent = new Intent(HomeActivity.this, HomeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.Sell:
                    intent = new Intent(HomeActivity.this, addProduct.class);
                    startActivity(intent);
                    break;
                case R.id.Account:
                    intent = new Intent(HomeActivity.this, AccountActivity.class);
                    startActivity(intent);
                    break;
                case R.id.Payment:
                    intent = new Intent(HomeActivity.this, PaymentTaker.class);
                    startActivity(intent);
                    break;
            }
            return true;
        });

    }


}