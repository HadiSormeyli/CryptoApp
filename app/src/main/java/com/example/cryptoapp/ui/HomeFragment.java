package com.example.cryptoapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.widget.ViewPager2;

import com.example.cryptoapp.MainActivity;
import com.example.cryptoapp.R;
import com.example.cryptoapp.adapter.SliderImageAdapter;
import com.example.cryptoapp.adapter.TopCoinAdapter;
import com.example.cryptoapp.adapter.TopGainLosersAdapter;
import com.example.cryptoapp.databinding.FragmentHomeBinding;
import com.example.cryptoapp.model.crypto.AllMarket;
import com.example.cryptoapp.model.crypto.CryptoCurrency;
import com.example.cryptoapp.viewmodel.MainViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class HomeFragment extends Fragment {

    private static final String TAG = "Home";
    private final String[] topCoins = {"BTC", "ETH", "BNB", "ADA", "XRP", "DOGE", "DOT", "UNI", "LTC", "LINK"};
    private FragmentHomeBinding binding;
    private MainActivity activity;
    private MainViewModel viewModel;
    private TopCoinAdapter adapter;
    private CompositeDisposable compositeDisposable;
    private TopGainLosersAdapter topGainLosersAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(inflater.getContext()),
                R.layout.fragment_home, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        compositeDisposable = new CompositeDisposable();

        setUpViewPager();
        getAllMarketEntity();
        setUpTabLayout(binding.topCoinLayout, binding.downCoinLayout);

        return binding.getRoot();
    }

    private void setUpTabLayout(View topCoinLayout, View downCoinLayout) {
        topGainLosersAdapter = new TopGainLosersAdapter(this);
        binding.tabViewPager.setAdapter(topGainLosersAdapter);

        Animation gainAnimIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_from_left);
        Animation gainAnimOut = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_out_left);
        Animation loseAnimIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_from_right);
        Animation loseAnimOut = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_out_right);


        binding.tabViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0) {
                    downCoinLayout.startAnimation(loseAnimOut);
                    downCoinLayout.setVisibility(View.GONE);
                    topCoinLayout.setVisibility(View.VISIBLE);
                    topCoinLayout.startAnimation(gainAnimIn);
                } else {
                    topCoinLayout.startAnimation(gainAnimOut);
                    topCoinLayout.setVisibility(View.GONE);
                    downCoinLayout.setVisibility(View.VISIBLE);
                    downCoinLayout.startAnimation(loseAnimIn);
                }
            }
        });

        new TabLayoutMediator(binding.tabLayout, binding.tabViewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("TopGainers");
            } else {
                tab.setText("TopLosers");
            }
        }).attach();
    }

    private void getAllMarketEntity() {
        Disposable disposable = viewModel.getAllMarketEntity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allMarketEntity -> {
                    AllMarket allMarket = allMarketEntity.getAllMarket();

                    ArrayList<CryptoCurrency> cryptoCurrencies = new ArrayList<>();
                    for (int i = 0; i < allMarket.getData().getCryptoCurrencyList().size(); i++) {
                        for (String coinName : topCoins) {
                            if (allMarket.getData().getCryptoCurrencyList().get(i).getSymbol().equals(coinName)) {
                                CryptoCurrency cryptoCurrency = allMarket.getData().getCryptoCurrencyList().get(i);
                                cryptoCurrencies.add(cryptoCurrency);
                            }
                        }
                    }

                    if (binding.topCoinRv.getAdapter() != null) {
                        adapter = (TopCoinAdapter) binding.topCoinRv.getAdapter();
                        adapter.update(cryptoCurrencies);
                    } else {
                        adapter = new TopCoinAdapter(cryptoCurrencies);
                        binding.topCoinRv.setAdapter(adapter);
                    }
                });

        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    private void setUpViewPager() {
        viewModel.getMutableLiveData().observe((LifecycleOwner) requireActivity(), integers -> {
            binding.viewPager.setAdapter(new SliderImageAdapter(integers));
            binding.viewPager.setOffscreenPageLimit(3);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpToolbar(view);
    }

    private void setUpToolbar(View view) {
        NavController navController = Navigation.findNavController(view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration
                .Builder(R.id.home_item)
                .setOpenableLayout(activity.getDrawerLayout())
                .build();

        Toolbar toolbar = view.findViewById(R.id.tool_bar);

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.home_item) {
                toolbar.setNavigationIcon(R.drawable.ic_baseline_sort_24);
                toolbar.setTitle(R.string.app_name);
            }
        });
    }
}