package com.example.uicomponents.button;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.uicomponents.R;

public class BthBig extends BthCustom{
    public BthBig(@NonNull Context context) {
        super(context);
    }
    public BthBig(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public BthBig(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void init(Integer idLayout) {
        super.init(R.layout.button);
    }
}
