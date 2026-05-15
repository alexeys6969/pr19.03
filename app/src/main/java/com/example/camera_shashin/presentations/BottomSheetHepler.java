package com.example.camera_shashin.presentations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.camera_shashin.ProductActivity;
import com.example.camera_shashin.R;
import com.example.network.domains.models.Product;
import com.example.uicomponents.button.BthBig;
import com.example.uicomponents.button.BthCustom;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheetHepler {
    public BottomSheetDialog dialog;
    public BottomSheetHepler(Context context) {
        dialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.bs_select_image, null);
        BthBig bthGallery = view.findViewById(R.id.bthGallery);
        BthBig bthCamera = view.findViewById(R.id.bthCamera);

        bthGallery.init(
                "\uD83D\uDDBC\uFE0F Выбрать из галереи",
                BthCustom.TypeButton.SECONDARY
        );

        bthCamera.init(
                "\uD83D\uDCF8 Сфотографировать",
                BthCustom.TypeButton.SECONDARY
        );

        bthGallery.Bth.setOnClickListener(v -> {
            ProductActivity.init.OpenGallery();
        });

        bthCamera.Bth.setOnClickListener(v -> {
            ProductActivity.init.OpenCamera();
        });

        dialog.setContentView(view);
    }
}
