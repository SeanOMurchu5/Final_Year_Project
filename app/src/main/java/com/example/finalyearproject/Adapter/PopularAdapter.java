package com.example.finalyearproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.Domain.CategoryDomain;
import com.example.finalyearproject.Domain.ProductDomain;
import com.example.finalyearproject.R;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
    ArrayList<ProductDomain> popularProducts;

    public PopularAdapter(ArrayList<ProductDomain> productDomain) {
        this.popularProducts = productDomain;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.ViewHolder holder, int position) {
    holder.title.setText(popularProducts.get(position).getTitle());
    holder.fee.setText(String.valueOf(popularProducts.get(position).getFee()));

      int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(popularProducts.get(position).getPic(),"drawable",holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return popularProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,fee;
        ImageView pic;
        TextView addBtn;
        ConstraintLayout mainLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            fee = itemView.findViewById(R.id.fee);
            pic = itemView.findViewById(R.id.pic);
            addBtn = itemView.findViewById(R.id.addBtn);


        }
    }
}
