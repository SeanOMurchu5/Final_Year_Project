package com.example.finalyearproject;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_LIMIT;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class listOfTransactions extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    private MyAdapter mAdapter;
    RecyclerView mRecyclerView;
    ArrayList<Transaction> myDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_transactions);

        DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference("Transaction");

        mRecyclerView = (RecyclerView) findViewById(R.id.transactionRecycler);

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
                    Transaction transactionObj = taskSnapshot.getValue(Transaction.class);

                    myDataset.add(transactionObj);
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
                    Intent intent = new Intent(listOfTransactions.this, HomeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.Search:
                    intent = new Intent(listOfTransactions.this, HomeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.Sell:
                    intent = new Intent(listOfTransactions.this, addProduct.class);
                    startActivity(intent);
                    break;
                case R.id.Account:
                    intent = new Intent(listOfTransactions.this, AccountActivity.class);
                    startActivity(intent);
                    break;
                case R.id.Payment:
                    intent = new Intent(listOfTransactions.this, PaymentTaker.class);
                    startActivity(intent);
                    break;
            }
            return true;
        });

    }

        //write method in solidity to retrieve all banks with the owner address



}