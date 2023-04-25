package com.example.finalyearproject.Activity;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_LIMIT;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalyearproject.Adapter.ProductAdapter;
import com.example.finalyearproject.Bank;
import com.example.finalyearproject.BankFactory;
import com.example.finalyearproject.Domain.ProductDomain;
import com.example.finalyearproject.R;
import com.example.finalyearproject.Transaction;
import com.example.finalyearproject.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActiveTransactionDetail extends AppCompatActivity {
    RecyclerView recyclerView;
    ProductAdapter adapter;
    TextView Cost,receiver, senderAddress,title,description,releaseFundsBtn;
    ImageView pic;
    private ArrayList list;
    Transaction transaction;
    Web3j web3;
    Credentials credentials;
    BigInteger gasPrice, gasLimit;
    BigDecimal bigDecimal;
    ExecutorService es;
    DatabaseReference userDB;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String userID;
    BigInteger customGasLimit;
    ContractGasProvider gasProvider;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_transaction_detail);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        assert mUser != null;
        userID = mUser.getUid();

        list = new ArrayList<ProductDomain>();
        userDB = FirebaseDatabase.getInstance().getReference("Users");

        //set up eth variables
        web3 = Web3j.build(new HttpService("HTTP://192.168.0.51:7545"));
        gasPrice = GAS_PRICE; // replace with the actual gas price you want to use
        customGasLimit = BigInteger.valueOf(6721975);
        gasProvider = new StaticGasProvider(gasPrice, customGasLimit);
// Adjust this value based on your requirements
        Cost = findViewById(R.id.totalTV);
        receiver = findViewById(R.id.receivertv);
        senderAddress = findViewById(R.id.senderAddresstv);
        title = findViewById(R.id.titletext);
        description = findViewById(R.id.descriptiontext);
        pic = findViewById(R.id.productImageView);
        getBundle();
        userDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.getKey().equalsIgnoreCase(userID)){
                    User user = snapshot.getValue(User.class);
                    credentials = Credentials.create(user.getCredentials());

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


        releaseFundsBtn = findViewById(R.id.releaseFundsBtn);
        releaseFundsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 es = Executors.newCachedThreadPool();
                es.execute(new Runnable() {



                    @Override
                    public void run() {
                        try {

                            releaseFunds();

                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }


                });
                es.shutdown();
            }
        });
    }

    public void releaseFunds(){
        String uniqueId = transaction.getUniqueId();

        TransactionManager manager = new RawTransactionManager(web3, credentials, 200, 500);
        String bankAddress = null;
        try {
            final BankFactory bfContract = BankFactory.load("0x6a4392607d1B6C5A549C52963130f29356523E0e", web3, manager, gasProvider);

            bankAddress = bfContract.getBankAddress(transaction.getUniqueId()).send();
            Bank bankContract = Bank.load(bankAddress, web3, credentials, gasPrice, customGasLimit);
            TransactionReceipt receipt = bankContract.sendFunds().send();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void getBundle() {
        transaction= (Transaction) getIntent().getSerializableExtra("transaction");


        Cost.setText(String.valueOf(transaction.getAmount()));
        receiver.setText(String.valueOf(transaction.getReceiverAddress()));
        senderAddress.setText(transaction.getSenderAddress());
        ProductDomain productDomain = transaction.getProduct();
        title.setText(productDomain.getTitle());
        description.setText(productDomain.getDescription());
        String picurl = productDomain.getPic();
        int drawableResourceId = this.getResources().getIdentifier(picurl,"drawable",this.getPackageName());
        pic.setImageResource(drawableResourceId);




    }
}