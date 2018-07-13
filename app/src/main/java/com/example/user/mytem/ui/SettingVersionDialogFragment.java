package com.example.user.mytem.ui;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.example.user.mytem.R;

public class SettingVersionDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_version_dialog,null);
        return new AlertDialog.Builder(getActivity()).setView(view).create();
    }
}
