package com.example.finalyearproject;

import static android.widget.Toast.LENGTH_LONG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.AuthResult;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottonnav);
        bottomNavigationView.setOnItemSelectedListener(item ->{
            switch (item.getItemId()) {
                case R.id.Home:
                    Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.Search:
                    intent = new Intent(HomeActivity.this, HomeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.Sell:
                    intent = new Intent(HomeActivity.this, addProduct.class);
                    startActivity(intent);
                    break;
                case R.id.Account:
                    intent = new Intent(HomeActivity.this, AccountActivity.class);
                    startActivity(intent);
                    break;
                case R.id.Payment:
                    intent = new Intent(HomeActivity.this, PaymentTaker.class);
                    startActivity(intent);
                    break;
            }
            return true;
        });

    }


}