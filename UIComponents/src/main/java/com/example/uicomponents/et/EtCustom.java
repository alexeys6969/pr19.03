package com.example.uicomponents.et;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.uicomponents.R;

public class EtCustom extends ConstraintLayout {
    public EditText Et;
    public TextView TvLabel;
    public TextView TvError;
    private static final int DEFAULT_LAYOUT = R.layout.et; // Укажите ваш layout файл
    public enum TypeET {
        DEFAULT, HOVER, ERROR
    }

    public EtCustom(@NonNull Context context) {
        super(context);
        initLayout();
    }

    public EtCustom(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }

    public EtCustom(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    private void initLayout() {
        LayoutInflater.from(this.getContext()).inflate(R.layout.et, this, true);
        Et = findViewById(R.id.et);
        TvLabel = findViewById(R.id.tv_label);
        TvError = findViewById(R.id.tv_error);
    }

    public void init(String labelText, String hintText, String value, TypeET type) {
        if (TvLabel != null) {
            TvLabel.setText(labelText);
        }
        if (Et != null) {
            Et.setHint(hintText);
            Et.setText(value);
        }
        applyStyle(type);
    }
    public void init(String labelText, String hintText, TypeET type) {
        init(labelText, hintText, "", type);
    }

    private void applyStyle(TypeET type) {
        if (type == TypeET.DEFAULT) {
            Et.setBackgroundResource(R.drawable.et_default);
        }
        else if (type == TypeET.HOVER) {
            Et.setBackgroundResource(R.drawable.et_hover);
        }
        else if (type == TypeET.ERROR) {
            Et.setBackgroundResource(R.drawable.et_error);
        }
    }
    public void setType(TypeET type) {
        applyStyle(type);
    }
    public String getText() {
        return Et != null ? Et.getText().toString().trim() : "";
    }
    public void setText(String text) {
        if (Et != null) {
            Et.setText(text);
        }
    }
    public void setError(String errorMessage) {
        if (Et != null && TvError != null) {
            TvError.setText(errorMessage);
            TvError.setTextColor(Color.RED);
            TvError.setVisibility(VISIBLE);
            applyStyle(TypeET.ERROR);
        }
    }

    public void clearError() {
        if (Et != null && TvError != null) {
            TvError.setText("");
            TvError.setVisibility(GONE);
            applyStyle(TypeET.DEFAULT);
        }
    }
}
