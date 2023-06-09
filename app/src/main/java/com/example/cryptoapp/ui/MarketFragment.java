package com.example.cryptoapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cryptoapp.MainActivity;
import com.example.cryptoapp.R;
import com.example.cryptoapp.adapter.TopGainRvAdapter;
import com.example.cryptoapp.databinding.FragmentMarketBinding;
import com.example.cryptoapp.model.crypto.AllMarket;
import com.example.cryptoapp.model.crypto.CryptoCurrency;
import com.example.cryptoapp.viewmodel.MainViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MarketFragment extends Fragment {


    private static final String TAG = "Market";
    private FragmentMarketBinding binding;
    private MainActivity activity;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private MainViewModel viewModel;
    private CompositeDisposable compositeDisposable;
    private List<CryptoCurrency> cryptoCurrencies;
    private TopGainRvAdapter adapter;
    private List<CryptoCurrency> filterList;
    private List<CryptoCurrency> updateList;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(inflater.getContext()),
                R.layout.fragment_market, container, false);

        compositeDisposable = new CompositeDisposable();
        filterList = new ArrayList<>();
        updateList = new ArrayList<>();

        setUpViewModel();
        setUpSearchBox();
        getMarketListFromDB();
        return binding.getRoot();
    }

    private void setUpSearchBox() {
        binding.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.noItemTv.setVisibility(View.GONE);
                filterList(s.toString());
                if (filterList.isEmpty())
                    binding.noItemTv.setVisibility(View.VISIBLE);
            }
        });
    }

    private void filterList(String searchText) {
        filterList.clear();
        for (CryptoCurrency cryptoCurrency : cryptoCurrencies) {
            if (cryptoCurrency.getName().toLowerCase().contains(searchText.toLowerCase())
                    || cryptoCurrency.getSymbol().toLowerCase().contains(searchText.toLowerCase())) {
                filterList.add(cryptoCurrency);
            }
        }
        adapter.update(filterList);
    }

    private void getMarketListFromDB() {
        Disposable disposable
                = viewModel.getAllMarketEntity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allMarketEntity -> {
                    AllMarket allMarket = allMarketEntity.getAllMarket();
                    cryptoCurrencies = allMarket.getData().getCryptoCurrencyList();


                    if (binding.marketRecycleView.getAdapter() == null) {
                        adapter = new TopGainRvAdapter(cryptoCurrencies);
                        binding.marketRecycleView.setAdapter(adapter);
                    } else {
                        adapter = (TopGainRvAdapter) binding.marketRecycleView.getAdapter();
                        if (filterList.isEmpty() || filterList.size() == cryptoCurrencies.size())
                            adapter.update(cryptoCurrencies);
                        else adapter.update(filterList);
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void setUpViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpToolbar(view);

    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    private void setUpToolbar(View view) {
        NavController navController = Navigation.findNavController(view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration
                .Builder(R.id.market_item)
                .setOpenableLayout(activity.getDrawerLayout())
                .build();

        Toolbar toolbar = view.findViewById(R.id.market_tool_bar);
        collapsingToolbarLayout = binding.collapsingMarket;

        NavigationUI.setupWithNavController(collapsingToolbarLayout, toolbar, navController, appBarConfiguration);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.market_item) {
                toolbar.setNavigationIcon(R.drawable.ic_baseline_sort_24);
                toolbar.setTitle(R.string.market);
            }
        });
    }
}