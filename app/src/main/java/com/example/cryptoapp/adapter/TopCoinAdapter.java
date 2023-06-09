package com.example.cryptoapp.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cryptoapp.R;
import com.example.cryptoapp.databinding.TopCoinItemBinding;
import com.example.cryptoapp.model.crypto.CryptoCurrency;

import java.util.ArrayList;

public class TopCoinAdapter extends RecyclerView.Adapter<TopCoinAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<CryptoCurrency> cryptoCurrencyArrayList;

    public TopCoinAdapter(ArrayList<CryptoCurrency> cryptoCurrencyArrayList) {
        this.cryptoCurrencyArrayList = cryptoCurrencyArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        TopCoinItemBinding itemBinding = DataBindingUtil
                .inflate(layoutInflater, R.layout.top_coin_item, parent, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(cryptoCurrencyArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return cryptoCurrencyArrayList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void update(ArrayList<CryptoCurrency> cryptoCurrencyArrayList) {
        this.cryptoCurrencyArrayList.clear();
        this.cryptoCurrencyArrayList.addAll(cryptoCurrencyArrayList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TopCoinItemBinding itemBinding;

        public ViewHolder(@NonNull TopCoinItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        public void bind(CryptoCurrency cryptoCurrency) {
            itemBinding.topCoinNameTv.setText(String.format("%s/USD", cryptoCurrency.getSymbol()));
            loadCoinLogo(cryptoCurrency);
            setColorText(cryptoCurrency);
            setDecimalsForPrice(cryptoCurrency);
            if (cryptoCurrency.getQuotes().get(0).getPercentChange24h() > 0) {
                itemBinding.topCoinChangeTv.setText("+" + String.format("%.2f", cryptoCurrency.getQuotes().get(0).getPercentChange24h()) + "%");
            } else {
                itemBinding.topCoinChangeTv.setText(String.format("%.2f", cryptoCurrency.getQuotes().get(0).getPercentChange24h()) + "%");
            }
            itemBinding.executePendingBindings();
        }

        @SuppressLint("DefaultLocale")
        private void setDecimalsForPrice(CryptoCurrency cryptoCurrency) {
            if (cryptoCurrency.getQuotes().get(0).getPrice() < 1) {
                itemBinding.topCoinPriceTv.setText(String.format("%.0f", cryptoCurrency.getQuotes().get(0).getPrice()));
            } else if (cryptoCurrency.getQuotes().get(0).getPrice() < 10) {
                itemBinding.topCoinPriceTv.setText(String.format("%.2f", cryptoCurrency.getQuotes().get(0).getPrice()));
            } else {
                itemBinding.topCoinPriceTv.setText(String.format("%.4f", cryptoCurrency.getQuotes().get(0).getPrice()));
            }
        }

        private void setColorText(CryptoCurrency cryptoCurrency) {
            if (cryptoCurrency.getQuotes().get(0).getPercentChange24h() < 0) {
                itemBinding.topCoinPriceTv.setTextColor(Color.RED);
                itemBinding.topCoinChangeTv.setTextColor(Color.RED);
            } else {
                itemBinding.topCoinPriceTv.setTextColor(Color.GREEN);
                itemBinding.topCoinChangeTv.setTextColor(Color.GREEN);
            }
        }

        private void loadCoinLogo(CryptoCurrency cryptoCurrency) {
            Glide.with(itemBinding.getRoot())
                    .load("https://s2.coinmarketcap.com/static/img/coins/32x32/" + cryptoCurrency.getId() + ".png")
                    .thumbnail(Glide.with(itemBinding.getRoot().getContext()).load(R.drawable.ic_baseline_cloud_download_24))
                    .into(itemBinding.topCoinRiv);
        }
    }
}
