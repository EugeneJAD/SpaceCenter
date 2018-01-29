package com.eugene.spacecenter.ui.main;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eugene.spacecenter.R;
import com.eugene.spacecenter.databinding.FragmentRateDialogBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class RateDialogFragment extends DialogFragment {

    private PositiveButtonClickListener callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (PositiveButtonClickListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement PositiveButtonClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentRateDialogBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_rate_dialog,container,false);

        binding.buttonCancel.setOnClickListener(view -> dismiss());
        binding.buttonRate.setOnClickListener(view -> {
            callback.onRateButtonClick();
            dismiss();
        });
        return binding.getRoot();
    }

    public interface PositiveButtonClickListener{void onRateButtonClick ();}
}
