package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PaymentTaker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_taker);



        Button sendEthBTN = findViewById(R.id.sendEthBTN);
        sendEthBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText addressET = findViewById(R.id.addressET);
                String address = addressET.getText().toString();

                EditText KeyET = findViewById(R.id.keyId);
                String key = KeyET.getText().toString();

                EditText receiverET = findViewById(R.id.receiverET);
                String receiver = receiverET.getText().toString();

                EditText amountET = findViewById(R.id.amountEthET);
                String amount = amountET.getText().toString();
                double amountInt =  Double.parseDouble(amount);
                Intent intent = new Intent(PaymentTaker.this, paymentActivity.class);
                intent.putExtra("address", address);
                intent.putExtra("amount",amountInt);
                intent.putExtra("key",key);
                intent.putExtra("receiver",receiver);

                startActivity(intent);
            }
        });


    }
}