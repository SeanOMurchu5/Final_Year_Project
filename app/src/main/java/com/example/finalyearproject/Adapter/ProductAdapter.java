package com.example.finalyearproject.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.Activity.allApparelActivity;
import com.example.finalyearproject.Activity.allShoesActivity;
import com.example.finalyearproject.Product;
import com.example.finalyearproject.R;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    ArrayList<Product> products;

    public ProductAdapter(ArrayList<Product> popularProducts) {
        this.products = popularProducts;
    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_product,parent,false);
        return new ProductAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        holder.title.setText(products.get(position).getProductName());
        holder.fee.setText(String.valueOf(products.get(position).getProductPrice()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(products.get(position).getPic(),"drawable",holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            String name =products.get(position).getProductName();
            switch (name){
                case "Shoes":{
                    Intent intent = new Intent(view.getContext(), allShoesActivity.class);
                    view.getContext().startActivity(intent);
                    break;
                }
                case "Apparel":{
                    Intent intent = new Intent(view.getContext(), allApparelActivity.class);
                    view.getContext().startActivity(intent);
                    break;
                }
                case "Electronic":{

                    break;
                }
                case "Collectibles":{

                    break;
                }
                case "Accessories":{

                    break;
                }
            }
        }
    }
}

