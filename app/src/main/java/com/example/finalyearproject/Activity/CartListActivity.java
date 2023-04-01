package com.example.finalyearproject.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.finalyearproject.Adapter.CartListAdapter;
import com.example.finalyearproject.Domain.ProductDomain;
import com.example.finalyearproject.Helper.ManagementCart;
import com.example.finalyearproject.Interface.ChangeNumberItemsListener;
import com.example.finalyearproject.Interface.OnDataReceiveCallback;
import com.example.finalyearproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CartListActivity extends AppCompatActivity {
private RecyclerView.Adapter adapter;
private RecyclerView recyclerViewList;
private ManagementCart managementCart;
TextView totalFeetxt, taxTxt,deliveryTxt,totalTxt, emptyTxt;
private double tax;
private ScrollView scrollView;
    ArrayList<ProductDomain> result;

    ExecutorService es;
    DatabaseReference fireDB;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String userID;
    ArrayList<ProductDomain> cart;
    ExecutorService taskExecutor;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        assert mUser != null;
        userID = mUser.getUid();

        cart = new ArrayList<>();
        fireDB = FirebaseDatabase.getInstance().getReference("cart");
        ExecutorService es = Executors.newSingleThreadExecutor();

        //managementCart = new ManagementCart(this);
         result = null;
        initView();
        try {
            initList();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        calculateCart();
        bottomNavigation();

    }

    private void bottomNavigation(){
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this,CartListActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this,MainActivity.class));
            }
        });
    }
    private void initView() {
        recyclerViewList = findViewById(R.id.recyclerView);
        totalFeetxt = findViewById(R.id.totalFeeTv);
        taxTxt = findViewById(R.id.taxTxt);
        deliveryTxt = findViewById(R.id.deliveryTxt);
        totalTxt = findViewById(R.id.totalTxt);
        emptyTxt = findViewById(R.id.emptyTxt);
        scrollView = findViewById(R.id.scrollView3);
        recyclerViewList = findViewById(R.id.cartView);

    }

    private void initList() throws InterruptedException {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        Log.d("CREATION", "start of initList ");



        getFromFirebase(new OnDataReceiveCallback(){
            @Override
            public void onDataReceived(ArrayList<ProductDomain> list) {
                Log.d("CREATION", "received data ");

                adapter = new CartListAdapter(list, CartListActivity.this, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        Log.d("CREATION", "calc cart ");
                        calculateCart();
                    }
                });

                recyclerViewList.setAdapter(adapter);

                if(list.isEmpty()){
                    Log.d("CREATION","empty ");

                    emptyTxt.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.GONE);
                }else{
                    Log.d("CREATION","no empty ");

                    emptyTxt.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            }

        });


    };

    private void getFromFirebase(OnDataReceiveCallback callback){
        Log.d("CREATION", "get from firebase ");
        ArrayList<ProductDomain> list = new ArrayList<>();

        fireDB.child(userID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.v(TAG, "Created child " + snapshot.getKey());
                ProductDomain productDomain = snapshot.getValue(ProductDomain.class);
                list.add(productDomain);
                callback.onDataReceived(list);


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
                Log.v(TAG, "cancelled");

            }
        });

    }

    private void calculateCart(){
        double percentTax = 0.02;
        double delivery=10;

        tax= Math.round((getTotalFee(cart)*percentTax)*100)/100;
        double total = Math.round((getTotalFee(cart) + tax + delivery) * 100)/100;
        double itemTotal = Math.round(getTotalFee(cart)*100)/100;

        totalFeetxt.setText("$"+itemTotal);
        taxTxt.setText("$"+tax);
        deliveryTxt.setText("$"+delivery);
        totalTxt.setText("$"+itemTotal);
    }

    public Double getTotalFee(ArrayList<ProductDomain> list) {


        double fee =0;
        for(int i = 0;i< list.size();i++){
            fee = fee + (list.get(i).getFee() * list.get(i).getNumberInCart());
        }

        return fee;
    }
}

