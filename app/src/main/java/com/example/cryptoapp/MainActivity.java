package com.example.cryptoapp;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cryptoapp.databinding.ActivityMainBinding;
import com.example.cryptoapp.model.crypto.AllMarket;
import com.example.cryptoapp.viewmodel.MainViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Inject
    public ConnectivityManager connectivityManager;

    @Inject
    public NetworkRequest networkRequest;

    private ActivityMainBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private DrawerLayout drawerLayout;
    private MainViewModel viewModel;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        drawerLayout = binding.drawerLayout;

        compositeDisposable = new CompositeDisposable();

        setUpViewModel();
        setUpNavigationComponent();

        checkConnection();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    private void checkConnection() {
        ConnectivityManager.NetworkCallback networkCallback
                = new ConnectivityManager.NetworkCallback() {

            @Override
            public void onAvailable(@androidx.annotation.NonNull Network network) {
                callListApiRequest();
                callCryptoMarketApiRequest();
            }

            @Override
            public void onLost(@androidx.annotation.NonNull Network network) {
                Snackbar.make(binding.getRoot()
                        , getResources().getString(R.string.network_lost)
                        , Snackbar.LENGTH_LONG).show();
            }
        };

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        } else {
            connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
        }
    }

    private void callCryptoMarketApiRequest() {
        Completable.fromRunnable(() -> {
            try {
                Document document = Jsoup.connect("https://coinmarketcap.com/").get();
                Elements elements = document.getElementsByClass("cmc-link");

                String cryptos = elements.get(0).text();
                String exchanges = elements.get(1).text();
                String marketCap = elements.get(2).text();
                String volume = elements.get(3).text();
                String[] dominance = elements.get(4).text().split(" ");

            } catch (IOException ignore) {
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: jsoup done");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: jsoup " + e.getMessage());
                    }
                });
    }

    private void setUpViewModel() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    private void callListApiRequest() {
        Observable.interval(20, TimeUnit.SECONDS)
                .flatMap(n -> viewModel.getAllMarket().get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AllMarket>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        //called first
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull AllMarket allMarket) {
                        viewModel.insert(allMarket);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void setUpNavigationComponent() {
        navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.home_container
                , R.id.market_container, R.id.watch_list_container)
                .setOpenableLayout(binding.drawerLayout)
                .build();

        NavigationUI.setupWithNavController(binding.navigationView, navController);

        binding.navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.exit_item) {
                finish();
            } else {
                NavigationUI.onNavDestinationSelected(item, navController);
                binding.drawerLayout.close();
            }
            return false;
        });

        setUpSmoothBottomMenu();
    }

    private void setUpSmoothBottomMenu() {
        PopupMenu popupMenu = new PopupMenu(this, null);
        popupMenu.inflate(R.menu.smooth_menu);
        Menu menu = popupMenu.getMenu();

        NavigationUI.setupWithNavController(binding.bottomNavigationMenu, navHostFragment.getNavController());
    }
}