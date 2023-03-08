package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TransactionView extends AppCompatActivity {
Transaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_view);
        Bundle extras = getIntent().getExtras();
        transaction = (Transaction) extras.get("Transaction");
        String amount = String.valueOf(transaction.getAmount());

        TextView tvTransactionID = findViewById(R.id.transactionID);
        tvTransactionID.setText(transaction.getUniqueId());

        TextView tvTransactionSeller = findViewById(R.id.transactionSeller);
        tvTransactionSeller.setText(transaction.getReceiverAddress());

        TextView tvTransactionBuyer = findViewById(R.id.transactionBuyer);
        tvTransactionBuyer.setText(transaction.getSenderAddress());

        TextView tvTransactionAmount = findViewById(R.id.transactionAmount);
        tvTransactionBuyer.setText(amount);

        TextView tvTransactionStatus = findViewById(R.id.transactionStatus);
        tvTransactionBuyer.setText(transaction.getStatus());
    }
}