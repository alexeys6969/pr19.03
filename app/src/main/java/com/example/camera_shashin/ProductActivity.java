package com.example.camera_shashin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.camera_shashin.R;
import com.example.camera_shashin.presentations.BottomSheetHepler;
import com.example.network.datas.products.ProductCreate;
import com.example.network.domains.callbacks.MyResponseCallback;
import com.example.network.domains.models.Product;
import com.example.uicomponents.button.BthBig;
import com.example.uicomponents.button.BthCustom;
import com.example.uicomponents.et.EtCustom;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class ProductActivity extends AppCompatActivity {

    public static ProductActivity init;
    EtCustom etName, etDescription, etExpenditure, etPrice;
    Spinner sCategory;
    BthBig bthCreate;
    View bthImageSelect;
    BottomSheetHepler bottomSheetHepler;
    String currentPhotoPath;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product);
        init = this;
        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etExpenditure = findViewById(R.id.etExpenditure);
        etPrice = findViewById(R.id.etPrice);
        bthCreate = findViewById(R.id.bthCreate);
        bthImageSelect = findViewById(R.id.bthImageSelect);
        sCategory = findViewById(R.id.sCategory);

        bottomSheetHepler = new BottomSheetHepler(this);
        etName.init("Название", "Введите название", "", EtCustom.TypeET.DEFAULT);
        etDescription.init("Описание", "Введите описание", "", EtCustom.TypeET.DEFAULT);
        etExpenditure.init("Расход", "Введите расход", "", EtCustom.TypeET.DEFAULT);
        etPrice.init("Цена", "Введите цену", "", EtCustom.TypeET.DEFAULT);

        bthCreate.init("Подтвердить", BthCustom.TypeButton.PRIMARY);
        bthCreate.setEnabled(false);

        etName.Et.setOnFocusChangeListener(LastFocus);
        etDescription.Et.setOnFocusChangeListener(LastFocus);
        etExpenditure.Et.setOnFocusChangeListener(LastFocus);
        etPrice.Et.setOnFocusChangeListener(LastFocus);;

        bthImageSelect.setOnClickListener(v -> {
            bottomSheetHepler.dialog.show();
        });
        bthCreate.setOnClickListener(v -> {
            Log.d("TEST", "CLICK");
        });

        bthCreate.Bth.setOnClickListener(v -> {
            Product product = new Product(
                    etName.Et.getText().toString(),
                    etDescription.Et.getText().toString(),
                    sCategory.getSelectedItemPosition(),
                    etExpenditure.Et.getText().toString(),
                    Integer.parseInt(etPrice.Et.getText().toString())
            );

            ProductCreate Request = new ProductCreate(
                    this,
                    MainActivity.TOKEN,
                    product,
                    imageUri,
                    new MyResponseCallback() {
                        @Override
                        public void onCompile(String result) {
                            Log.e("PRODUCT CREATE", result);
                            Toast.makeText(init,"Новый продукт создан!", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onError(String error) {
                            Log.e("PRODUCT CREATE", error);
                        }
                    }
            );
            Request.execute();
        });
    }

    View.OnFocusChangeListener LastFocus = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus) return;

            boolean state = true;

            if(etName.Et.getText().toString().isEmpty()) state = false;
            if(etDescription.Et.getText().toString().isEmpty()) state = false;
            if(etExpenditure.Et.getText().toString().isEmpty()) state = false;
            if(etPrice.Et.getText().toString().isEmpty()) state = false;

            boolean isCorrectPrice = Pattern.matches("\\d*", etPrice.Et.getText().toString());
            if(!isCorrectPrice)
                etPrice.setError("Поле принимает только цифры");
            if(!isCorrectPrice) state = false;
            bthCreate.setEnabled(state);
        }
    };

    public void OpenGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Выберите изображение"), 1);
    }

    public void OpenCamera() {
        try {
            Intent PictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File PhotoFile = File.createTempFile(
                    "MY_PHOTO_CADR",
                    ".jpg",
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            );
            currentPhotoPath = PhotoFile.getAbsolutePath();
            imageUri = FileProvider.getUriForFile(
                    this,
                    getPackageName() + ".provider",
                    PhotoFile);

            PictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(PictureIntent, 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bottomSheetHepler.dialog.cancel();
        if(resultCode == RESULT_OK) {
            if(requestCode == 1)
                imageUri = data.getData();
            ((ImageView) bthImageSelect).setImageURI(imageUri);
        }
    }
}