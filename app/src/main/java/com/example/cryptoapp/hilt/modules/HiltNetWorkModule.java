package com.example.cryptoapp.hilt.modules;

import androidx.annotation.NonNull;

import com.example.cryptoapp.retrofit.Repository;
import com.example.cryptoapp.retrofit.RequestApi;
import com.example.cryptoapp.room.RoomDao;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class HiltNetWorkModule {

    String BASE_URL = "https://api.coinmarketcap.com";

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {//if api has api-key
        return new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .build();
                return chain.proceed(request);
            }
        }).build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    RequestApi provideRequestApi(@NonNull Retrofit retrofit) {
        return retrofit.create(RequestApi.class);
    }

    @Provides
    @Singleton
    Repository provideRepository(RequestApi requestApi, RoomDao roomDao) {
        return new Repository(requestApi, roomDao);
    }
}
