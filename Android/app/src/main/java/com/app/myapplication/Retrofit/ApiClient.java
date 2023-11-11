package com.app.myapplication.Retrofit;
import android.content.Context;

import androidx.annotation.NonNull;

import com.app.myapplication.helper.App;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {
    public static Context context = App.getContext();
    public static Retrofit retrofit;
    public static   String BASE_URL = "http://192.168.8.153/AbsenQRCode/";
    public static   String BASE_URL_IMAGE = BASE_URL+"images/";

    public static Retrofit getRetrofitInstance() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpBuilder())
                    .build();
        }
        return retrofit;
    }


    public static OkHttpClient okHttpBuilder() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        Integer cacheSize = (5 * 1024 * 1024);
       // Cache myCache = new Cache(context.getCachekir(), cacheSize);
        return new OkHttpClient.Builder()
        //        .cache(myCache)
                .connectTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
             /*   .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Request request = chain.request();
                  HttpUrl url = request.url().newBuilder()
                                .addQueryParameter("key", context.getResources().getString(R.string.google_map_api_key))
                                .addQueryParameter("region", context.getResources().getString(R.string.region_code_map))
                                .build();
                        return chain.proceed(request.newBuilder().url(url).build());
                    }
                })*/
                .addInterceptor(logging)
                .build();
    }
}