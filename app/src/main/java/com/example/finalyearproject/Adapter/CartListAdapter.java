package com.example.finalyearproject.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.Activity.CartListActivity;
import com.example.finalyearproject.Domain.ProductDomain;
import com.example.finalyearproject.Helper.ManagementCart;
import com.example.finalyearproject.Interface.ChangeNumberItemsListener;
import com.example.finalyearproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
   private ArrayList<ProductDomain> productDomains;
   private ManagementCart managementCart;
   private ChangeNumberItemsListener changeNumberItemsListener;
   DatabaseReference fireDB;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String userID;


    public CartListAdapter(ArrayList<ProductDomain> productDomains, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.productDomains = productDomains;
        this.managementCart = new ManagementCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
        fireDB = FirebaseDatabase.getInstance().getReference("cart");
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        assert mUser != null;
        userID = mUser.getUid();

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
     holder.title.setText(productDomains.get(position).getTitle());
     holder.totalEachItem.setText(String.valueOf(Math.round((productDomains.get(position).getNumberInCart() * productDomains.get(position).getFee())*100) / 100));
     holder.num.setText(String.valueOf(productDomains.get(position).getNumberInCart()));
        StorageReference ref = FirebaseStorage.getInstance().getReference("images/"+productDomains.get(position).getUniqueId()+".jpg");

        //int drawableResourceId = this.getResources().getIdentifier(object.getPic(),"drawable",this.getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(ref)
                .into(holder.pic);



     holder.minusItem.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

                 managementCart.minusNumberFood(productDomains, position, new ChangeNumberItemsListener() {
                     @Override
                     public void changed() {
                         notifyDataSetChanged();

                         changeNumberItemsListener.changed();

                     }
                 });

         }
     });
    }

    @Override
    public int getItemCount() {
        return productDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, feeEachItem;
        ImageView pic, minusItem;
        TextView totalEachItem, num;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            pic=itemView.findViewById(R.id.picCart);
            totalEachItem=itemView.findViewById(R.id.totalEachItem);
            num=itemView.findViewById(R.id.numberItemTxt);
            minusItem=itemView.findViewById(R.id.minusCartBtn);
        }
    }
}
