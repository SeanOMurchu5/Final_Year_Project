package com.example.finalyearproject.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.Activity.TransactionDetailActivity;
import com.example.finalyearproject.R;
import com.example.finalyearproject.Transaction;

import java.util.ArrayList;

public class transactionHistoryAdapter extends RecyclerView.Adapter<transactionHistoryAdapter.ViewHolder> {

    ArrayList<Transaction> transactions;

    public transactionHistoryAdapter(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("CREATION", "create viewholder ");

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_transaction,parent,false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull transactionHistoryAdapter.ViewHolder holder, int position) {
        Log.d("CREATION", "onbind purchasehistory");

        holder.uniqueId.setText(String.valueOf(position));
        holder.status.setText("Status: "+transactions.get(position).isStatus());
        holder.totalCost.setText(String.valueOf(transactions.get(position).getAmount()));
        holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.cat_background1));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CREATION", "transaction history onclick");

                //get the position where the click happened
                String uniqueId =transactions.get(position).getUniqueId();
                Transaction p = transactions.get(position);


                Intent intent = new Intent(view.getContext(), TransactionDetailActivity.class);
                intent.putExtra("transaction",p);

                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView status,totalCost,uniqueId;
        ConstraintLayout mainLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.purchaseHistoryConstraint);
            status = itemView.findViewById(R.id.statustext);
            totalCost = itemView.findViewById(R.id.priceTxt);
            uniqueId = itemView.findViewById(R.id.transactionId);
            Log.d("CREATION", "transaction history view created");



        }


        @Override
        public void onClick(View view){
            Log.d("CREATION", "transaction history onclick");

            int position = this.getLayoutPosition(); //get the position where the click happened
            String uniqueId =transactions.get(position).getUniqueId();
            Transaction p = transactions.get(position);


            Intent intent = new Intent(view.getContext(), TransactionDetailActivity.class);
            intent.putExtra("transaction",p);

            view.getContext().startActivity(intent);
        }
    }
}
