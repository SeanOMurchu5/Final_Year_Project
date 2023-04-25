package com.example.finalyearproject.Activity;

import static android.content.ContentValues.TAG;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_LIMIT;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.hardware.SensorDirectChannel;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.finalyearproject.Adapter.CartListAdapter;
import com.example.finalyearproject.BankFactory;
import com.example.finalyearproject.Domain.ProductDomain;
import com.example.finalyearproject.Helper.ManagementCart;
import com.example.finalyearproject.Interface.ChangeNumberItemsListener;
import com.example.finalyearproject.Interface.OnDataReceiveCallback;
import com.example.finalyearproject.R;
import com.example.finalyearproject.Transaction;
import com.example.finalyearproject.User;
import com.example.finalyearproject.paymentActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.web3j.abi.datatypes.Int;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Convert;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
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
TextView totalFeetxt, taxTxt,deliveryTxt,totalTxt, emptyTxt, checkoutBtn;
private double tax;
private ScrollView scrollView;
    ArrayList<ProductDomain> result;

    ExecutorService es;
    DatabaseReference fireDB,productDB;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String userID;
    Credentials credentials;
    String credentialsString;
    Web3j web3;
    String bigint, receiverAddress, senderAddress;
    BigInteger gasPrice, gasLimit;
    BigDecimal bigDecimal;

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
        productDB= FirebaseDatabase.getInstance().getReference("Products");



        //temporary for testing


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
        es = Executors.newCachedThreadPool();

        recyclerViewList = findViewById(R.id.recyclerView);
        totalFeetxt = findViewById(R.id.totalFeeTv);
        taxTxt = findViewById(R.id.taxTxt);
        deliveryTxt = findViewById(R.id.deliveryTxt);
        totalTxt = findViewById(R.id.totalTxt);
        emptyTxt = findViewById(R.id.emptyTxt);
        scrollView = findViewById(R.id.scrollView3);
        recyclerViewList = findViewById(R.id.cartView);
        checkoutBtn = findViewById(R.id.CheckoutBTN);



                checkoutBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        createTransaction();

                    }
                });


    }

    public void createTransaction(){


                DatabaseReference userdatabase;
                userdatabase = FirebaseDatabase.getInstance().getReference("Users");
                //Make new transaction
                Transaction transaction = new Transaction();

                //Get user details necessary for transaction
                //Sets transaction amount, address to pay the amount to, sets status to false because money will go into escrow.
                for(int i =0; i < cart.size(); i++){
                    transaction.setAmount(cart.get(i).getFee());
                    transaction.setReceiverAddress(cart.get(i).getSellerAddress());
                    transaction.setStatus(false);
                    transaction.setProduct(cart.get(i));

                }

                //Sets User to current user
                //sets transaction sender address to the users eth address.
                userdatabase.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        if(snapshot.getKey().equalsIgnoreCase(userID)){
                            User user = snapshot.getValue(User.class);

                            transaction.setSenderAddress(user.getEthAddress());



                            //make new bank

                            try {

                                 insertTransaction(transaction,user);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


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
                        Log.d("CREATION", "cancelled transaction child listener");

                    }
                });




    }

    public void insertTransaction(Transaction transaction,User user){
        DatabaseReference transactionDB;
        transactionDB = FirebaseDatabase.getInstance().getReference();
        transactionDB.child("Transaction").child(userID).child(transaction.getUniqueId()).setValue(transaction).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("Transaction","Transaction added to database");
                Intent intent = new Intent(CartListActivity.this, paymentActivity.class);
                intent.putExtra("transaction",transaction);
                intent.putExtra("user",user);

                startActivity(intent);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Transaction","Transaction failed to add to database");

            }
        });
    }

    private void initList() throws InterruptedException {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        Log.d("CREATION", "start of initList ");



        getFromFirebase(new OnDataReceiveCallback(){
            @Override
            public void onDataReceived(ArrayList list) {
                Log.d("CREATION", "received data ");

                cart = list;
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

            @Override
            public void onDataReceived(int n) {

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

