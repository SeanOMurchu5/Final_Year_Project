package com.example.finalyearproject.Helper;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.finalyearproject.Domain.ProductDomain;
import com.example.finalyearproject.Domain.ProductDomain;
import com.example.finalyearproject.Interface.ChangeNumberItemsListener;
import com.example.finalyearproject.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    DatabaseReference fireDB;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String userID;

    public ManagementCart(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        userID = mUser.getUid();
        fireDB = FirebaseDatabase.getInstance().getReference();

    }

    public void insertProduct(ProductDomain item){
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
        fireDB.child("cart").child(userID).setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public ArrayList<ProductDomain> getListCart(){
        ArrayList<ProductDomain> cart = new ArrayList<>();
        Log.d("CREATION","getList1 ");

        fireDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("CREATION","sNAPSHOT ");

                for(DataSnapshot taskSnapshot2 : dataSnapshot.getChildren()){
                    if(taskSnapshot2.getValue().equals("cart")){
                        for (DataSnapshot taskSnapshot : taskSnapshot2.getChildren()) {
                            Log.d("CREATION","HERE1 ");

                            if(taskSnapshot.getValue().equals(userID)){
                                ProductDomain productObj = taskSnapshot.getValue(ProductDomain.class);
                                Log.d("CREATION","HERE2 ");

                                cart.add(productObj);
                            }



                        }
                    }
                }


            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.d("CREATION","Database failed ");
            }
        });
        return cart;
    }

    public void plusNumberFood(ArrayList<ProductDomain> list, int position, ChangeNumberItemsListener changeNumberItemsListener){
       list.get(position).setNumberInCart(list.get(position).getNumberInCart() + 1);
       fireDB.setValue(list);
       changeNumberItemsListener.changed();
    }

    public void minusNumberFood(ArrayList<ProductDomain> list,int position,ChangeNumberItemsListener changeNumberItemsListener){
      if(list.get(position).getNumberInCart()==1){
        list.remove(position);
      }else{
          list.get(position).setNumberInCart(list.get(position).getNumberInCart()-1);
      }
      fireDB.setValue(list);
      changeNumberItemsListener.changed();
    }

    public Double getTotalFee(){
        ArrayList<ProductDomain> list = getListCart();
        double fee =0;
        for(int i = 0;i<list.size();i++){
            fee = fee + (list.get(i).getFee() * list.get(i).getNumberInCart());
        }

        return fee;
    }
}
