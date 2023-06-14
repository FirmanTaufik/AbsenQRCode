package com.app.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.myapplication.Model.Post;
import com.app.myapplication.R;
import com.app.myapplication.Retrofit.ApiClient;
import com.app.myapplication.Retrofit.GetService;
import com.app.myapplication.helper.Utils;
import com.app.myapplication.databinding.ActivityLoginBinding;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG ="LoginActivity" ;
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    public void login(View view) {
        if (binding.edtUsername.getText().length()==0){
            binding. edtUsername.setError("Username Tidak Boleh Kosong");
        }else if (binding.edtPassword.getText().length()==0) {
            binding. edtPassword.setError("Pasword Tidak Boleh Kosong");

        }else {
            binding. spinKit.setVisibility(View.VISIBLE);
            GetService getService = ApiClient.getRetrofitInstance().create(GetService.class);
            HashMap<String, String> params = new HashMap<>();
            params.put("Username",binding.edtUsername.getText().toString().trim());
            params.put("Password",binding.edtPassword.getText().toString().trim());
            Call<Post> postCall = getService.postLogin(params);
            postCall.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
                    if (response.body().getSucces().equals("1")){
                        Utils.setUserId(LoginActivity.this, response.body()
                                .getMessage());
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));

                        Toast.makeText(LoginActivity.this, "Sukses Login", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onResponse: "+response.body().getMessage());
                    }
                    binding.spinKit.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {
                    binding.  spinKit.setVisibility(View.GONE);
                    Log.d(TAG, "onFailure: "+t.getMessage());
                    Toast.makeText(LoginActivity.this, getString(R.string.terjadi_kesalahan), Toast.LENGTH_SHORT).show();

                }
            });
        }

    }

    boolean doubleBackToExitPressedOnce = false;
    @SuppressLint("NewApi")
    public void onBackPressed(){
        if (doubleBackToExitPressedOnce) {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory( Intent.CATEGORY_HOME );
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tap Sekali Lagi Untuk Keluar...", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }
}