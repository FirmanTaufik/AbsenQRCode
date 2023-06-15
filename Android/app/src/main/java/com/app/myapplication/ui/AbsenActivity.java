package com.app.myapplication.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.app.myapplication.Adapter.MahasiswaAdapter;
import com.app.myapplication.Model.Absen;
import com.app.myapplication.Model.Mahasiswa;
import com.app.myapplication.Model.Post;
import com.app.myapplication.R;
import com.app.myapplication.Retrofit.ApiClient;
import com.app.myapplication.Retrofit.GetService;
import com.app.myapplication.databinding.ActivityAbsenBinding;
import com.app.myapplication.helper.Utils;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import android.Manifest;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AbsenActivity extends AppCompatActivity {
    private String TAG = "AbsenActivityTAG";
    private ActivityAbsenBinding binding;
    private IntentIntegrator intentIntegrator;
    private MahasiswaAdapter mahasiswaAdapter;
    private ArrayList<Mahasiswa> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAbsenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle("Pertemuan Ke " + getIntent().getStringExtra("pertemuan"));
        initRv();
        initOnclick();
        if (getIntent().hasExtra("isEdit")) {
            getData();
        }
    }

    private void getData() {
        ApiClient.getRetrofitInstance().create(GetService.class)
                .getAbsen(getIntent().getStringExtra("pertemuan"), getIntent().getStringExtra("idMk"))
                .enqueue(new Callback<List<Absen>>() {
                    @Override
                    public void onResponse(Call<List<Absen>> call, Response<List<Absen>> response) {
                        if (response.isSuccessful()) {
                            for (Absen res : response.body()) {
                                Mahasiswa mahasiswa = new Mahasiswa();
                                mahasiswa.setIdMhs(res.getIdMhs());
                                mahasiswa.setNama(res.getNama());
                                mahasiswa.setNim(res.getNim());
                                list.add(mahasiswa);
                            }
                            mahasiswaAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Absen>> call, Throwable t) {

                    }
                });
    }

    private void initRv() {
        mahasiswaAdapter = new MahasiswaAdapter(AbsenActivity.this, list);
        binding.recyclerView.setAdapter(mahasiswaAdapter);
        mahasiswaAdapter.setListener(new MahasiswaAdapter.Listener() {
            @Override
            public void ItemClick(int position) {
                Utils.dialogConfirmation(AbsenActivity.this, sDialog -> {
                    sDialog.dismissWithAnimation();
                    list.remove(position);
                    mahasiswaAdapter.notifyDataSetChanged();
                });
            }
        });
    }

    private void initOnclick() {
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(AbsenActivity.this)
                        .withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                initScanner();
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                Utils.setUserId(AbsenActivity.this, "permintaan tidak di ijinkan");
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                            }
                        })
                        .check();

            }
        });
    }

    private void initScanner() {
        intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setPrompt("SCAN");
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() != null) {
                Log.d(TAG, "onActivityResult: " + Result.getContents());
                Mahasiswa mahasiswa = Utils.getMahasiswa(Result.getContents());
                if (mahasiswa.getIdMhs() != null) {
                    list.add(mahasiswa);
                    Collections.reverse(list);
                    mahasiswaAdapter.notifyDataSetChanged();
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            saveData();
        }
        return false;
    }


    private void saveData() {
        if (list.isEmpty())
            Toast.makeText(this, "daftar mahasiswa hadir kosong", Toast.LENGTH_SHORT).show();
        else {
            String pertemuan = getIntent().getStringExtra("pertemuan");
            String idMk = getIntent().getStringExtra("idMk");
            String tanggal = getIntent().getStringExtra("tanggal");
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                if (i == list.size() - 1) stringBuilder.append(list.get(i).getIdMhs());
                else stringBuilder.append(list.get(i).getIdMhs() + ",");
                ApiClient.getRetrofitInstance().create(GetService.class)
                        .postAbsen(pertemuan, tanggal, idMk, stringBuilder.toString())
                        .enqueue(new Callback<Post>() {
                            @Override
                            public void onResponse(Call<Post> call, Response<Post> response) {
                                if (response.isSuccessful()) {
                                    Utils.showToast(AbsenActivity.this, response.message());
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<Post> call, Throwable t) {
                                Log.d(TAG, "onFailure: " + t.getMessage());
                            }
                        });
            }

        }
    }
}