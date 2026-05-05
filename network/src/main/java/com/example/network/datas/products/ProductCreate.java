package com.example.network.datas.products;

import android.content.Context;
import android.net.Uri;

import com.example.network.domains.apis.MyAsyncTask;
import com.example.network.domains.callbacks.MyResponseCallback;
import com.example.network.domains.models.Product;
import com.example.network.domains.models.ProductBasket;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class ProductCreate extends MyAsyncTask {
    Context context;
    String token;
    Product product;
    Uri uri;
    public ProductCreate(Context context, String token, Product product, Uri uri, MyResponseCallback callback) {
        super(callback);
        this.context = context;
        this.token = token;
        this.product = product;
        this.uri = uri;
    }
    @Override
    protected String doInBackground(Void... voids) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            File tempFile = createTempFileFromServer(inputStream);
            Map<String, String> params = new HashMap<>();
            params.put("Name", product.name);
            params.put("Description", product.description);
            params.put("Gender", String.valueOf(product.gender));
            params.put("Expenditure", product.expenditure);
            params.put("Price", String.valueOf(product.price));


        }
    }
}
