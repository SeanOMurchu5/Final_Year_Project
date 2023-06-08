package com.example.finalyearproject;

import static android.content.ContentValues.TAG;
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
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.EthTransaction;
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
import java.util.Optional;
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
    User user;
    Transaction transaction;
    String uniqueId;
    Boolean transactionStarted;


    public paymentActivity() throws ExecutionException, InterruptedException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        transactionStarted = false;
        Intent intent = getIntent();
        getBundle();
       // credentials = Credentials.create(key);
        credentials = Credentials.create(user.getCredentials());
         uniqueId = transaction.getUniqueId();

        String receiverAddress = transaction.getReceiverAddress();
        String senderAddress = transaction.getSenderAddress();
        double amount = transaction.getAmount();
        web3 = Web3j.build(new HttpService("HTTP://192.168.0.51:7545"));
        //change server address and portnumber

        BigInteger gasPrice = GAS_PRICE; // replace with the actual gas price you want to use
        BigInteger customGasLimit = BigInteger.valueOf(6721975); // Adjust this value based on your requirements
        BigDecimal b = Convert.toWei(String.valueOf(amount), Convert.Unit.ETHER);
        BigInteger value = Convert.toWei(String.valueOf(amount), Convert.Unit.ETHER).toBigInteger();

        final ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, customGasLimit);
        TransactionManager manager = new RawTransactionManager(web3, credentials, 200, 500);
        final BankFactory bfContract = BankFactory.load("0x7Fc7E366cBEAf00daafe771af27c21A097711284", web3, manager, gasProvider);
        ExecutorService es = Executors.newCachedThreadPool();
        //temporary for testing
        String status = "Funds released";
        String contractAddress = "0x65aaF951A6EbA97Bc1059430af919169716d58d2";


        es.execute(new Runnable() {
            @Override
            public void run() {



                //make new bank

                try {
                    String getFunds;


                   String a = String.valueOf(bfContract.createBank(senderAddress,receiverAddress,uniqueId,value).send());
                    bigint = String.valueOf(bfContract.getBankDetails(uniqueId).send());
                    Log.v(TAG,"successfully created transaction");
                    transactionStarted = true;


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
        
        if(transactionStarted){
            Toast.makeText(this, "Successfully started transaction, payment moved to escrow", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Was unable to move funds to escrow", Toast.LENGTH_SHORT).show();

        }





        TextView transactionId = findViewById(R.id.transactionIdTV);
        transactionId.setText(transaction.getUniqueId());
        TextView ethgptv = findViewById(R.id.GaspriceTVdisplay);
//     //   ethgptv.setText(gasPrice.getGasPrice().toString());
        ethgptv.setText(bigint);


    }

    private void getBundle() {
         transaction = (Transaction) getIntent().getSerializableExtra("transaction");
         user = (User) getIntent().getSerializableExtra("user");





    }

    }



