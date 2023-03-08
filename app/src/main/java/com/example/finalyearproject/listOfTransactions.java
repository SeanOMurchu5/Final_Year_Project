package com.example.finalyearproject;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_LIMIT;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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

    ArrayList<Transaction> TransactionsList;
    String ownerAddress;
    Web3j web3;
    Credentials credentials;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_transactions);

        TransactionsList = new ArrayList<>();
        web3 = Web3j.build(new HttpService("HTTP://192.168.0.127:7545"));
        BigInteger gasPrice = GAS_PRICE; // replace with the actual gas price you want to use
        BigInteger gasLimit = GAS_LIMIT;
        credentials = Credentials.create("0a95592342d8f17f8b712d937d2e9fe356b75b46062ed4552a7e89059d0c669d");

        final ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);
        TransactionManager manager = new RawTransactionManager(web3, credentials, 200, 500);
        final BankFactory bfContract = BankFactory.load("0xd78f053BB2c8cAca51844398b01F769b46212322", web3, manager, gasProvider);
        ExecutorService es = Executors.newCachedThreadPool();

        //write method in solidity to retrieve all banks with the owner address


    }
}