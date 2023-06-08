package com.example.finalyearproject.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finalyearproject.Adapter.CategoryAdapter;
import com.example.finalyearproject.Adapter.PopularAdapter;
import com.example.finalyearproject.Adapter.ProductAdapter;
import com.example.finalyearproject.Domain.CategoryDomain;
import com.example.finalyearproject.Domain.ProductDomain;
import com.example.finalyearproject.Interface.OnDataReceiveCallback;
import com.example.finalyearproject.R;
import com.example.finalyearproject.addProduct;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
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
    ArrayList<String> productList;
    ArrayList<ProductDomain> popularList;
    TextView searchView;
    int counter;
    Dialog dialog;

    ImageView homeBtn,addProductBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        userID = mUser.getUid();
        fireDB = FirebaseDatabase.getInstance().getReference("Products");

        setContentView(R.layout.activity_main);
        popularList = new ArrayList<>();
        productList = new ArrayList<>();
        searchView = findViewById(R.id.searchView);
        getAllProducts();

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             dialog = new Dialog(MainActivity.this);
             dialog.setContentView(R.layout.dialog_searchable_spinner);

             dialog.getWindow().setLayout(900,1000);
             dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
             dialog.show();

             EditText editText = dialog.findViewById(R.id.edit_Text);
             ListView listView = dialog.findViewById(R.id.list_view);

             ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,productList);
             listView.setAdapter(adapter);
             listView.setBackgroundColor(Color.BLACK);

             editText.addTextChangedListener(new TextWatcher() {
                 @Override
                 public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                 }

                 @Override
                 public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                   adapter.getFilter().filter(charSequence);

                 }

                 @Override
                 public void afterTextChanged(Editable editable) {

                 }
             });
             listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                 @Override
                 public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                     String value = adapter.getItem(i);
                     StartProductActivity(value);
                     dialog.dismiss();


                 }
             });
            }
        });


        counter = 0;


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
        category.add(new CategoryDomain("Collectibles","cat5"));
        category.add(new CategoryDomain("Accessories","cat_4"));

        adapter = new CategoryAdapter(category);
        recyclerViewCategoryList.setAdapter(adapter);

    }
    private void bottomNavigation(){
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout addBtn = findViewById(R.id.addBtn);
        LinearLayout editBtn = findViewById(R.id.editBtn);

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
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ProfileDetails.class));

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

        adapter2 = new PopularAdapter(popularList);
        recyclerViewPopularList.setAdapter(adapter2);
        getFromFirebase(new OnDataReceiveCallback(){
            @Override
            public void onDataReceived(ArrayList list) {
                Log.d("CREATION", "received data ");

                popularList = list;

                adapter2 = new ProductAdapter(popularList);

                recyclerViewPopularList.setAdapter(adapter2);
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

                if(counter <= 5) {
                    list.add(productDomain);
                    counter++;
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

    public void getAllProducts(){
        fireDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ProductDomain productDomain = snapshot.getValue(ProductDomain.class);
                productList.add(productDomain.getTitle());
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

    public void StartProductActivity(String i){
        Log.v(TAG, "inside startproductactivity : "+i);

        fireDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ProductDomain productDomain = snapshot.getValue(ProductDomain.class);

                if(productDomain.getTitle().equalsIgnoreCase(i)){
                    Intent intent = new Intent(MainActivity.this, showDetailActivity.class);
                    intent.putExtra("object",productDomain);
                    startActivity(intent);

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