package com.example.finalyearproject.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.finalyearproject.Adapter.ProductAdapter;
import com.example.finalyearproject.Domain.ProductDomain;
import com.example.finalyearproject.Interface.OnDataReceiveCallback;
import com.example.finalyearproject.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class allElectronicsActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView electronicsRecycler;
    DatabaseReference fireDB;
    ArrayList<ProductDomain> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_electronics);
        fireDB = FirebaseDatabase.getInstance().getReference("Products");

        recyclerViewElectronics();
    }

    private void recyclerViewElectronics() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        electronicsRecycler = findViewById(R.id.electronicsRV);
        electronicsRecycler.setLayoutManager(linearLayoutManager);

        products = new ArrayList<>();
        adapter = new ProductAdapter(products);
        electronicsRecycler.setAdapter(adapter);
        getFromFirebase(new OnDataReceiveCallback(){
            @Override
            public void onDataReceived(ArrayList list) {
                Log.d("CREATION", "received data ");

                products = list;

                adapter = new ProductAdapter(products);

                electronicsRecycler.setAdapter(adapter);
            }

            @Override
            public void onDataReceived(int n) {

            }

        });

    }
    private void getFromFirebase(OnDataReceiveCallback callback){
        Log.d("CREATION", "get from firebase ");
        ArrayList<ProductDomain> list = new ArrayList<>();
        fireDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ProductDomain productDomain = snapshot.getValue(ProductDomain.class);
                Log.v(TAG, "inside purchase activity firebase fetch , product : "+productDomain.getUniqueId());

                if(productDomain.getCategory().equalsIgnoreCase("Electronics")) {
                    list.add(productDomain);
                    callback.onDataReceived(list);
                }


            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}