package com.example.finalyearproject.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.finalyearproject.Activity.allShoesActivity;
import com.example.finalyearproject.Activity.purchaseActivity;
import com.example.finalyearproject.Activity.showDetailActivity;
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
    public void onBindViewHolder(@NonNull PopularAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
    holder.title.setText(popularProducts.get(position).getTitle());
    holder.fee.setText(String.valueOf(popularProducts.get(position).getFee()));

      int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(popularProducts.get(position).getPic(),"drawable",holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.pic);

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), showDetailActivity.class);
                intent.putExtra("object",popularProducts.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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

        @Override
        public void onClick(View view) {
            int position = this.getLayoutPosition(); //get the position where the click happened
            String name =popularProducts.get(position).getTitle();
            ProductDomain p = popularProducts.get(position);

            Intent intent = new Intent(view.getContext(), purchaseActivity.class);
            intent.putExtra("product",p);
            view.getContext().startActivity(intent);
        }
    }
}
