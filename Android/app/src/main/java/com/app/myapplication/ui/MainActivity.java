package com.app.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.app.myapplication.Adapter.kelasAdapter;
import com.app.myapplication.Model.Home;
import com.app.myapplication.R;
import com.app.myapplication.Retrofit.ApiClient;
import com.app.myapplication.Retrofit.GetService;
import com.app.myapplication.databinding.ActivityMainBinding;
import com.app.myapplication.helper.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getData();

    }

    private void getData() {
        ApiClient.getRetrofitInstance().create(GetService.class)
                .getHome(Utils.getUserId(this))
                .enqueue(new Callback<Home>() {
                    @Override
                    public void onResponse(Call<Home> call, Response<Home> response) {
                        if (response.isSuccessful()){
                            setProfile(response.body().getProfile());
                            setMengajar(response.body().getMengajar());
                        }
                        binding.spinKit.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<Home> call, Throwable t) {
                        binding.spinKit.setVisibility(View.GONE);

                    }
                });
    }

    private void setMengajar(List<Home.Mengajar> mengajar) {
        binding.imgRekap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String json = new Gson().toJson(mengajar);
                Intent intent = new Intent(MainActivity.this, RekapActivity.class);
                intent.putExtra("json",json);
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) mengajar);
                startActivity(intent);
            }
        });
        binding.recyclerView.setAdapter(new kelasAdapter(this, mengajar));
    }

    private void setProfile(Home.Profile profile) {
        binding.txtNama.setText(profile.getNamaDosen());
        binding.txtNidn.setText("NIDN : "+profile.getNidn());
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

    public void logout(View view) {
        SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setTitleText("Informasi");
        sweetAlertDialog.setContentText("Anda yakin ingin Logout?");
        sweetAlertDialog.setConfirmText("Iya");
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                Utils.setUserId(MainActivity.this, null);
                sDialog.dismissWithAnimation();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
        sweetAlertDialog.setCancelButton("Batal", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                sDialog.dismissWithAnimation();
            }
        });
        sweetAlertDialog.show();
    }

}