package com.example.cryptoapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cryptoapp.R;
import com.example.cryptoapp.adapter.TopGainRvAdapter;
import com.example.cryptoapp.databinding.FragmentTopGainLoseBinding;
import com.example.cryptoapp.model.crypto.AllMarket;
import com.example.cryptoapp.model.crypto.CryptoCurrency;
import com.example.cryptoapp.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class TopGainLoseFragment extends Fragment {


    private FragmentTopGainLoseBinding binding;
    private MainViewModel viewModel;
    private CompositeDisposable compositeDisposable;
    private List<CryptoCurrency> data;
    private TopGainRvAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(inflater.getContext()),
                R.layout.fragment_top_gain_lose, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        compositeDisposable = new CompositeDisposable();
        if (getArguments() != null) {
            int pos = getArguments().getInt("pos");
            setUpRecycleView(pos);
        }

        return binding.getRoot();
    }

    private void setUpRecycleView(int pos) {
        Disposable disposable = viewModel.getAllMarketEntity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allMarketEntity -> {
                    AllMarket allMarket = allMarketEntity.getAllMarket();
                    data = allMarket.getData().getCryptoCurrencyList();
                    Collections.sort(data, new Comparator<CryptoCurrency>() {
                        @Override
                        public int compare(CryptoCurrency o1, CryptoCurrency o2) {
                            return Integer.compare(o1.getQuotes().get(0).getPercentChange24h().intValue(), o2.getQuotes().get(0).getPercentChange24h().intValue());
                        }
                    });


                    try {
                        ArrayList<CryptoCurrency> cryptoCurrencies = new ArrayList<>();
                        if (pos == 0) {
                            for (int i = 0; i < 10; i++) {
                                cryptoCurrencies.add(data.get(data.size() - 1 - i));
                            }
                        } else {
                            for (int i = 0; i < 10; i++) {
                                cryptoCurrencies.add(data.get(i));
                            }
                        }
                        if (binding.topLoseCoinRv.getAdapter() == null) {
                            adapter = new TopGainRvAdapter(cryptoCurrencies);
                            binding.topLoseCoinRv.setAdapter(adapter);
                        } else {
                            adapter = (TopGainRvAdapter) binding.topLoseCoinRv.getAdapter();
                            adapter.update(cryptoCurrencies);
                        }
                    } catch (Exception ignore) {
                    }
                });
        compositeDisposable.add(disposable);

    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}