package com.example.cryptoapp.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.cryptoapp.R;
import com.example.cryptoapp.databinding.TopGainCoinItemBinding;
import com.example.cryptoapp.model.crypto.CryptoCurrency;

import java.util.List;

public class TopGainRvAdapter extends RecyclerView.Adapter<TopGainRvAdapter.ViewHolder> {

    private List<CryptoCurrency> cryptoCurrencyArrayList;
    private LayoutInflater layoutInflater;

    public TopGainRvAdapter(List<CryptoCurrency> cryptoCurrencyArrayList) {
        this.cryptoCurrencyArrayList = cryptoCurrencyArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) layoutInflater = LayoutInflater.from(parent.getContext());
        TopGainCoinItemBinding itemBinding
                = DataBindingUtil.inflate(layoutInflater
                , R.layout.top_gain_coin_item, null, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(cryptoCurrencyArrayList.get(position), position);
        holder.itemBinding.coinContainer.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("coin", cryptoCurrencyArrayList.get(position));
            Navigation.findNavController(view).navigate(R.id.action_home_item_to_coinDetailsFragment2, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return cryptoCurrencyArrayList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void update(List<CryptoCurrency> cryptoCurrencies) {
        this.cryptoCurrencyArrayList = cryptoCurrencies;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TopGainCoinItemBinding itemBinding;

        public ViewHolder(@NonNull TopGainCoinItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        public void bind(final CryptoCurrency cryptoCurrency, final int position) {
            loadCoinLogo(cryptoCurrency);
            loadChart(cryptoCurrency);
            setColorText(cryptoCurrency);
            setDecimalsForPrice(cryptoCurrency);
            if (cryptoCurrency.getQuotes().get(0).getPercentChange24h() > 0) {
                itemBinding.gainTextView.setText("+" + String.format("%.2f", cryptoCurrency.getQuotes().get(0).getPercentChange24h()) + "%");
            } else {
                itemBinding.gainTextView.setText(String.format("%.2f", cryptoCurrency.getQuotes().get(0).getPercentChange24h()) + "%");
            }
            itemBinding.numTextView.setText(String.valueOf(position));
            itemBinding.nameTextView.setText(cryptoCurrency.getName());
            itemBinding.symbolTextView.setText(cryptoCurrency.getSymbol());
            itemBinding.executePendingBindings();
        }

        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        private void setDecimalsForPrice(CryptoCurrency cryptoCurrency) {
            itemBinding.priceTextView.setText("$" + cryptoCurrency.getQuotes().get(0).getPrice().floatValue());
            if (cryptoCurrency.getQuotes().get(0).getPrice() < 1) {
                itemBinding.gainTextView.setText(String.format("%.0f", cryptoCurrency.getQuotes().get(0).getPrice()));
            } else if (cryptoCurrency.getQuotes().get(0).getPrice() < 10) {
                itemBinding.gainTextView.setText(String.format("%.2f", cryptoCurrency.getQuotes().get(0).getPrice()));
            } else {
                itemBinding.gainTextView.setText(String.format("%.4f", cryptoCurrency.getQuotes().get(0).getPrice()));
            }
        }

        private void setColorText(CryptoCurrency cryptoCurrency) {
            if (cryptoCurrency.getQuotes().get(0).getPercentChange24h() < 0) {
                itemBinding.chartImageView.setColorFilter(Color.RED);
                itemBinding.gainTextView.setTextColor(Color.RED);
            } else if (cryptoCurrency.getQuotes().get(0).getPercentChange24h() > 0) {
                itemBinding.chartImageView.setColorFilter(Color.GREEN);
                itemBinding.gainTextView.setTextColor(Color.GREEN);
            } else {
                itemBinding.chartImageView.setColorFilter(R.color.white);
                itemBinding.gainTextView.setTextColor(Color.WHITE);
            }
        }

        private void loadCoinLogo(CryptoCurrency cryptoCurrency) {
            Glide.with(itemBinding.getRoot())
                    .load("https://s2.coinmarketcap.com/static/img/coins/32x32/" + cryptoCurrency.getId() + ".png")
                    .thumbnail(Glide.with(itemBinding.getRoot().getContext()).load(R.drawable.ic_baseline_cloud_download_24))
                    .into(itemBinding.topCoinIv);
        }

        private void loadChart(CryptoCurrency cryptoCurrency) {
            Glide.with(itemBinding.getRoot())
                    .load("https://s3.coinmarketcap.com/generated/sparklines/web/7d/usd/" + cryptoCurrency.getId() + ".png")
                    .transition(DrawableTransitionOptions.withCrossFade(300))
                    .into(itemBinding.chartImageView);
        }

    }
}
