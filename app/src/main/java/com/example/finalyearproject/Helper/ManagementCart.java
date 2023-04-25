package com.example.finalyearproject.Helper;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.finalyearproject.Domain.ProductDomain;
import com.example.finalyearproject.Interface.ChangeNumberItemsListener;
import com.example.finalyearproject.Interface.OnGetDataListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Handler;

public class ManagementCart{
    private Context context;
    DatabaseReference fireDB;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String userID;
    ArrayList<ProductDomain> feeList = null;
    ExecutorService es;

    public ManagementCart(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        assert mUser != null;
        userID = mUser.getUid();
         es = Executors.newCachedThreadPool();

        fireDB = FirebaseDatabase.getInstance().getReference("cart");

    }

    public void insertProduct(ProductDomain item) throws InterruptedException {
        Log.d("CREATION","insert1 ");

        ArrayList<ProductDomain> productList = getListCart();


        Log.d("CREATION","AFTER GETlIST ");

        boolean existAlready =false;
        int n=0;
        for(int i=0;i<productList.size();i++){
            if(productList.get(i).getTitle().equalsIgnoreCase(item.getTitle())){
                 existAlready=true;
                 n = i;
                 break;
            }
        }

        if(existAlready){
             productList.get(n).setNumberInCart(item.getNumberInCart());
        }else{
            productList.add(item);
        }
        Log.d("CREATION","BEFORE ADDITION ");

        fireDB.child(userID).child(item.getUniqueId()).setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context.getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                Log.d("CREATION","success ");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context.getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                Log.d("CREATION","failure ");


            }
        });





    }

    public ArrayList<ProductDomain> getListCart()  {

        ArrayList<ProductDomain> cart = new ArrayList<>();
        Log.d("CREATION","Created cart ");

        fireDB.child(userID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.v(TAG,"Created child "+snapshot.getKey());
                ProductDomain productDomain = snapshot.getValue(ProductDomain.class);
                cart.add(productDomain);

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
                Log.v(TAG,"cancelled");

            }
        });





        return cart;
    }


    public void plusNumberFood(ArrayList<ProductDomain> list, int position, ChangeNumberItemsListener changeNumberItemsListener)  {
       list.get(position).setNumberInCart(list.get(position).getNumberInCart() + 1);
       for(int i =0; i < list.size();i++){

           fireDB.child(userID).child(list.get(i).getUniqueId()).setValue(list.get(i));
       }
       changeNumberItemsListener.changed();
    }

    public void minusNumberFood(ArrayList<ProductDomain> list,int position,ChangeNumberItemsListener changeNumberItemsListener)  {
      if(list.get(position).getNumberInCart()==1){
        list.remove(position);
      }else{
          list.get(position).setNumberInCart(list.get(position).getNumberInCart()-1);
      }
        for(int i =0; i < list.size();i++){

            fireDB.child(userID).child(list.get(i).getUniqueId()).setValue(list.get(i));
        }
        changeNumberItemsListener.changed();
    }

    public Double getTotalFee() {


        double fee =0;
        for(int i = 0;i< getListCart().size();i++){
            fee = fee + (getListCart().get(i).getFee() * getListCart().get(i).getNumberInCart());
        }

        return fee;
    }




}
