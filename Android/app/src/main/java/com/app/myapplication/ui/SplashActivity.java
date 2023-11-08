package com.app.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.app.myapplication.Model.SiswaKelas;
import com.app.myapplication.R;
import com.app.myapplication.Retrofit.ApiClient;
import com.app.myapplication.Retrofit.GetService;
import com.app.myapplication.databinding.ActivitySplashBinding;
import com.app.myapplication.helper.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        binding.swipeRefresh.setRefreshing(true);
        getData();
    }

    private void getData() {
        ApiClient.getRetrofitInstance().create(GetService.class)
                .getSiswaKelas().enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        binding.swipeRefresh.setRefreshing(false);
                        if (response.isSuccessful()) {
                            Utils.setAllMahasiswaJson(SplashActivity.this,response.body().toString());
                            if (Utils.getUserId(SplashActivity.this)!=null) {
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            }else startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Utils.showToast(SplashActivity.this, "terjadi kesalahan silahkan reload dengan cara swipe ke bawah");
                        binding.swipeRefresh.setRefreshing(false);

                    }
                });
    }

   /* private void getData() {
        ApiClient.getRetrofitInstance().create(GetService.class)
                .getAllMahasiswa().enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        binding.swipeRefresh.setRefreshing(false);
                        if (response.isSuccessful()) {
                            Utils.setAllMahasiswaJson(SplashActivity.this,response.body().toString());
                            if (Utils.getUserId(SplashActivity.this)!=null) {
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            }else startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Utils.showToast(SplashActivity.this, "terjadi kesalahan silahkan reload dengan cara swipe ke bawah");
                        binding.swipeRefresh.setRefreshing(false);

                    }
                });
    }*/
}