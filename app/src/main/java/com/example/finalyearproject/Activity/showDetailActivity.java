package com.example.finalyearproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.Domain.ProductDomain;
import com.example.finalyearproject.Helper.ManagementCart;
import com.example.finalyearproject.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class showDetailActivity extends AppCompatActivity {
private TextView addToCartBtn;
private TextView titleTxt,feeTxt,descriptionTxt,numberOrderTxt;
ImageView addBtn,minusBtn, productImage;
private ProductDomain object;
int numberOrder=1;
private ManagementCart managementCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        managementCart = new ManagementCart(this);
        initView();
        getBundle();
    }

    private void getBundle() {
        object= (ProductDomain) getIntent().getSerializableExtra("object");
        StorageReference ref = FirebaseStorage.getInstance().getReference("images/"+object.getUniqueId()+".jpg");

        //int drawableResourceId = this.getResources().getIdentifier(object.getPic(),"drawable",this.getPackageName());
        Glide.with(this)
                .load(ref)
                .into(productImage);
        titleTxt.setText(object.getTitle());
        feeTxt.setText("$"+object.getFee());
        descriptionTxt.setText(object.getDescription());





        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                object.setNumberInCart(numberOrder);
                try {
                    managementCart.insertProduct(object);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Log.d("CREATION","ADD TO CART BUTTON CLICKED ");

            }
        });
    }

    private void initView() {
        addToCartBtn=findViewById(R.id.addToCartBtn);
        titleTxt = findViewById(R.id.titleTxt);
        feeTxt = findViewById(R.id.priceTxt);
        descriptionTxt=findViewById(R.id.descriptionTxt);

        productImage = findViewById(R.id.productPic);

    }
}