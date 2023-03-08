package com.example.finalyearproject;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.web3j.tx.TransactionManager;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<Transaction> transactionList;
    private ArrayList<Product> productList;
    private ArrayList<String> transactonsIdentifiers;
    private ArrayList<String> productIdentifiers;


    public static final String MESSAGE_KEY2 = "position";

    public MyAdapter(ArrayList<?> myDataSet) {
        if (myDataSet.isEmpty()) {
            throw new IllegalArgumentException("The data set is empty.");
        }
        Object firstObject = myDataSet.get(0);
        if (firstObject instanceof Transaction) {
            transactionList = (ArrayList<Transaction>) myDataSet;
        } else if (firstObject instanceof Product) {
            productList = (ArrayList<Product>) myDataSet;
        } else {
            throw new IllegalArgumentException("The data set is not of the expected type.");
        }
    }


    public void filterList(ArrayList<Product> filterlist) {

        productList = filterlist;

        notifyDataSetChanged();
    }


    public ArrayList<Transaction> getTransactionList() {

        return transactionList;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.row_layout, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       String type = "product";
        if(transactionList.size()<0){
          type = "Transaction";
        }
        if (type.equalsIgnoreCase("Transaction")) {
            final Transaction task = transactionList.get(position);
            String taskDescription = "Sender Address: " + task.getSenderAddress();
            String taskStatus = "Receiver Address: " + task.getReceiverAddress();
            String taskTag = " Amount in Escrow" + task.getAmount();

            String taskTotal = "Location: " + "\n" + taskDescription + "\n" + taskStatus + "\n" + taskTag;


            holder.txtView.setText(taskTotal);
        } else {
            final Product prod = productList.get(position);
            String productName = "Product Name: " + prod.getProductName();
            String productPrice = "Product price: " + prod.getProductPrice();
            String productDescription = " Product description" + prod.getDescription();

            String productTotal = "Product: " + "\n" + productName + "\n" + productPrice + "\n" + productDescription;


            holder.txtView.setText(productTotal);
        }


    }

    @Override
    public int getItemCount() {
        String type = "product";
        if(transactionList.size()<0){
            type = "Transaction";
        }else{
            type="Product";
        }
        int i = 0;
        if (type.equalsIgnoreCase("Transaction")){
            i = transactionList.size();
        }else {
            i = productList.size();

        }
        return i;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtView;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtView = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = this.getLayoutPosition(); //get the position where the click happened

          if(transactionList.size()<0){
              Intent intent = new Intent(view.getContext(), TransactionView.class );

              Transaction trans = transactionList.get(position);
              String identifier = transactonsIdentifiers.get(position);


              intent.putExtra("Transaction",trans);
              intent.putExtra(MESSAGE_KEY2, position);
              intent.putExtra("identifier",identifier);

              view.getContext().startActivity(intent);
          }else{
              Intent intent = new Intent(view.getContext(), productView.class );

              Product product = productList.get(position);
              String identifier = productIdentifiers.get(position);


              intent.putExtra("Product",product);
              intent.putExtra(MESSAGE_KEY2, position);
              intent.putExtra("identifier",identifier);

              view.getContext().startActivity(intent);
          }
        }


    }
}
