package com.example.cryptoapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.cryptoapp.R;
import com.example.cryptoapp.databinding.FragmentCoinDetailsBinding;
import com.example.cryptoapp.model.crypto.CryptoCurrency;


public class CoinDetailsFragment extends Fragment {


    private final String TAG = "Details";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCoinDetailsBinding binding
                = DataBindingUtil.inflate(LayoutInflater.from(inflater.getContext())
                , R.layout.fragment_coin_details, container
                , false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            CryptoCurrency cryptoCurrency = bundle.getParcelable("coin");
            binding.detailsTextView.setText(cryptoCurrency.getName());
        }

        binding.backButton.setOnClickListener(view -> {
//            requireActivity().onBackPressed();
            Navigation.findNavController(view).popBackStack();
        });
        return binding.getRoot();
    }
}