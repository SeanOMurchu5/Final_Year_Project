package com.example.finalyearproject;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_LIMIT;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.w3c.dom.Text;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Ethereum;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;
import org.web3j.utils.Convert;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.web3j.abi.datatypes.Function;
public class paymentActivity extends AppCompatActivity {
 Web3j web3;
 File file;
 String walletname;
 Credentials credentials;
 TextView txtaddress;
    EthGetBalance ethGetBalance;
    EthGasPrice gasPrice;
    String ethbalanceeth;
    String function;
    String answer;
    String a;
    String bigint;


    public paymentActivity() throws ExecutionException, InterruptedException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
       // credentials = Credentials.create(key);
        credentials = Credentials.create("0a95592342d8f17f8b712d937d2e9fe356b75b46062ed4552a7e89059d0c669d");
        String uniqueId = UUID.randomUUID().toString();

        String receiverAddress = intent.getStringExtra("receiver");
        String senderAddress = intent.getStringExtra("address");
        double amount = intent.getDoubleExtra("amount",0);
        web3 = Web3j.build(new HttpService("HTTP://192.168.0.127:7545"));
        //change server address and portnumber

        BigInteger gasPrice = GAS_PRICE; // replace with the actual gas price you want to use
        BigInteger gasLimit = GAS_LIMIT;
        BigDecimal b = Convert.toWei(String.valueOf(amount), Convert.Unit.ETHER);
        final ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);
        TransactionManager manager = new RawTransactionManager(web3, credentials, 200, 500);
        final BankFactory bfContract = BankFactory.load("0xd78f053BB2c8cAca51844398b01F769b46212322", web3, manager, gasProvider);
        ExecutorService es = Executors.newCachedThreadPool();
        //temporary for testing
        senderAddress="0xDFCd1eb65f15f5Fb248579b8251f1A9A33E86f30";
        receiverAddress="0xF945257bb938214e9e15122Fd3C9D56579896705";
        String status = "Funds released";


        es.execute(new Runnable() {
            @Override
            public void run() {



                //make new bank

                try {
                    String getFunds;

//                    String a = String.valueOf(bfContract.createBank(senderAddress,receiverAddress,uniqueId, b.toBigInteger()).send());
//                    getFunds = String.valueOf(bfContract.getBankDetails(uniqueId).send());
//                     bigint = String.valueOf(bfContract.getBank().send());
                    String a = String.valueOf(bfContract.createBank("0xDFCd1eb65f15f5Fb248579b8251f1A9A33E86f30","0xF945257bb938214e9e15122Fd3C9D56579896705",uniqueId, b.toBigInteger()).send());
                    bigint = String.valueOf(bfContract.getBankDetails(uniqueId).send());

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        es.shutdown();
        try {
            boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DatabaseReference firebaseUsers = FirebaseDatabase.getInstance().getReference();

        Transaction transaction = new Transaction(senderAddress,receiverAddress,amount,uniqueId,status);

        firebaseUsers.child("Products").child(uniqueId).setValue(transaction).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("MESSAGE", "Success at database transaction creation");
                Toast.makeText(getApplicationContext(), "Created new transaction", Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("MESSAGE", "Failed at database transaction creation");
            }
        });


        TextView ethgptv = findViewById(R.id.GaspriceTVdisplay);
//     //   ethgptv.setText(gasPrice.getGasPrice().toString());
        ethgptv.setText(bigint);

        Button releaseBTN = findViewById(R.id.releaseBTN);
        releaseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExecutorService es = Executors.newCachedThreadPool();
                es.execute(new Runnable() {



                    @Override
                    public void run() {
                        try {
                            String bankAddress = bfContract.getBankAddress(uniqueId).send();
                            Bank bankContract = Bank.load(bankAddress, web3, credentials, gasPrice, gasLimit);
                            TransactionReceipt receipt = bankContract.sendFunds().send();

                            //String releasefunds = String.valueOf(bfContract.sendFunds(uniqueId).send());
                            Toast.makeText(getApplicationContext(),"Ether Funds released from Escrow",Toast.LENGTH_SHORT).show();   // <-- throws exception
                        } catch (Exception e) {
                            e.printStackTrace();
                            //Toast.makeText(getApplicationContext(),"Ether Funds not released from Escrow",Toast.LENGTH_SHORT).show();   // <-- throws exception

                        }
                    }


        });
        }


        });
    }

    }



