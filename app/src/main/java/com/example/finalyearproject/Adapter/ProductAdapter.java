package com.example.finalyearproject.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.finalyearproject.Activity.allApparelActivity;
import com.example.finalyearproject.Activity.allShoesActivity;
import com.example.finalyearproject.Activity.purchaseActivity;
import com.example.finalyearproject.Activity.showDetailActivity;
import com.example.finalyearproject.Domain.ProductDomain;
import com.example.finalyearproject.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    ArrayList<ProductDomain> products;

    public ProductAdapter(ArrayList<ProductDomain> popularProducts) {
        this.products = popularProducts;
    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_product,parent,false);
        return new ProductAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        holder.title.setText(products.get(position).getTitle());
        holder.fee.setText(String.valueOf(products.get(position).getFee()));

        //int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(products.get(position).getPic(),"drawable",holder.itemView.getContext().getPackageName());

        //Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.pic);
        StorageReference ref = FirebaseStorage.getInstance().getReference("images/"+products.get(position).getUniqueId()+".jpg");
        Glide.with(holder.itemView.getContext())
                .load(ref)
                .centerCrop()
                .into(holder.pic);

        //Glide.with(holder.itemView.getContext()).asBitmap().load(products.get(position).getPic()).into(holder.pic)
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), showDetailActivity.class);
                intent.putExtra("object",products.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });

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
            String name =products.get(position).getTitle();
            ProductDomain p = products.get(position);

            Intent intent = new Intent(view.getContext(), purchaseActivity.class);
            intent.putExtra("product",p);
            view.getContext().startActivity(intent);
        }
    }
}

