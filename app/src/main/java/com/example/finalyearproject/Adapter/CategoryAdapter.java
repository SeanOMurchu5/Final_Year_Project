package com.example.finalyearproject.Adapter;

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
import com.example.finalyearproject.Activity.allAccessoryActivity;
import com.example.finalyearproject.Activity.allApparelActivity;
import com.example.finalyearproject.Activity.allCollectibleActivity;
import com.example.finalyearproject.Activity.allElectronicsActivity;
import com.example.finalyearproject.Activity.allShoesActivity;
import com.example.finalyearproject.Domain.CategoryDomain;
import com.example.finalyearproject.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    ArrayList<CategoryDomain> categoryDomains;

    public CategoryAdapter(ArrayList<CategoryDomain> categoryDomains) {
        this.categoryDomains = categoryDomains;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
    holder.categoryName.setText(categoryDomains.get(position).getTitle());
    String picurl="";
    switch (position){
        case 0:{
            picurl = "cat_1";
            holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.cat_background1));
            break;
        }
        case 1:{
            picurl = "cat_2";
            holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.cat_background2));
            break;}
        case 2:{
            picurl = "cat_3";
            holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.cat_background3));
            break;}
        case 3:{
            picurl = "cat5";
            holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.cat_background4));
            break; }
        case 4:{
            picurl = "cat_4";
            holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.cat_background5));
            break;}

      }
      int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(picurl,"drawable",holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.categoryPic);
    }

    @Override
    public int getItemCount() {
        return categoryDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView categoryName;
        ImageView categoryPic;
        ConstraintLayout mainLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryPic = itemView.findViewById(R.id.categoryPic);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = this.getLayoutPosition(); //get the position where the click happened
            String name =categoryDomains.get(position).getTitle();
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
                case "Electronics":{
                    Intent intent = new Intent(view.getContext(), allElectronicsActivity.class);
                    view.getContext().startActivity(intent);
                    break;
                }
                case "Collectibles":{
                    Intent intent = new Intent(view.getContext(), allCollectibleActivity.class);
                    view.getContext().startActivity(intent);
                    break;
                }
                case "Accessories":{
                    Intent intent = new Intent(view.getContext(), allAccessoryActivity.class);
                    view.getContext().startActivity(intent);
                    break;
                }
            }

        }
    }
}
