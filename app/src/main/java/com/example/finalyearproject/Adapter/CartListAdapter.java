package com.example.finalyearproject.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
   private ArrayList<ProductDomain> productDomains;
   private ManagementCart managementCart;
   private ChangeNumberItemsListener changeNumberItemsListener;

    public CartListAdapter(ArrayList<ProductDomain> productDomains, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.productDomains = productDomains;
        this.managementCart = new ManagementCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
     holder.title.setText(productDomains.get(position).getTitle());
     holder.feeEachItem.setText(String.valueOf(productDomains.get(position).getFee()));
     holder.totalEachItem.setText(String.valueOf(Math.round((productDomains.get(position).getNumberInCart() * productDomains.get(position).getFee())*100) / 100));
     holder.num.setText(String.valueOf(productDomains.get(position).getNumberInCart()));

     int drawableResourceId=holder.itemView.getContext().getResources().getIdentifier(productDomains.get(position).getPic(),"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);

     holder.plusItem.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

                 managementCart.plusNumberFood(productDomains, position, new ChangeNumberItemsListener() {
                     @Override
                     public void changed()  {
                         notifyDataSetChanged();
                         changeNumberItemsListener.changed();
                     }
                 });

         }
     });

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
        ImageView pic, plusItem, minusItem;
        TextView totalEachItem, num;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            pic=itemView.findViewById(R.id.picCart);
            totalEachItem=itemView.findViewById(R.id.totalEachItem);
            num=itemView.findViewById(R.id.numberItemTxt);
            plusItem=itemView.findViewById(R.id.plusCartBtn);
            minusItem=itemView.findViewById(R.id.minusCartBtn);
        }
    }
}
