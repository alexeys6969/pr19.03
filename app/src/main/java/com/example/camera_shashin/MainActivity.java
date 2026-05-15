package com.example.camera_shashin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.camera_shashin.domains.PermissionManager;
import com.example.network.datas.products.ProductGetUser;
import com.example.network.domains.callbacks.MyResponseCallback;
import com.example.network.domains.models.Product;
import com.example.uicomponents.button.BthBig;
import com.example.uicomponents.button.BthCustom;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String TOKEN = "ee7d01d6-ca0d-4459-92f2-257b0e7bd0ef";
    View bthOpenAddProduct;
    LinearLayout llContent;
    List<Product> Products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        PermissionManager.GetPermission(this,this);
        bthOpenAddProduct = findViewById(R.id.bthOpenAddProduct);
        llContent = findViewById(R.id.llContent);
        bthOpenAddProduct.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProductActivity.class);
            startActivity(intent);
        });
        ProductGetUser();
    }

    public void ProductGetUser() {
        ProductGetUser RequestProductGetUser = new ProductGetUser(TOKEN, new MyResponseCallback() {
            @Override
            public void onCompile(String result) {
                Log.d("PRODUCT GET USER", result);
                Products = new GsonBuilder().create().fromJson(
                        result,
                        new TypeToken<ArrayList<Product>>(){}.getType()
                );
                CreateElement();
            }

            @Override
            public void onError(String error) {
                Log.e("PRODUCT GET USER", error);
            }
        });
        RequestProductGetUser.execute();
    }

    public void CreateElement() {
        for(Product product : Products) {
            View itemProduct = LayoutInflater.from(this).inflate(R.layout.card, llContent, false);
            BthBig bthBig = itemProduct.findViewById(R.id.bthOpenProduct);
            TextView tvName = itemProduct.findViewById(R.id.tvName);
            TextView tvPrice = itemProduct.findViewById(R.id.tvPrice);
            bthBig.init("Открыть", BthCustom.TypeButton.PRIMARY);

            tvName.setText(product.name);
            tvPrice.setText(product.price + "Р");
            llContent.addView(itemProduct);
        }
    }
}