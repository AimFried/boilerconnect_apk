package com.example.boilerconnect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.boilerconnect.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
    ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.bottom_sheet_layout,
                container, false);

        Button btnValidAction = v.findViewById(R.id._btn_valid_action);
        Button btnCancelAction = v.findViewById(R.id._btn_cancel_action);

        btnValidAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                dismiss();
            }
        });

        btnCancelAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
        return v;
    }
}