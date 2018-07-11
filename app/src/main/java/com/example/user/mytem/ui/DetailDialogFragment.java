package com.example.user.mytem.ui;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.user.mytem.R;


public class DetailDialogFragment extends DialogFragment {
    private Button request;
    private Button cart;
    private Button buy;
    private TextView detailTextView;
    private ImageButton cancel;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_commet_dialog,null);

        detailTextView = view.findViewById(R.id.comment_detail_text);
        request = view.findViewById(R.id.btn_request);
        buy = view.findViewById(R.id.btn_buy);
        cart = view.findViewById(R.id.btn_cart);
        cancel = view.findViewById(R.id.btn_cancel);

        String detail = getArguments().getString("POST_DATAIL");

        detailTextView.setText(detail);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                dismiss();
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {

            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {

            }
        });

        return new AlertDialog.Builder(getActivity()).setView(view).create();
    }

}